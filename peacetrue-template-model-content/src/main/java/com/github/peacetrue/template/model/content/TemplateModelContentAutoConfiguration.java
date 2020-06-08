package com.github.peacetrue.template.model.content;

import com.github.peacetrue.generator.*;
import com.github.peacetrue.generator.velocity.ContextFactory;
import com.github.peacetrue.generator.velocity.ToolContextFactory;
import com.github.peacetrue.generator.velocity.VelocityGeneratorAutoConfiguration;
import com.github.peacetrue.spring.util.BeanUtils;
import com.github.peacetrue.sql.metadata.ModelSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xiayx
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@AutoConfigureBefore({
        GeneratorAutoConfiguration.class,
        VelocityGeneratorAutoConfiguration.class
})
@PropertySource("classpath:application-template-model-content.properties")
@EnableConfigurationProperties(TemplateModelContentProperties.class)
public class TemplateModelContentAutoConfiguration {

    private TemplateModelContentProperties properties;

    public TemplateModelContentAutoConfiguration(TemplateModelContentProperties properties) {
        this.properties = properties;
    }

    @Bean
    public ContextFactory contextFactory() {
        return ToolContextFactory.DEFAULT;
    }

    @Bean
    public JdbcTypeContextHandler jdbcTypeContextHandler() {
        return new JdbcTypeContextHandler();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public UpperCamelContextHandler upperCamelContextHandler() {
        return new UpperCamelContextHandler("ModuleName", "DomainName");
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    @ConditionalOnProperty(prefix = "peacetrue.template.model.content", name = "isAtomic", havingValue = "false")
    public ContextHandler resetBasePackageNameToMultiple() {
        return new ContextHandler() {
            @Override
            public void handle(Map<String, Object> map) {
                String basePackageName = (String) map.get("basePackageName");
                basePackageName = basePackageName.concat(".modules.").concat((String) map.get("modulename"));
                map.put("basePackageName", basePackageName);
            }
        };
    }

    @Bean
    public PackageNameContextHandler packageNameContextHandler() {
        return PackageNameContextHandler.DEFAULT;
    }

    @Bean
    @ConditionalOnMissingBean
    public ContextsSupplier contextsSupplier(@Autowired ModelSupplier modelSupplier) {
        TemplateModelContentProperties.Fields fields = properties.getFields();
        List<String> idAuditFieldNames = Arrays.asList(
                fields.getId(),
                fields.getCreatorId(),
                fields.getCreatedTime(),
                fields.getModifierId(),
                fields.getModifiedTime()
        );

        List<Map<String, Object>> contexts = modelSupplier.getModels()
                .stream().map(model -> {
                    Map<String, Object> context = BeanUtils.map(model);
                    context.put("fields", fields);
                    context.put("ModuleName", model.getName());
                    context.put("nonIdProperties", model.getProperties().stream().filter(property -> !property.getName().equals(fields.getId())).collect(Collectors.toList()));
                    context.put("nonIdAuditProperties", model.getProperties().stream().filter(property -> !idAuditFieldNames.contains(property.getName())).collect(Collectors.toList()));
                    context.put("idAuditFieldNames", idAuditFieldNames);
                    return context;
                }).collect(Collectors.toList());
        return new ContextsSupplierImpl(contexts);
    }
}
