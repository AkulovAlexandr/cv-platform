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

