create table resumes
(
    id        bigint auto_increment
        primary key,
    common    text         null,
    contacts  text         null,
    education text         null,
    title     varchar(255) null,
    work_exp  text         null
);

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

create table users_resume
(
    user_id   bigint not null,
    resume_id bigint not null,
    primary key (user_id, resume_id),
    constraint FK6l4q5y7ff1w5mxqij4acflb2t
        foreign key (resume_id) references resumes (id),
    constraint FK9t2wdnsrihlk2sn73t9rtgqie
        foreign key (user_id) references users (id)
);


