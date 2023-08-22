INSERT INTO faculties (faculty_name)
VALUES ('Cybernetics faculty'),
       ('History faculty'),
       ('Geography'),
       ('Literature faculty'),
       ('Social faculty'),
       ('Law faculty');

INSERT INTO courses (course_name, faculty_id)
VALUES ('Math', 1),
       ('History of Ukraine', 2),
       ('Physic', 3),
       ('Geography', 4),
       ('English', 2),
       ('Chemistry', 2);

INSERT INTO groups (faculty_id, group_name)
VALUES (1, 'jk-56'),
       (2, 'ag-21'),
       (3, 'jk-56'),
       (4, 'sd-89'),
       (3, 'rt-56'),
       (5, 'nm-36');

INSERT INTO students (group_id, first_name, last_name, email, parole)
VALUES (2, 'Kate', 'Moss', 'kate@gmail.com', '{bcrypt}$2a$10$1jRD7t3OXfWLlUie5zRhv.pefeDth5gJ0AR0/9T9DD1qcYgM.zXaC'),
       (1, 'Mark', 'Zorin', 'mark@gmail.com', '{bcrypt}$2a$10$KJ3KZ84EQFlyyJezKkWfzu0A9lTQvIQuBXUJEsLnjDBq1xkpMmpAW'),
       (3, 'Dony', 'Zell', 'dony@gmail.com', '{bcrypt}$2a$10$8cuAlBnXhuSjw8FGqWzlReeWfi5ObFTifh8GZ0K6xCXSshEly4o2G'),
       (4, 'Dima', 'Korin', 'dima@gmail.com', '{bcrypt}$2a$10$70Y6SIOd.fOq27dyXTIvVOx0B5rXmbE2LiY0GF64pgXH4J1jZz9Hy'),
       (5, 'Kamila', 'Shved', 'kami@gmail.com', '{bcrypt}$2a$10$qbJO7wwT..QO2H1H/6tk0eqZ/aoi4x9jqG59i/d63D83O8EtKEep2'),
       (4, 'Tony', 'Stark', 'tony@gmail.com', '{bcrypt}$2a$10$gh3I3q4oCOm2Z8N0vAaTju9O5aGn.Fup8MOS.zFDD6XZPqM0RsU2S');

INSERT INTO professors (first_name, last_name, email, parole)
VALUES ('Andrii', 'Nemchinskiy', 'andrii@gmail.com', '{bcrypt}$2a$10$zaRfhKWkK0oKxK/c1g9Tte3Hcj81KIQHR5aOY6pIsBSXmHw/UxYyS'),
       ('Karl', 'Lagerfeld', 'karl@gmail.com', '{bcrypt}$2a$10$HSaVjCXjZDq4ZBZHT8eIxO.sHmIo7aEpV5qmA6FlBxYbdPNHB/rqi'),
       ('Oleg', 'Slon', 'oleg@gmail.com', '{bcrypt}$2a$10$DNDbd6IlDDCzKJbOKKWOa.gwgbH/dTPsrET9U5NZwrpymZGNAsAau'),
       ('Pasha', 'Rovin', 'pasha@gmail.com', '{bcrypt}$2a$10$UcrJgb1BwUwmE50UFAOEoOMe2qBWKm9xHjEYVhwDLuMDUMLfGNv46'),
       ('Misha', 'Manay', 'misha@gmail.com', '{bcrypt}$2a$10$kEDh9Jlkd72NolLYBBamm.VgS9vR416E3p1UNSFd7/fQlGbnni2Eu'),
       ('Tolya', 'Akeev', 'tolya@gmail.com', '{bcrypt}$2a$10$CGTrtv4l.i9G.DedToVn1.dOfunbKq18qdJHKglp64vxPnfchO69O');

INSERT INTO timetables (date)
VALUES ('2023-11-21'),
       ('2023-11-22'),
       ('2023-11-23'),
       ('2023-11-24'),
       ('2023-11-25'),
       ('2023-11-26');

INSERT INTO classrooms (number)
VALUES (36),
       (47),
       (56),
       (89),
       (12),
       (45),
       (69);

INSERT INTO lectures (lecture_time_start, lecture_time_finish, course_id, group_id, classroom_id, professor_id, timetable_id)
VALUES ('09:05:00', '10:35:00', 3, 2, 5, 6, 2),
       ('11:40:00', '13:10:00', 5, 1, 6, 3, 4),
       ('09:05:00', '10:35:00', 2, 4, 6,1, 3),
       ('11:40:00', '13:10:00', 1, 6, 4,2,2),
       ('09:05:00', '10:35:00', 5,3,1,2,6),
       ('11:40:00', '13:10:00', 4,1,5,3, 4),
       ('13:05:00', '14:35:00', 4,2,3,5,1),
       ('12:40:00', '13:40:00', 3,4,2,3, 5);

INSERT INTO students_courses (student_id, course_id)
VALUES (1, 2),
       (2, 5),
       (3, 4),
       (5, 5),
       (1, 6),
       (4, 1),
       (6, 2),
       (2, 4);

INSERT INTO professors_courses (professor_id, course_id)
VALUES (3, 5),
       (6, 1),
       (2, 3),
       (1, 2),
       (5, 6),
       (1, 4),
       (3, 1),
       (4, 4);

