DROP TABLE IF EXISTS demo;
CREATE TABLE demo
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    code          VARCHAR(255)                      NOT NULL COMMENT '编码',
    name          VARCHAR(255)                      NOT NULL COMMENT '名称',
    creator_id    BIGINT                            NOT NULL COMMENT '创建者主键',
    created_time  DATETIME                          NOT NULL COMMENT '创建时间',
    modifier_id   BIGINT                            NOT NULL COMMENT '创建者主键',
    modified_time DATETIME                          NOT NULL COMMENT '创建时间'
);

COMMENT ON TABLE demo IS '示例';
COMMENT ON COLUMN demo.id IS '主键';


