package com.degtyaruk.university.dao.impl;

import com.degtyaruk.university.config.SpringConfigTest;
import com.degtyaruk.university.dao.ClassroomDao;
import com.degtyaruk.university.dao.Page;
import com.degtyaruk.university.dao.implhibernate.ClassroomDaoHibernateImpl;
import com.degtyaruk.university.model.Classroom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfigTest.class}, loader = AnnotationConfigContextLoader.class)
@SqlGroup({@Sql("classpath:initdatabase.sql"), @Sql("classpath:fulltestdatabase.sql")})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ClassroomDaoImplTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ClassroomDao classroomDao;

    @Test
    void addClassroom_shouldReturnNewClassroom_whenAddNewClassroom() {
        Classroom classroomExpected = makeClassroom(4, 95);
        Classroom classroom = makeClassroom(null, 95);
        Classroom actual = classroomDao.add(classroom);
        assertThat("Actual result does not match expected", actual, is(equalTo(classroomExpected)));
    }

    @Test
    void addClassroom_shouldAddNewRecord_whenAddNewClassroom() {
        Classroom classroom = makeClassroom(null, 4562);
        classroomDao.add(classroom);
        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "classrooms");
        assertThat("Actual result does not match expected", actual, is(equalTo(4)));
        int actualWithWhereClause = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "classrooms", "number = 4562");
        assertThat("Actual result does not match expected", actualWithWhereClause, is(equalTo(1)));
    }

    @Test
    void updateClassroom_shouldUpdateClassroom_whenPassedClassroom() {
        Classroom classroom = makeClassroom(1, 999);
        classroomDao.update(classroom);
        int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "classrooms", "number = 999");
        assertThat("Actual result does not match expected", actual, is(equalTo(1)));
    }

    @Test
    void deleteClassroom_shouldDeleteClassroom_whenPassedId() {
        classroomDao.removeById(3);
        int actualCountRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "classrooms");
        int actualCountRowsWithWhereClause = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "classrooms", "id = 3");
        assertThat("Actual result does not match expected", actualCountRows, is(equalTo(2)));
        assertThat("Actual result does not match expected", actualCountRowsWithWhereClause, is(equalTo(0)));
    }

    @Test
    void getClassroomById_shouldReturnCorrectClassroomByGivenId() {
        Classroom expected = makeClassroom(1, 36);
        Classroom actual = classroomDao.getById(1).get();
        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
    }

    @Test
    void getClassroomById_shouldReturnEmpty_whenClassroomNotExist() {
        Optional<Classroom> expected = Optional.empty();
        Optional<Classroom> actual = classroomDao.getById(4);
        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
    }

    @Test
    void getAll_shouldReturnAllClassrooms() {
        Classroom firstClassroom = makeClassroom(1, 36);
        Classroom secondClassroom = makeClassroom(2, 47);
        ;
        Classroom thirdClassroom = makeClassroom(3, 56);
        ;
        List<Classroom> expected = Arrays.asList(firstClassroom, secondClassroom, thirdClassroom);
        List<Classroom> actual = classroomDao.getAll();
        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
    }

    @Test
    void getAll_shouldReturnCorrectClassroomRecordsCount() {
        int actualClassroomSize = classroomDao.getAll().size();
        int expectedClassroomSize = JdbcTestUtils.countRowsInTable(jdbcTemplate, "classrooms");
        assertThat("Actual result does not match expected", actualClassroomSize, is(equalTo(expectedClassroomSize)));
    }

    @Test
    void getAllAccordingLimitOffset_shouldReturnAllClassrooms_whenPassedPageWithLimitAndOffset() {
        Page page = new Page(2, 1);
        Classroom secondClassroom = makeClassroom(2, 47);
        ;
        Classroom thirdClassroom = makeClassroom(3, 56);
        ;
        List<Classroom> expected = Arrays.asList(secondClassroom, thirdClassroom);
        List<Classroom> actual = classroomDao.getAll(page);
        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
    }

    @Test
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
}

