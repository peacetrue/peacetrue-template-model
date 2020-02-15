package com.github.peacetrue.template.model.structure;

import com.github.peacetrue.generator.GeneratorAutoConfiguration;
import com.github.peacetrue.generator.PackageNameContextHandler;
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
@PropertySource("classpath:application-template-model-structure.properties")
public class TemplateModelStructureAutoConfiguration {

    @Bean
    public ContextFactory contextFactory() {
        return ToolContextFactory.DEFAULT;
    }

    @Bean
    public UpperCamelContextHandler upperCamelContextHandler() {
        return new UpperCamelContextHandler("ModuleName", "DomainName");
    }

    @Bean
    public PackageNameContextHandler packageNameContextHandler() {
        return PackageNameContextHandler.DEFAULT;
    }

}
