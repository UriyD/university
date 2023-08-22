//package com.degtyaruk.university.dao.impl;
//
//import com.degtyaruk.university.config.SpringConfigTest;
//import com.degtyaruk.university.dao.Page;
//import com.degtyaruk.university.dao.implhibernate.ProfessorDaoHibernateImpl;
//import com.degtyaruk.university.model.Course;
//import com.degtyaruk.university.model.Professor;
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
//class ProfessorDaoImplTest {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    @Autowired
//    private ProfessorDaoHibernateImpl professorDao;
//
//    @Test
//    void addProfessor_shouldReturnNewProfessor_whenAddNewProfessor() {
//        Professor expected = makeProfessor(4, "Max", "Gurin", "gurin@gmail.com", "12345");
//        Professor professor = makeProfessor(null, "Max", "Gurin", "gurin@gmail.com", "12345");
//        Professor actual = professorDao.add(professor);
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void addProfessor_shouldAddNewRecord_whenAddNewProfessor() {
//        Professor professor = makeProfessor(null, "Max", "Gurin", "gurin@gmail.com", "12345");
//        professorDao.add(professor);
//        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "professors");
//        assertThat("Actual result does not match expected", actual, is(equalTo(4)));
//        int actualWithWhereClause = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "professors",
//                "first_name = 'Max' and last_name = 'Gurin' and email = 'gurin@gmail.com'");
//        assertThat("Actual result does not match expected", actualWithWhereClause, is(equalTo(1)));
//    }
//
//    @Test
//    void updateProfessor_shouldUpdateProfessor_whenPassedProfessor() {
//        Professor professor = makeProfessor(3, "Max", "Gurin", "gurin@gmail.com", "12345");
//        professorDao.update(professor);
//        int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "professors",
//                "id = 3 and first_name = 'Max' and last_name = 'Gurin' and email = 'gurin@gmail.com'");
//        assertThat("Actual result does not match expected", actual, is(equalTo(1)));
//    }
//
//    @Test
//    void deleteProfessor_shouldDeleteProfessor_whenPassedId() {
//        professorDao.removeById(1);
//        int actualCountRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "professors");
//        int actualCountRowsWithWhereClause = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "professors", "id = 1");
//        assertThat("Actual result does not match", actualCountRows, is(equalTo(2)));
//        assertThat("Actual result does not match", actualCountRowsWithWhereClause, is(equalTo(0)));
//    }
//
//    @Test
//    void getProfessorById_shouldReturnCorrectProfessorByGivenId() {
//        List<Course> courses = new ArrayList<>();
//        courses.add(makeCourse(2, "History of Ukraine", 2));
//        Professor expected = makeProfessor(3, "Oleg", "Slon", "oleg@gmail.com", courses);
//        Professor actual = professorDao.getById(3).get();
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getProfessorById_shouldReturnEmpty_whenProfessorNotExist() {
//        Optional<Professor> expected = Optional.empty();
//        Optional<Professor> actual = professorDao.getById(4);
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getAll_shouldReturnAllProfessors() {
//        List<Course> firstCourses = new ArrayList<>();
//        List<Course> secondCourses = new ArrayList<>();
//        List<Course> thirdCourses = new ArrayList<>();
//        firstCourses.add(makeCourse(1, "Math", 1));
//        secondCourses.add(makeCourse(2, "History of Ukraine", 2));
//        thirdCourses.add(makeCourse(3, "Physic", 3));
//        Professor firstProfessor = makeProfessor(1, "Andrii", "Nemchinskiy", "andrii@gmail.com", firstCourses);
//        Professor secondProfessor = makeProfessor(2, "Karl", "Lagerfeld", "karl@gmail.com", thirdCourses);
//        Professor thirdProfessor = makeProfessor(3, "Oleg", "Slon", "oleg@gmail.com", secondCourses);
//        List<Professor> expected = Arrays.asList(firstProfessor, secondProfessor, thirdProfessor);
//        List<Professor> actual = professorDao.getAll();
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getAll_shouldReturnCorrectProfessorRecordsCount() {
//        int actualProfessorsSize = professorDao.getAll().size();
//        int expectedProfessorsSize = JdbcTestUtils.countRowsInTable(jdbcTemplate, "professors");
//        assertThat("Actual result does not match expected", actualProfessorsSize, is(equalTo(expectedProfessorsSize)));
//    }
//
//    @Test
//    void getAllAccordingLimitOffset_shouldReturnAllProfessors_whenPassedPageWithLimitAndOffset() {
//        Page page = new Page(1, 1);
//        List<Course> courses = new ArrayList<>();
//        courses.add(makeCourse(3, "Physic", 3));
//        Professor professor = makeProfessor(2, "Karl", "Lagerfeld", "karl@gmail.com", courses);
//        List<Professor> expected = Arrays.asList(professor);
//        List<Professor> actual = professorDao.getAll(page);
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getAllAccordingLimitOffset_shouldReturnCorrectProfessorRecordsCount_whenPassedPageWithLimitAndOffset() {
//        Page page = new Page(1, 1);
//        int actualProfessorSize = professorDao.getAll(page).size();
//        assertThat("Actual result does not match expected", actualProfessorSize, is(equalTo(1)));
//    }
//
//    private static Professor makeProfessor(Integer id, String firstName, String lastName, String email, String parole) {
//        return new Professor.ProfessorBuilder()
//                .withId(id)
//                .withFirstName(firstName)
//                .withLastName(lastName)
//                .withEmail(email)
//                .withParole(parole)
//                .build();
//    }
//
//    private static Professor makeProfessor(Integer id, String firstName, String lastName, String email, List<Course> courses) {
//        return new Professor.ProfessorBuilder()
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
//                .setFacultyId(facultyId)
//                .build();
//    }
//}
//
