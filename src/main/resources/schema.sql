create schema IF NOT EXISTS cv_platform;

create table IF NOT EXISTS users
(
    id       bigint auto_increment
        primary key,
    login    varchar(255) null,
    password varchar(255) null,
    role     varchar(20) null,
    name     varchar(255) null,
    surname  varchar(255) null,
    constraint UK_ow0gan20590jrb00upg3va2fn
        unique (login)
);

create table IF NOT EXISTS resumes
(
    id        bigint auto_increment
        primary key,
    title     varchar(255) null,
    common    text         null,
    education text         null,
    work_exp  text         null,
    contacts  text         null,
    platform_user_id   bigint       null,
    constraint FK340nuaivxiy99hslr3sdydfvv
        foreign key (platform_user_id) references users (id)
);