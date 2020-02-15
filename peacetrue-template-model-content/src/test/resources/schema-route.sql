DROP TABLE IF EXISTS route;
create table route
(
    id                bigint auto_increment primary key,
    express_type_code varchar(63)  not null comment '快递类型',
    code              varchar(63)  not null comment '路由编号',
    order_code        varchar(63)  null comment '订单编号',
    express_code      varchar(63)  not null comment '运单号',
    address           varchar(255) not null comment '接收地址',
    accept_time       datetime     not null comment '接收时间',
    remark            varchar(255) null comment '描述',
    created_time      datetime     not null comment '创建时间'
);

INSERT INTO route (id, express_type_code, code, order_code, express_code, address, remark, accept_time, created_time)
VALUES (1, 'sf', '54', null, '803423039008', '天津市', '顺丰速运 已收取快件', '2019-02-27 15:56:25', '2019-03-06 19:42:09');