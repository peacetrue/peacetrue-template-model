package com.github.peacetrue.template.model.structure;

import com.github.peacetrue.generator.Generator;
import com.github.peacetrue.generator.GeneratorAutoConfiguration;
import com.github.peacetrue.generator.velocity.VelocityGeneratorAutoConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileSystemUtils;

import java.io.File;
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
@ActiveProfiles("template-model-structure-test")
public class TemplateModelStructureAutoConfigurationTest {

    @Autowired
    @Qualifier(GeneratorAutoConfiguration.GENERATOR_FOLDER)
    private Generator folderGenerator;
    @Value("${peacetrue.generator.target-path}")
    private String targetPath;

    @Test
    public void generateModel() throws IOException {
        generateModel(folderGenerator, targetPath);
    }

    public static void generateModel(Generator folderGenerator, String targetPath) throws IOException {
        FileSystemUtils.deleteRecursively(new File(targetPath));
        folderGenerator.generate(getContext());
    }

    public static Map<String, Object> getContext() {
        Map<String, Object> context = new HashMap<>();
        context.put("project-name", "peacetrue-dictionary");
        context.put("basePackageName", "com.github.peacetrue.dictionary");
        context.put("DomainName", "Dictionary");
        context.put("domainDescription", "字典");
        return context;
    }
}