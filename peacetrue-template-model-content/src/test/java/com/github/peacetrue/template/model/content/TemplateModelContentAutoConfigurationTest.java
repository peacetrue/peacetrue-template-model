package com.github.peacetrue.template.model.content;

import com.github.peacetrue.generator.FolderGeneratorAutoConfiguration;
import com.github.peacetrue.generator.Generator;
import com.github.peacetrue.generator.GeneratorAutoConfiguration;
import com.github.peacetrue.generator.velocity.VelocityGeneratorAutoConfiguration;
import com.github.peacetrue.spring.util.BeanUtils;
import com.github.peacetrue.sql.metadata.MetadataSqlAutoConfiguration;
import com.github.peacetrue.sql.metadata.Model;
import com.github.peacetrue.sql.metadata.ModelSupplier;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
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
        TemplateModelContentAutoConfiguration.class,
        GeneratorAutoConfiguration.class,
        FolderGeneratorAutoConfiguration.class,
        VelocityGeneratorAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        MetadataSqlAutoConfiguration.class,
})
public class TemplateModelContentAutoConfigurationTest {

    @Autowired
    @Qualifier(GeneratorAutoConfiguration.GENERATOR_FOLDER)
    private Generator folderGenerator;
    @Autowired
    private ModelSupplier modelSupplier;

    @Test
    public void generate() throws IOException {
        for (Model model : modelSupplier.getModels()) {
            Map<String, Object> context = getContext();
            context.putAll(BeanUtils.map(model));
            context.put("ModuleName", model.getName());
            folderGenerator.generate(context);
        }
    }

    private Map<String, Object> getContext() {
        Map<String, Object> context = new HashMap<>();
        context.put("project-name", "peacetrue-demo");
        context.put("basePackageName", "com.github.peacetrue.demo");
        context.put("DomainName", "Demo");
        context.put("domainDescription", "示例");
        return context;
    }
}