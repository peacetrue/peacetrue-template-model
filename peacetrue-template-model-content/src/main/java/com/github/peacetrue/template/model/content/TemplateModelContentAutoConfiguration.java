package com.github.peacetrue.template.model.content;

import com.github.peacetrue.generator.GeneratorAutoConfiguration;
import com.github.peacetrue.generator.JdbcTypeContextHandler;
import com.github.peacetrue.generator.PackageNameContextHandler;
import com.github.peacetrue.generator.UpperCamelContextHandler;
import com.github.peacetrue.generator.velocity.ContextFactory;
import com.github.peacetrue.generator.velocity.ToolContextFactory;
import com.github.peacetrue.generator.velocity.VelocityGeneratorAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author xiayx
 */
@Configuration
@AutoConfigureBefore({
        GeneratorAutoConfiguration.class,
        VelocityGeneratorAutoConfiguration.class
})
@PropertySource("classpath:application-template-model-content.properties")
public class TemplateModelContentAutoConfiguration {

    @Bean
    public ContextFactory contextFactory() {
        return ToolContextFactory.DEFAULT;
    }

    @Bean
    public JdbcTypeContextHandler jdbcTypeContextHandler() {
        return new JdbcTypeContextHandler();
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
