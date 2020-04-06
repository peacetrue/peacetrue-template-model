package com.github.peacetrue.template.model.content;

import com.github.peacetrue.generator.*;
import com.github.peacetrue.generator.velocity.ContextFactory;
import com.github.peacetrue.generator.velocity.ToolContextFactory;
import com.github.peacetrue.generator.velocity.VelocityGeneratorAutoConfiguration;
import com.github.peacetrue.spring.util.BeanUtils;
import com.github.peacetrue.sql.metadata.ModelSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xiayx
 */
@Configuration
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
    public ContextsSupplier contextsSupplier(@Autowired ModelSupplier modelSupplier) {
        List<Map<String, Object>> contexts = modelSupplier.getModels()
                .stream().map(model -> {
                    Map<String, Object> context = BeanUtils.map(model);
                    context.put("ModuleName", model.getName());
                    context.put("fields", properties.getFields());
                    return context;
                }).collect(Collectors.toList());
        return new ContextsSupplierImpl(contexts);
    }
}
