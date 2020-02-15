package com.github.peacetrue.template.model.content;

import com.github.peacetrue.generator.Generator;
import com.github.peacetrue.generator.GeneratorAutoConfiguration;
import com.github.peacetrue.generator.velocity.VelocityGeneratorAutoConfiguration;
import com.github.peacetrue.spring.util.BeanUtils;
import com.github.peacetrue.sql.metadata.MetadataSqlAutoConfiguration;
import com.github.peacetrue.sql.metadata.Model;
import com.github.peacetrue.sql.metadata.ModelSupplier;
import com.github.peacetrue.template.model.structure.TemplateModelStructureAutoConfigurationTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
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
    private ModelSupplier modelSupplier;

    @Test
    public void generateContent() throws IOException {
        //TODO 关于没有创建者的情况
        for (Model model : modelSupplier.getModels()) {
            Map<String, Object> context = TemplateModelStructureAutoConfigurationTest.getContext();
            context.putAll(BeanUtils.map(model));
            context.put("ModuleName", model.getName());
            folderGenerator.generate(context);
        }
    }

}