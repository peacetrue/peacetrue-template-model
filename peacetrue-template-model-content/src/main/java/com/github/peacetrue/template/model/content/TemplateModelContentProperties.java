package com.github.peacetrue.template.model.content;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : xiayx
 * @since : 2020-02-21 12:43
 **/
@Data
@ConfigurationProperties(prefix = "peacetrue.template.model.content")
public class TemplateModelContentProperties {

    /** 是否原子性模块 */
    private Boolean isAtomic = true;
    /** 字段名集合 */
    private Fields fields = new Fields();

    @Data
    public static class Fields {
        private String id = "id";
        /** 创者者字段名 */
        private String creatorId = "creatorId";
        private String createdTime = "createdTime";
        private String modifierId = "modifierId";
        private String modifiedTime = "modifiedTime";
    }
}
