package com.degtyaruk.university.dao.implhibernate;

import com.degtyaruk.university.config.SpringHibernateConfigTest;
import com.degtyaruk.university.dao.CourseDao;
import com.degtyaruk.university.dao.Page;
import com.degtyaruk.university.model.Course;
import com.degtyaruk.university.model.Faculty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("hibernate")
@ContextConfiguration(classes = {SpringHibernateConfigTest.class}, loader = AnnotationConfigContextLoader.class)
@SqlGroup({@Sql(value = "classpath:initdatabase.sql"), @Sql("classpath:fulltestdatabase.sql")})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CourseDaoHibernateImplTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CourseDao courseDao;

    @Test
    @Transactional
    @Commit
    void addCourse_shouldReturnNewCourse_whenAddNewCourse() {
        Course courseExpected = makeCourse(7, null, "Chemistry");
        Course course = makeCourseWithoutId("Chemistry");
        Course actual = courseDao.add(course);
        assertThat("Actual result does not match expected", actual, is(equalTo(courseExpected)));
    }

    @Test
    @Transactional
    @Commit
    void addCourse_shouldAddNewRecord_whenAddNewCourse() {
        Course course = makeCourseWithoutId("Physics");
        courseDao.add(course);
        int actual = entityManager.createQuery("SELECT COUNT(c) FROM Course c", Long.class)
                .getSingleResult()
                .intValue();
        assertThat("Actual result does not match expected", actual, is(equalTo(7)));
        int actualWithWhereClause = entityManager.createQuery("SELECT COUNT(c) FROM Course c WHERE c.name = :name", Long.class)
                .setParameter("name", "Physics")
                .getSingleResult()
                .intValue();
        assertThat("Actual result does not match expected", actualWithWhereClause, is(equalTo(1)));
    }

    @Test
    @Transactional
    @Commit
    void updateCourse_shouldUpdateCourse_whenPassedCourse() {
        Course course = makeCourse(1, null, "Mathematics");
        courseDao.update(course);
        int actual = entityManager.createQuery("SELECT COUNT(c) FROM Course c WHERE c.name = :name", Long.class)
                .setParameter("name", "Mathematics")
                .getSingleResult()
                .intValue();
        assertThat("Actual result does not match expected", actual, is(equalTo(1)));
    }

    @Test
    @Transactional
    @Commit
    void deleteCourse_shouldDeleteCourse_whenPassedId() {
        courseDao.removeById(6);
        int actualCountRows = entityManager.createQuery("SELECT COUNT(c) FROM Course c", Long.class)
                .getSingleResult()
                .intValue();
        int actualCountRowsWithWhereClause = entityManager.createQuery("SELECT COUNT(c) FROM Course c WHERE c.id = :id", Long.class)
                .setParameter("id", 6)
                .getSingleResult()
                .intValue();
        assertThat("Actual result does not match expected", actualCountRows, is(equalTo(5)));
        assertThat("Actual result does not match expected", actualCountRowsWithWhereClause, is(equalTo(0)));
    }

    @Test
    @Transactional
    @Commit
    void getCourseById_shouldReturnCorrectCourseByGivenId() {
        Course expected = makeCourse(3, null, "Physic");
        Course actual = courseDao.getById(3).orElse(null);
        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
    }

    @Test
    @Transactional
    @Commit
    void getCourseById_shouldReturnEmpty_whenCourseNotExist() {
        Optional<Course> actual = courseDao.getById(7);
        assertThat("Actual result does not match expected", actual, is(Optional.empty()));
    }

    @Test
    @Transactional
    @Commit
    void getAll_shouldReturnAllCourses() {
        Course firstCourse = makeCourse(1,null, "Math");
        Course secondCourse = makeCourse(2, null, "History of Ukraine");
        Course thirdCourse = makeCourse(3, null, "Physic");
        Course fourthCourse = makeCourse(4,null, "Geography");
        Course fifthCourse = makeCourse(5, null, "English");
        Course sixthCourse = makeCourse(6, null, "Chemistry");
        List<Course> expected = Arrays.asList(firstCourse, secondCourse, thirdCourse, fourthCourse, fifthCourse, sixthCourse);
        List<Course> actual = courseDao.getAll();
        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
    }

    @Test
    @Transactional
    @Commit
    void getAll_shouldReturnCorrectCourseRecordsCount() {
        int actualCourseSize = courseDao.getAll().size();
        int expectedCourseSize = entityManager.createQuery("SELECT COUNT(c) FROM Course c", Long.class)
                .getSingleResult()
                .intValue();
        assertThat("Actual result does not match expected", actualCourseSize, is(equalTo(expectedCourseSize)));
    }

    @Test
    @Transactional
    @Commit
    void getAllAccordingLimitOffset_shouldReturnAllCourses_whenPassedPageWithLimitAndOffset() {
        Page page = new Page(2, 1);
        Course secondCourse = makeCourse(2, null, "History of Ukraine");
        Course thirdCourse = makeCourse(3, null, "Physic");
        List<Course> expected = Arrays.asList(secondCourse, thirdCourse);
        List<Course> actual = courseDao.getAll(page);
        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
    }

    @Test
    @Transactional
    @Commit
    void getAllAccordingLimitOffset_shouldReturnCorrectCourseRecordsCount_whenPassedPageWithLimitAndOffset() {
        Page page = new Page(2, 1);
        int actualCourseSize = courseDao.getAll(page).size();
        assertThat("Actual result does not match expected", actualCourseSize, is(equalTo(2)));
    }

    private static Course makeCourse(Integer id, Faculty faculty, String name) {
        return Course.builder()
                .setId(id)
                .setFaculty(faculty)
                .setName(name)
                .build();
    }

    private static Course makeCourseWithoutId(String name) {
        return Course.builder()
                .setName(name)
                .build();
    }
}

