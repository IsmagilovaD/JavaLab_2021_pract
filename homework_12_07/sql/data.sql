insert into student(first_name, last_name, group_number)
VALUES ('Вася', 'Пупкин', 5);
insert into student(first_name, last_name, group_number)
VALUES ('Амир', 'Камалов', 3);
insert into student(first_name, last_name, group_number)
VALUES ('Ильназ', 'Ахмадиев', 1);
insert into student(first_name, last_name, group_number)
VALUES ('Динара', 'Исмагилова', 2);
insert into student(first_name, last_name, group_number)
VALUES ('Ксения', 'Бабушкина', 4);

insert into teacher(first_name, last_name, experience)
VALUES ('Наталья', 'Минина', 5);
insert into teacher(first_name, last_name, experience)
VALUES ('Андрей', 'Казанцев', 6);
insert into teacher(first_name, last_name, experience)
VALUES ('Михаил', 'Чупин', 3);

insert into course( name, start_date, end_date, teacher_id)
VALUES ( 'Английский язык', '05.02.2021', '31.05.2021', 1);
insert into course(name, start_date, end_date, teacher_id)
VALUES ('Французский язык', '01.03.2021', '01.07.2021', 1);
insert into course( name, start_date, end_date, teacher_id)
VALUES ( 'Философия', '03.03.2021', '01.09.2021', 2);
insert into course( name, start_date, end_date, teacher_id)
VALUES ('Английский язык', '01.09.2020', '31.05.2021', 1);
insert into course( name, start_date, end_date, teacher_id)
VALUES ('Робототехника', '01.09.2020', '31.05.2021', 3);

insert into course_students(student_id, course_id)
VALUES (1, 1);
insert into course_students(student_id, course_id)
VALUES (2, 1);
insert into course_students(student_id, course_id)
VALUES (3, 1);
insert into course_students(student_id, course_id)
VALUES (4, 2);
insert into course_students(student_id, course_id)
VALUES (5, 3);
insert into course_students(student_id, course_id)
VALUES (1, 3);

-- insert into teacher_courses(teacher_id, course_id)
-- VALUES (777, 1001);
-- insert into teacher_courses(teacher_id, course_id)
-- VALUES (777, 1002);
-- insert into teacher_courses(teacher_id, course_id)
-- VALUES (401, 1003);

insert into lesson(name, weekday,time, course)
values('Неправильные глаголы','Среда','12:00',4);
insert into lesson(name, weekday,time, course)
values('Модальные глаголы','Пятница','12:00',4);
