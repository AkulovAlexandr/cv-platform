INSERT INTO users (id, login, password, name, surname, role) VALUES (1, 'admin', 'admin', 'Александр', 'Админ', 'ADMIN');
INSERT INTO users (id, login, password, name, surname, role) VALUES (2, 'user', 'user', 'Александр', 'Пользователь', 'USER');
INSERT INTO resumes (id, title, common, education, work_exp, contacts, user_id) VALUES (1, 'Резюме Админа', 'Это резюме Александра Админа', 'Айти ШАГ', 'Артокс Лаб', 'Телефон: +375 900 00 00', 1);
INSERT INTO resumes (id, title, common, education, work_exp, contacts, user_id) VALUES (2, 'Резюме Пользователя', 'Это резюме Александра Пользователя', 'БТЭУПК', 'Артокс Лаб', 'Телефон: +375 900 00 00', 2);


