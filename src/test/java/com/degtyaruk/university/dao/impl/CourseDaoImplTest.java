//package com.degtyaruk.university.dao.impl;
//
//import com.degtyaruk.university.config.SpringConfigTest;
//import com.degtyaruk.university.dao.Page;
//import com.degtyaruk.university.dao.implhibernate.CourseDaoHibernateImpl;
//import com.degtyaruk.university.model.Course;
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
//class CourseDaoImplTest {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    @Autowired
//    private CourseDaoHibernateImpl courseDao;
//
//    @Test
//    void addCourse_shouldReturnNewCourse_whenAddNewCourse() {
//        Course expected = makeCourse(7, 1, "Programming");
//        Course course = makeCourse(null, 1, "Programming");
//        Course actual = courseDao.add(course);
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void addCourse_shouldAddNewRecord_whenAddNewCourse() {
//        Course course = makeCourse(null, 1, "Programming");
//        courseDao.add(course);
//        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "courses");
//        assertThat("Actual result does not match expected", actual, is(equalTo(7)));
//        int actualWithWhereClause = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "courses", "course_name = 'Programming'");
//        assertThat("Actual result does not match expected", actualWithWhereClause, is(equalTo(1)));
//    }
//
//    @Test
//    void updateCourse_shouldUpdateCourse_whenPassedCourse() {
//        Course course = makeCourse(1, 2, "English");
//        courseDao.update(course);
//        int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "courses", "id = 1 and faculty_id = 2 and course_name = 'English'");
//        assertThat("Actual result does not match expected", actual, is(equalTo(1)));
//    }
//
//    @Test
//    void deleteCourse_shouldDeleteCourse_whenPassedId() {
//        courseDao.removeById(3);
//        int actualCountRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "courses");
//        int actualCountRowsWithWhereClause = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "courses", "id = 3");
//        assertThat("Actual result does not match", actualCountRows, is(equalTo(5)));
//        assertThat("Actual result does not match", actualCountRowsWithWhereClause, is(equalTo(0)));
//    }
//
//    @Test
//    void getCourseById_shouldReturnCorrectCourseByGivenId() {
//        Course expected = makeCourse(1, 1, "Math");
//        Course actual = courseDao.getById(1).get();
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getCourseById_shouldReturnEmpty_whenCourseNotExist() {
//        Optional<Course> expected = Optional.empty();
//        Optional<Course> actual = courseDao.getById(7);
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getAll_shouldReturnAllCourses() {
//        Course firstCourse = makeCourse(1, 1, "Math");
//        Course secondCourse = makeCourse(2, 2, "History of Ukraine");
//        Course thirdCourse = makeCourse(3, 3, "Physic");
//        Course fourthCourse = makeCourse(4, 1, "Geography");
//        Course fifthCourse = makeCourse(5, 2, "English");
//        Course sixthCourse = makeCourse(6, 2, "Chemistry");
//        List<Course> expected = Arrays.asList(firstCourse, secondCourse, thirdCourse, fourthCourse, fifthCourse, sixthCourse);
//        List<Course> actual = courseDao.getAll();
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getAll_shouldReturnCorrectCourseRecordsCount() {
//        int actualCourseSize = courseDao.getAll().size();
//        int expectedCourseSize = JdbcTestUtils.countRowsInTable(jdbcTemplate, "courses");
//        assertThat("Actual result does not match expected", actualCourseSize, is(equalTo(expectedCourseSize)));
//    }
//
//    @Test
//    void getAllAccordingLimitOffset_shouldReturnAllCourses_whenPassedPageWithLimitAndOffset() {
//        Page page = new Page(2, 1);
//        Course secondCourse = makeCourse(2, 2, "History of Ukraine");
//        Course thirdCourse = makeCourse(3, 3,"Physic");
//        List<Course> expected = Arrays.asList(secondCourse, thirdCourse);
//        List<Course> actual = courseDao.getAll(page);
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getAllAccordingLimitOffset_shouldReturnCorrectCourseRecordsCount_whenPassedPageWithLimitAndOffset() {
//        Page page = new Page(2, 1);
//        int actualCourseSize = courseDao.getAll(page).size();
//        assertThat("Actual result does not match expected", actualCourseSize, is(equalTo(2)));
//    }
//
//    private static Course makeCourse(Integer id, Integer facultyId, String courseName) {
//        return Course.builder()
//                .setId(id)
//                .setFacultyId(facultyId)
//                .setName(courseName)
//                .build();
//    }
//}
//
