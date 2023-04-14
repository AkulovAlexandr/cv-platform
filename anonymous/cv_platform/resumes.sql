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

