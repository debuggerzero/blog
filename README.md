# 雨·忆

## 需求分析

![](https://zero-img-1314223587.cos.ap-shanghai.myqcloud.com/blog/requirement/%E9%9B%A8%C2%B7%E5%BF%86.png)

### 技术选型

- 前端：
  - Vue3 + element-plus + editor.md
- 后端
  - spring + spring mvc + mybatis + mybatis-plus + spring boot + mysql8

## 概要设计

### 数据库设计

- user_info 表

| 字段名      | 类型          | 描述     |
| ----------- | :------------ | -------- |
| id          | int           | Id       |
| username    | varchar(256)  | 用户名   |
| account     | varchar(256)  | 用户账号 |
| password    | varchar(256)  | 用户密码 |
| avatar      | varchar(1024) | 用户头像 |
| email       | varchar(128)  | 邮箱     |
| create_time | varchar(128)  | 创建时间 |
| update_time | varchar(128)  | 修改时间 |
| is_delete   | int           | 是否删除 |

- articles_info

| 字段名          | 类型          | 描述        |
| --------------- | ------------- | ----------- |
| id              | int           | Id          |
| article_id      | var(256)      | 文章唯一 Id |
| article_title   | varchar(256)  | 博客标题    |
| article_content | varchar(1024) | 博客内容    |
| create_time     | varchar(128)  | 创建时间    |
| update_time     | varchar(128)  | 修改时间    |
| is_deleted      | int           | 是否删除    |

- user_articles

| 字段名     | 类型 | 描述    |
| ---------- | ---- | ------- |
| user_id    | int  | 用户 Id |
| article_id | int  | 文章 Id |



```mysql
create table articles_info
(
    id              int auto_increment comment 'Id'
        primary key,
    article_title   varchar(255)           not null comment '文章标题',
    article_content varchar(1024)          not null comment '文章内容',
    create_time     varchar(64) default '' not null comment '创建时间',
    update_time     varchar(64) default '' not null comment '修改时间',
    is_delete       tinyint     default 0  not null comment '是否删除'
)
    comment '文章信息表';

create table user_articles
(
    user_id    int not null comment '用户Id',
    article_id int not null comment '文章Id',
    primary key (user_id, article_id)
)
    comment '用户文章表';

create table user_info
(
    id           int auto_increment comment '用户Id'
        primary key,
    username     varchar(256)                                             null comment '用户名',
    account      varchar(256)                                             not null comment '用户账号',
    password     varchar(256)  default 'e10adc3949ba59abbe56e057f20f883e' not null comment '用户密码',
    avatar       varchar(1024) default 'default.png'                      not null comment '用户头像',
    email        varchar(128)                                             null comment '邮箱',
    create_time  varchar(64)                                              null comment '创建时间',
    updated_time varchar(64)                                              null comment '修改时间',
    is_delete    tinyint       default 0                                  not null comment '是否被删除'
)
    comment '用户信息表';
```

