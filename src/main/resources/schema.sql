create schema IF NOT EXISTS cv_platform collate utf8mb4_0900_ai_ci;

create table IF NOT EXISTS users
(
    id       bigint auto_increment
        primary key,
    login    varchar(255) null,
    name     varchar(255) null,
    password varchar(255) null,
    role     varchar(255) null,
    surname  varchar(255) null,
    constraint UK_ow0gan20590jrb00upg3va2fn
        unique (login)
);

create table IF NOT EXISTS resumes
(
    id        bigint auto_increment
        primary key,
    common    text         null,
    contacts  text         null,
    education text         null,
    title     varchar(255) null,
    work_exp  text         null,
    user_id   bigint       null,
    constraint FK340nuaivxiy99hslr3sdydfvv
        foreign key (user_id) references users (id)
);