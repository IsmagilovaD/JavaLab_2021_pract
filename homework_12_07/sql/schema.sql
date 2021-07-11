-- таблица курсов
create table course
(
    id          serial primary key ,
    name        varchar(20)     not null ,
    start_date  varchar(20)     not null ,
    end_date    varchar(20)     not null ,
    teacher_id     integer     not null,
    foreign key (teacher_id) references teacher(id)
);


-- таблица студентов курса
create table course_students
(
    student_id   integer,
    course_id    integer,
    foreign key (course_id) references course(id),
    foreign key (student_id) references student(id)

);

-- таблица уроков
create table lesson
(
    id      serial primary key ,
    name    varchar(20),
    weekday varchar(20),
    time    varchar(10),
    course  integer,
    foreign key (course) references course(id)
);

-- таблица преподавателей
create table teacher
(
    id          serial         primary key ,
    first_name  varchar(20)     not null ,
    last_name   varchar(20)     not null ,
    experience  integer         not null
);

-- -- таблица курсов преподавателя
-- create table teacher_courses
-- (
--     teacher_id  integer,
--     course_id   integer,
--     foreign key (teacher_id) references teacher(id),
--     foreign key (course_id) references course(id)
-- );

-- таблица студентов
create table student
(
    id              serial         primary key ,
    first_name      varchar(20)     not null ,
    last_name       varchar(20)     not null ,
    group_number    integer         not null
);

