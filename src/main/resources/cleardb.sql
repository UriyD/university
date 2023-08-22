DELETE FROM faculties;
ALTER SEQUENCE faculties_id_seq RESTART WITH 1;
DELETE FROM groups;
ALTER SEQUENCE groups_id_seq RESTART WITH 1;
DELETE FROM courses;
ALTER SEQUENCE courses_id_seq RESTART WITH 1;
DELETE FROM students;
ALTER SEQUENCE students_id_seq RESTART WITH 1;
DELETE FROM professors;
ALTER SEQUENCE professors_id_seq RESTART WITH 1;
DELETE FROM lectures;
ALTER SEQUENCE lectures_id_seq RESTART WITH 1;
DELETE FROM timetables;
ALTER SEQUENCE timetables_id_seq RESTART WITH 1;
DELETE FROM classrooms;
ALTER SEQUENCE classrooms_id_seq RESTART WITH 1;
DELETE FROM student_course;

