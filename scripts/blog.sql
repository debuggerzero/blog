create table user_info
(
    `id`           int auto_increment comment '用户Id'
        primary key,
    `username`     varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci                                             null comment '用户名',
    `account`      varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci                                             not null comment '用户账号',
    `password`     varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  default 'e10adc3949ba59abbe56e057f20f883e' not null comment '用户密码',
    `avatar`       varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci default 'default.png'                      not null comment '用户头像',
    `email`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci                                             null comment '邮箱',
    `create_time`  varchar(64)                                                                                               null comment '创建时间',
    `updated_time` varchar(64)                                                                                               null comment '修改时间',
    `is_delete`    tinyint                                                        default 0                                  not null comment '是否被删除'
)
    AUTO_INCREMENT = 1001
    CHARACTER SET = utf8mb4
    COLLATE utf8mb4_0900_ai_ci comment '用户信息表';

insert into user_info (`username`, `account`, `password`, `create_time`, `updated_time`)
values ('zero', 'zero', 'e10adc3949ba59abbe56e057f20f883e', date(current_time), date(current_time));

create table articles_info
(
    `id`              int auto_increment comment 'Id'
        primary key,
    `article_id`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  not null comment '文章唯一 Id',
    `article_title`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  not null comment '文章标题',
    `article_content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci not null comment '文章内容',
    `create_time`     varchar(64) default ''                                         not null comment '创建时间',
    `update_time`     varchar(64) default ''                                         not null comment '修改时间',
    `is_delete`       tinyint     default 0                                          not null comment '是否删除'
)
    AUTO_INCREMENT = 1001
    CHARACTER SET = utf8mb4
    COLLATE utf8mb4_0900_ai_ci comment '文章信息表';

insert into articles_info (`article_id`, `article_title`, `article_content`, `create_time`, `update_time`)
values ('83bfd63991f44dfc80b60cbb1d90a3ff', 'test', '1001/83bfd63991f44dfc80b60cbb1d90a3ff.json', date(current_time),
        date(current_time));

create table user_articles
(
    `user_id`    int                                                           not null comment '用户Id',
    `article_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci not null comment '文章Id',
    primary key (`user_id`, `article_id`)
)
    CHARACTER SET = utf8mb4
    COLLATE utf8mb4_0900_ai_ci comment '用户文章表';

insert into user_articles (user_id, article_id)
values (1001, '83bfd63991f44dfc80b60cbb1d90a3ff');