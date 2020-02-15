DROP TABLE IF EXISTS dictionary_type;
CREATE TABLE dictionary_type
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    code          VARCHAR(255)                      NOT NULL COMMENT '编码',
    name          VARCHAR(255)                      NOT NULL COMMENT '名称',
    creator_id    BIGINT                            NOT NULL COMMENT '创建者主键',
    created_time  DATETIME                          NOT NULL COMMENT '创建时间',
    modifier_id   BIGINT                            NOT NULL COMMENT '修改者主键',
    modified_time DATETIME                          NOT NULL COMMENT '修改时间'
);

insert into dictionary_type (id, code, name, creator_id, created_time, modifier_id, modified_time)
values (1, 'sex', '性别', 1, '2010-01-01 01:01:01', 1, '2010-01-01 01:01:01');


DROP TABLE IF EXISTS dictionary_value;
CREATE TABLE dictionary_value
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    dictionary_type_id BIGINT                            NOT NULL COMMENT '字典类型主键',
    code               VARCHAR(255)                      NOT NULL COMMENT '编码',
    name               VARCHAR(255)                      NOT NULL COMMENT '名称',
    creator_id         BIGINT                            NOT NULL COMMENT '创建者主键',
    created_time       DATETIME                          NOT NULL COMMENT '创建时间',
    modifier_id        BIGINT                            NOT NULL COMMENT '修改者主键',
    modified_time      DATETIME                          NOT NULL COMMENT '修改时间'
);

insert into dictionary_value (id, dictionary_type_id, code, name, creator_id, created_time, modifier_id, modified_time)
values (1, 1, 'man', '男', 1, '2010-01-01 01:01:01', 1, '2010-01-01 01:01:01'),
       (2, 1, 'woman', '女', 1, '2010-01-01 01:01:01', 1, '2010-01-01 01:01:01');



