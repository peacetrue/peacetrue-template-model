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




insert into demo (code, name, creator_id, created_time, modifier_id, modified_time)
values ('1', '1', 1, '2010-01-01 01:01:01', 1, '2010-01-01 01:01:01');



