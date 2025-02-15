create database if not exists speaker;

use speaker;

create table if not exists post
(
    id            bigint auto_increment
        primary key,
    user_id       bigint                             not null,
    username      varchar(32)                        not null,
    title         varchar(64)                        not null,
    content       text                               not null,
    content_brief varchar(256)                       not null,
    create_time   datetime default CURRENT_TIMESTAMP not null
);

create table if not exists user
(
    id          bigint auto_increment
        primary key,
    username    varchar(32)                        not null,
    password    varchar(64)                        not null,
    create_time datetime default CURRENT_TIMESTAMP not null,
    constraint user_uk
        unique (username)
);
