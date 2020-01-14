package com.github.peacetrue.template.model.content;

import com.github.peacetrue.generator.ContextHandler;
import com.github.peacetrue.generator.GeneratorAutoConfiguration;
import com.github.peacetrue.generator.JdbcTypeContextHandler;
import com.github.peacetrue.generator.UpperCamelContextHandler;
import com.github.peacetrue.generator.placeholder.PlaceholderResolver;
import com.github.peacetrue.generator.placeholder.PlaceholderResolverImpl;
import com.github.peacetrue.generator.velocity.ContextFactory;
import com.github.peacetrue.generator.velocity.ToolContextFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author xiayx
 */
@Configuration
@AutoConfigureBefore(GeneratorAutoConfiguration.class)
@PropertySource("classpath:application-template-model-content.properties")
public class TemplateModelContentAutoConfiguration {

    @Bean
    public ContextFactory contextFactory() {
        return ToolContextFactory.DEFAULT;
    }

    @Bean
    public ContextHandler jdbcTypeContextHandler() {
        return new JdbcTypeContextHandler();
    }

    @Bean
    public UpperCamelContextHandler upperCamelContextHandler() {
        return new UpperCamelContextHandler("ModuleName", "DomainName");
    }

    @Bean
    public PlaceholderResolver placeholderResolver() {
        return new PlaceholderResolverImpl((placeholder, actualValue)
                -> "basePackageName".equals(placeholder)
                ? actualValue.replaceAll("\\.", "/")
                : actualValue
        );
    }

}
