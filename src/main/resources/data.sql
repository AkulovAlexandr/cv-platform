INSERT INTO users (login, password, name, surname, role) VALUES ('admin', '$2a$12$SgvPfb.0i4kDMbBk.tipRuAclrRXNbORqv2XV0E.OzX1BdljGICcW', 'Александр', 'Админ', 'ROLE_ADMIN');
INSERT INTO users (login, password, name, surname, role) VALUES ('user', '$2a$12$SgvPfb.0i4kDMbBk.tipRuAclrRXNbORqv2XV0E.OzX1BdljGICcW', 'Александр', 'Пользователь', 'ROLE_USER');
INSERT INTO resumes (title, common, education, work_exp, contacts, platform_user_id) VALUES ('Резюме Админа', 'Это резюме Александра Админа', 'Айти ШАГ', 'Артокс Лаб', 'Телефон: +375 900 00 00', 1);
INSERT INTO resumes (title, common, education, work_exp, contacts, platform_user_id) VALUES ('Резюме Пользователя', 'Это резюме Александра Пользователя', 'БТЭУПК', 'Артокс Лаб', 'Телефон: +375 900 00 00', 2);


