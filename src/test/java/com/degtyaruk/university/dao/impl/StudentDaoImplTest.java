//package com.degtyaruk.university.dao.impl;
//
//import com.degtyaruk.university.config.SpringConfigTest;
//import com.degtyaruk.university.dao.Page;
//import com.degtyaruk.university.dao.implhibernate.StudentDaoHibernateImpl;
//import com.degtyaruk.university.model.Course;
//import com.degtyaruk.university.model.Student;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.jdbc.SqlGroup;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.support.AnnotationConfigContextLoader;
//import org.springframework.test.jdbc.JdbcTestUtils;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//import static org.hamcrest.Matchers.is;
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {SpringConfigTest.class}, loader = AnnotationConfigContextLoader.class)
//@SqlGroup({@Sql("classpath:initdatabase.sql"), @Sql("classpath:fulltestdatabase.sql")})
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//class StudentDaoImplTest {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    @Autowired
//    private StudentDaoHibernateImpl studentDao;
//
//    @Test
//    void addStudent_shouldReturnNewStudent_whenAddNewStudent() {
//        Student expected = makeStudent(4, "Timur", "Basmanov", "tima@gmail.com", "67ty", 2);
//        Student student = makeStudent(null, "Timur", "Basmanov", "tima@gmail.com", "67ty", 2);
//        Student  actual = studentDao.add(student);
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void addStudent_shouldAddNewRecord_whenAddNewStudent() {
//        Student student = makeStudent(null, "Max", "Gurin", "gurin@gmail.com", "jhj6", 1);
//        studentDao.add(student);
//        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "students");
//        assertThat("Actual result does not match expected", actual, is(equalTo(4)));
//        int actualWithWhereClause = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "students",
//                "first_name = 'Max' and last_name = 'Gurin' and email = 'gurin@gmail.com' and parole = 'jhj6' and group_id = 1");
//        assertThat("Actual result does not match expected", actualWithWhereClause, is(equalTo(1)));
//    }
//
//    @Test
//    void updateStudent_shouldUpdateStudent_whenPassedStudent() {
//        Student student = makeStudent(1, "Max", "Gurin", "gurin@gmail.com", "jhj6", 3);
//        studentDao.update(student);
//        int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "students",
//                "id = 1 and first_name = 'Max' and last_name = 'Gurin' and email = 'gurin@gmail.com' and parole = 'jhj6' and group_id = 3");
//        assertThat("Actual result does not match expected", actual, is(equalTo(1)));
//    }
//
//    @Test
//    void deleteStudent_shouldDeleteStudent_whenPassedId() {
//        studentDao.removeById(1);
//        int actualCountRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "students");
//        int actualCountRowsWithWhereClause = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "students", "id = 1");
//        assertThat("Actual result does not match", actualCountRows, is(equalTo(2)));
//        assertThat("Actual result does not match", actualCountRowsWithWhereClause, is(equalTo(0)));
//    }
//
//    @Test
//    void getStudentById_shouldReturnCorrectStudentByGivenId() {
//        List<Course> courses = new ArrayList<>();
//        courses.add(makeCourse(2, "History of Ukraine", 2));
//        Student expected = makeStudent(3, "Dony", "Zell", "dony@gmail.com", 3, courses);
//        Student actual = studentDao.getById(3).get();
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getStudentById_shouldReturnEmpty_whenStudentNotExist() {
//        Optional<Student> expected = Optional.empty();
//        Optional<Student> actual = studentDao.getById(4);
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getAll_shouldReturnAllStudents() {
//        List<Course> firstCourses = new ArrayList<>();
//        List<Course> secondCourses = new ArrayList<>();
//        List<Course> thirdCourses = new ArrayList<>();
//        firstCourses.add(makeCourse(1, "Math", 1));
//        secondCourses.add(makeCourse(2, "History of Ukraine", 2));
//        thirdCourses.add(makeCourse(3, "Physic", 3));
//        Student firstStudent = makeStudent(1, "Kate", "Moss", "kate@gmail.com", 2, thirdCourses);
//        Student secondStudent = makeStudent(2, "Mark", "Zorin", "mark@gmail.com", 1, firstCourses);
//        Student thirdStudent = makeStudent(3, "Dony", "Zell", "dony@gmail.com", 3, secondCourses);
//        List<Student> expected = Arrays.asList(firstStudent, secondStudent, thirdStudent);
//        List<Student> actual = studentDao.getAll();
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getAll_shouldReturnCorrectStudentRecordsCount() {
//        int actualStudentsSize = studentDao.getAll().size();
//        int expectedStudentsSize = JdbcTestUtils.countRowsInTable(jdbcTemplate, "students");
//        assertThat("Actual result does not match expected", actualStudentsSize, is(equalTo(expectedStudentsSize)));
//    }
//
//    @Test
//    void getAllAccordingLimitOffset_shouldReturnAllStudents_whenPassedPageWithLimitAndOffset() {
//        Page page = new Page(1, 1);
//        List<Course> courses = new ArrayList<>();
//        courses.add(makeCourse(1, "Math", 1));
//        Student student = makeStudent(2, "Mark", "Zorin", "mark@gmail.com", 1, courses);
//        List<Student> expected = Arrays.asList(student);
//        List<Student> actual = studentDao.getAll(page);
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getAllAccordingLimitOffset_shouldReturnCorrectStudentRecordsCount_whenPassedPageWithLimitAndOffset() {
//        Page page = new Page(1, 1);
//        int actualStudentSize = studentDao.getAll(page).size();
//        assertThat("Actual result does not match expected", actualStudentSize, is(equalTo(1)));
//    }
//
//    private static Student makeStudent(Integer id, String firstName, String lastName, String email, String parole, Integer groupId) {
//        return new Student.StudentBuilder()
//                .withId(id)
//                .withFirstName(firstName)
//                .withLastName(lastName)
//                .withEmail(email)
//                .withParole(parole)
//                .build();
//    }
//
//    private static Student makeStudent(Integer id, String firstName, String lastName, String email, Integer groupId, List<Course> courses) {
//        return new Student.StudentBuilder()
//                .withId(id)
//                .withFirstName(firstName)
//                .withLastName(lastName)
//                .withEmail(email)
//                .withCourses(courses)
//                .build();
//    }
//
//    private static Course makeCourse(Integer id, String courseName, Integer facultyId) {
//        return Course.builder()
//                .setId(id)
//                .setName(courseName)
//                .build();
//    }
//}
//
