create table users
(
    id       bigint auto_increment
        primary key,
    login    varchar(255) null,
    name     varchar(255) null,
    password varchar(255) null,
    surname  varchar(255) null,
    constraint UK_ow0gan20590jrb00upg3va2fn
        unique (login)
);

