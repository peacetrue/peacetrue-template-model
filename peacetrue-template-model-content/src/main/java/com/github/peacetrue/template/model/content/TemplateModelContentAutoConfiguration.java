package com.github.peacetrue.template.model.content;

import com.github.peacetrue.generator.*;
import com.github.peacetrue.generator.velocity.ContextFactory;
import com.github.peacetrue.generator.velocity.ToolContextFactory;
import com.github.peacetrue.generator.velocity.VelocityGeneratorAutoConfiguration;
import com.github.peacetrue.spring.util.BeanUtils;
import com.github.peacetrue.sql.metadata.ModelProperty;
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
import java.util.LinkedList;
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
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public UpperCamelContextHandler upperCamelContextHandler() {
        return new UpperCamelContextHandler("ModuleName", "DomainName");
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    @ConditionalOnProperty(prefix = "peacetrue.template.model.content", name = "isAtomic", havingValue = "false")
    public ContextHandler resetBasePackageNameToMultiple() {
        return context -> {
            String basePackageName = (String) context.get("basePackageName");
            basePackageName = basePackageName.concat(".modules.").concat((String) context.get("modulename"));
            context.put("basePackageName", basePackageName);
        };
    }

    @Bean
    public PackageNameContextHandler packageNameContextHandler() {
        return PackageNameContextHandler.DEFAULT;
    }

    @Bean
    public ContextHandler contextHandler() {
        TemplateModelContentProperties.Fields fields = properties.getFields();
        List<String> auditFieldNames = Arrays.asList(
                fields.getCreatorId(),
                fields.getCreatedTime(),
                fields.getModifierId(),
                fields.getModifiedTime()
        );
        List<String> idAuditFieldNames = new LinkedList<>();
        idAuditFieldNames.add(fields.getId());
        idAuditFieldNames.addAll(auditFieldNames);
        return context -> {
            @SuppressWarnings("unchecked")
            List<ModelProperty> properties = (List<ModelProperty>) context.get("properties");
            context.put("nonIdProperties", properties.stream().filter(property -> !property.getName().equals(fields.getId())).collect(Collectors.toList()));
            context.put("nonAuditProperties", properties.stream().filter(property -> !auditFieldNames.contains(property.getName())).collect(Collectors.toList()));
            context.put("nonIdAuditProperties", properties.stream().filter(property -> !idAuditFieldNames.contains(property.getName())).collect(Collectors.toList()));
            for (String fieldName : idAuditFieldNames) {
                properties.stream().filter(property -> property.getName().equals(fieldName))
                        .findAny().ifPresent(property -> context.put(fieldName, property));
            }
            context.put("id", properties.stream().filter(property -> property.getName().equals(fields.getId())).findAny().orElse(null));
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ContextsSupplier contextsSupplier(@Autowired ModelSupplier modelSupplier) {
        List<Map<String, Object>> contexts = modelSupplier.getModels()
                .stream().map(context -> {
                    Map<String, Object> map = BeanUtils.map(context);
                    map.put("ModuleName", context.getName());
                    map.put("moduleDescription", context.getComment());
                    return map;
                }).collect(Collectors.toList());
        return new ContextsSupplierImpl(contexts);
    }
}
