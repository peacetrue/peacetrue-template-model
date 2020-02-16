package com.github.peacetrue.template.model.content;

import com.github.peacetrue.generator.ContextsSupplier;
import com.github.peacetrue.generator.Generator;
import com.github.peacetrue.generator.GeneratorAutoConfiguration;
import com.github.peacetrue.generator.velocity.VelocityGeneratorAutoConfiguration;
import com.github.peacetrue.sql.metadata.MetadataSqlAutoConfiguration;
import com.github.peacetrue.template.model.structure.TemplateModelStructureAutoConfigurationTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        TemplateModelContentAutoConfiguration.class,
        GeneratorAutoConfiguration.class,
        VelocityGeneratorAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        MetadataSqlAutoConfiguration.class,
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("template-model-content-test")
public class TemplateModelContentAutoConfigurationTest {

    @Autowired
    @Qualifier(GeneratorAutoConfiguration.GENERATOR_FOLDER)
    private Generator folderGenerator;
    @Autowired
    private ContextsSupplier contextsSupplier;

    @Test
    public void generateContent() throws IOException {
        //TODO 关于没有创建者的情况
        for (Map<String, Object> model : contextsSupplier.getContexts()) {
            Map<String, Object> context = TemplateModelStructureAutoConfigurationTest.getContext();
            context.putAll(model);
            folderGenerator.generate(context);
        }
    }

}