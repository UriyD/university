DROP TABLE IF EXISTS faculties CASCADE;
DROP TABLE IF EXISTS groups CASCADE;
DROP TABLE IF EXISTS courses CASCADE;
DROP TABLE IF EXISTS students CASCADE;
DROP TABLE IF EXISTS professors CASCADE;
DROP TABLE IF EXISTS timetables CASCADE;
DROP TABLE IF EXISTS classrooms CASCADE;
DROP TABLE IF EXISTS lectures CASCADE;
DROP TABLE IF EXISTS students_courses;
DROP TABLE IF EXISTS professors_courses;

CREATE TABLE faculties
(
    id   serial,
    faculty_name text,
    CONSTRAINT   PK_faculties_id PRIMARY KEY (id)
);

CREATE TABLE groups
(
    id   serial,
    faculty_id int not null,
    group_name TEXT,
    CONSTRAINT PK_groups_id PRIMARY KEY (id),
    CONSTRAINT FK_groups_id FOREIGN KEY (faculty_id) REFERENCES faculties (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE students
(
    id serial,
    group_id   int  null,
    first_name text not null,
    last_name  text not null,
    email      text not null,
    parole     text not null,
    CONSTRAINT PK_students_id PRIMARY KEY (id),
    CONSTRAINT FK_students_group_id FOREIGN KEY (group_id) REFERENCES groups (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE courses
(
    id   serial,
    course_name text,
    faculty_id  int,
    CONSTRAINT  PK_courses_id PRIMARY KEY (id),
    CONSTRAINT FK_courses_faculty_id FOREIGN KEY (faculty_id) REFERENCES faculties (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE professors
(
    id serial,
    first_name   text not null,
    last_name    text not null,
    email        text not null,
    parole       text not null,
    CONSTRAINT PK_professors_id PRIMARY KEY (id)
);

CREATE TABLE timetables
(
    id serial,
    date         date,
    CONSTRAINT   PK_timetables_id PRIMARY KEY (id)
);

CREATE TABLE classrooms
(
    id serial,
    number       int,
    CONSTRAINT   PK_classrooms_id PRIMARY KEY (id)
);

CREATE TABLE lectures
(
    id          serial,
    lecture_time_start  time,
    lecture_time_finish time,
    course_id int,
    group_id int,
    classroom_id int,
    professor_id int,
    timetable_id int,
    CONSTRAINT   PK_lectures_id PRIMARY KEY (id),
    CONSTRAINT FK_lectures_course_id FOREIGN KEY (course_id) REFERENCES courses (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_lectures_group_id FOREIGN KEY (group_id) REFERENCES groups (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_lectures_classroom_id FOREIGN KEY (classroom_id) REFERENCES classrooms (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_lectures_professor_id FOREIGN KEY (professor_id) REFERENCES professors (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_lectures_timetable_id FOREIGN KEY (timetable_id) REFERENCES timetables (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE students_courses
(
    student_id int REFERENCES students (id) ON UPDATE CASCADE ON DELETE CASCADE,
    course_id  int REFERENCES courses (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT student_course_pkey PRIMARY KEY (student_id, course_id)
);

CREATE TABLE professors_courses
(
    professor_id int REFERENCES professors (id) ON UPDATE CASCADE ON DELETE CASCADE,
    course_id  int REFERENCES courses (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT professor_course_pkey PRIMARY KEY (professor_id, course_id)
);

