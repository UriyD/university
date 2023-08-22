INSERT INTO faculties (faculty_name)
VALUES ('Cybernetics faculty'),
       ('History faculty'),
       ('Geography')
       ;

INSERT INTO courses (course_name, faculty_id)
VALUES ('Math', 1),
       ('History of Ukraine', 2),
       ('Physic', 3),
       ('Geography', 1),
       ('English', 2),
       ('Chemistry', 2);


INSERT INTO groups (faculty_id, group_name)
VALUES (1, 'jk-56'),
       (2, 'ag-21'),
       (3, 'jk-56'),
       (2, 'rt-56'),
       (3, 'oo-89'),
       (1, 'aa-22'),
       (2, 'tt-33'),
       (2, 'ii-77');


INSERT INTO students (group_id, first_name, last_name, email, parole)
VALUES (2, 'Kate', 'Moss', 'kate@gmail.com', '1234rt'),
       (1, 'Mark', 'Zorin', 'mark@gmail.com', '678yh'),
       (3, 'Dony', 'Zell', 'dony@gmail.com', '242424')
       ;

INSERT INTO professors (first_name, last_name, email, parole)
VALUES ('Andrii', 'Nemchinskiy', 'andrii@gmail.com', '12345'),
       ('Karl', 'Lagerfeld', 'karl@gmail.com', '4t54g'),
       ('Oleg', 'Slon', 'oleg@gmail.com', '12fg4g34345')
       ;

INSERT INTO timetables (date)
VALUES ('2023-11-21'),
       ('2023-11-22'),
       ('2023-11-23')
      ;

INSERT INTO classrooms (number)
VALUES (36),
       (47),
       (56)
       ;

INSERT INTO lectures (lecture_time_start, lecture_time_finish, course_id, group_id, classroom_id, professor_id, timetable_id)
VALUES ('09:05:00', '10:35:00', 3, 2, 2, 2, 2),
       ('11:40:00', '13:10:00', 1, 1, 3, 2, 1),
       ('09:05:00', '10:35:00', 2, 3, 1,1, 3)
       ;

INSERT INTO students_courses (student_id, course_id)
VALUES (1, 3),
       (2, 1),
       (3, 2)
       ;

INSERT INTO professors_courses (professor_id, course_id)
VALUES (3, 2),
       (1, 1),
       (2, 3)
       ;

