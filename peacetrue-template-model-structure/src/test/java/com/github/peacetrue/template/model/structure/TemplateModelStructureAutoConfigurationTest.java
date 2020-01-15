package com.github.peacetrue.template.model.structure;

import com.github.peacetrue.generator.Generator;
import com.github.peacetrue.generator.GeneratorAutoConfiguration;
import com.github.peacetrue.generator.velocity.VelocityGeneratorAutoConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        TemplateModelStructureAutoConfiguration.class,
        GeneratorAutoConfiguration.class,
        VelocityGeneratorAutoConfiguration.class,
})
public class TemplateModelStructureAutoConfigurationTest {

    @Autowired
    @Qualifier(GeneratorAutoConfiguration.GENERATOR_FOLDER)
    private Generator folderGenerator;

    @Test
    public void generate() throws IOException {
        Map<String, Object> context = new HashMap<>();
        context.put("project-name", "peacetrue-demo");
        context.put("basePackageName", "com.github.peacetrue.demo");
        context.put("DomainName", "Demo");
        context.put("domainDescription", "示例");
        folderGenerator.generate(context);
    }
}