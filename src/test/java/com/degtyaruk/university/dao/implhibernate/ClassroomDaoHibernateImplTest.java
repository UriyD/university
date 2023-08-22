package com.degtyaruk.university.dao.implhibernate;

import com.degtyaruk.university.config.SpringHibernateConfigTest;
import com.degtyaruk.university.dao.ClassroomDao;
import com.degtyaruk.university.dao.Page;
import com.degtyaruk.university.model.Classroom;
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
class ClassroomDaoHibernateImplTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ClassroomDao classroomDao;

    @Test
    @Transactional
    @Commit
    void addClassroom_shouldReturnNewClassroom_whenAddNewClassroom() {
        Classroom classroomExpected = makeClassroom(4, 95);
        Classroom classroom = makeClassroomWithoutId(95);
        Classroom actual = classroomDao.add(classroom);
        assertThat("Actual result does not match expected", actual, is(equalTo(classroomExpected)));
    }

    @Test
    @Transactional
    @Commit
    void addClassroom_shouldAddNewRecord_whenAddNewClassroom() {
        Classroom classroom = makeClassroom(null, 4562);
        classroomDao.add(classroom);
        int actual = entityManager.createQuery("SELECT COUNT(c) FROM Classroom c", Long.class)
                .getSingleResult()
                .intValue();
        assertThat("Actual result does not match expected", actual, is(equalTo(4)));
        int actualWithWhereClause = entityManager.createQuery("SELECT COUNT(c) FROM Classroom c WHERE c.number = :number", Long.class)
                .setParameter("number", 4562)
                .getSingleResult()
                .intValue();
        assertThat("Actual result does not match expected", actualWithWhereClause, is(equalTo(1)));
    }

    @Test
    @Transactional
    @Commit
    void updateClassroom_shouldUpdateClassroom_whenPassedClassroom() {
        Classroom classroom = makeClassroom(1, 999);
        classroomDao.update(classroom);
        int actual = entityManager.createQuery("SELECT COUNT(c) FROM Classroom c WHERE c.number = :number", Long.class)
                .setParameter("number", 999)
                .getSingleResult()
                .intValue();
        assertThat("Actual result does not match expected", actual, is(equalTo(1)));
    }

    @Test
    @Transactional
    @Commit
    void deleteClassroom_shouldDeleteClassroom_whenPassedId() {
        classroomDao.removeById(3);
        int actualCountRows = entityManager.createQuery("SELECT COUNT(c) FROM Classroom c", Long.class)
                .getSingleResult()
                .intValue();
        int actualCountRowsWithWhereClause = entityManager.createQuery("SELECT COUNT(c) FROM Classroom c WHERE c.id = :id", Long.class)
                .setParameter("id", 3)
                .getSingleResult()
                .intValue();
        assertThat("Actual result does not match expected", actualCountRows, is(equalTo(2)));
        assertThat("Actual result does not match expected", actualCountRowsWithWhereClause, is(equalTo(0)));
    }

    @Test
    @Transactional
    @Commit
    void getClassroomById_shouldReturnCorrectClassroomByGivenId() {
        Classroom expected = makeClassroom(1, 36);
        Classroom actual = classroomDao.getById(1).orElse(null);
        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
    }

    @Test
    @Transactional
    @Commit
    void getClassroomById_shouldReturnEmpty_whenClassroomNotExist() {
        Optional<Classroom> actual = classroomDao.getById(4);
        assertThat("Actual result does not match expected", actual, is(Optional.empty()));
    }

    @Test
    @Transactional
    @Commit
    void getAll_shouldReturnAllClassrooms() {
        Classroom firstClassroom = makeClassroom(1, 36);
        Classroom secondClassroom = makeClassroom(2, 47);
        Classroom thirdClassroom = makeClassroom(3, 56);
        List<Classroom> expected = Arrays.asList(firstClassroom, secondClassroom, thirdClassroom);
        List<Classroom> actual = classroomDao.getAll();
        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
    }

    @Test
    @Transactional
    @Commit
    void getAll_shouldReturnCorrectClassroomRecordsCount() {
        int actualClassroomSize = classroomDao.getAll().size();
        int expectedClassroomSize = entityManager.createQuery("SELECT COUNT(c) FROM Classroom c", Long.class)
                .getSingleResult()
                .intValue();
        assertThat("Actual result does not match expected", actualClassroomSize, is(equalTo(expectedClassroomSize)));
    }

    @Test
    @Transactional
    @Commit
    void getAllAccordingLimitOffset_shouldReturnAllClassrooms_whenPassedPageWithLimitAndOffset() {
        Page page = new Page(2, 1);
        Classroom secondClassroom = makeClassroom(2, 47);
        Classroom thirdClassroom = makeClassroom(3, 56);
        List<Classroom> expected = Arrays.asList(secondClassroom, thirdClassroom);
        List<Classroom> actual = classroomDao.getAll(page);
        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
    }

    @Test
    @Transactional
    @Commit
    void getAllAccordingLimitOffset_shouldReturnCorrectClassroomRecordsCount_whenPassedPageWithLimitAndOffset() {
        Page page = new Page(2, 1);
        int actualClassroomSize = classroomDao.getAll(page).size();
        assertThat("Actual result does not match expected", actualClassroomSize, is(equalTo(2)));
    }

    private static Classroom makeClassroom(Integer id, Integer number) {
        return Classroom.builder()
                .setId(id)
                .setNumber(number)
                .build();
    }

    private static Classroom makeClassroomWithoutId(Integer number) {
        return Classroom.builder()
                .setNumber(number)
                .build();
    }
}

