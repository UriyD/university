//package com.degtyaruk.university.dao.impl;
//
//import com.degtyaruk.university.config.SpringConfigTest;
//import com.degtyaruk.university.dao.Page;
//import com.degtyaruk.university.dao.implhibernate.FacultyDaoHibernateImpl;
//import com.degtyaruk.university.model.Course;
//import com.degtyaruk.university.model.Faculty;
//import com.degtyaruk.university.model.Group;
//import org.hamcrest.Matchers;
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
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import static org.hamcrest.CoreMatchers.allOf;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.contains;
//import static org.hamcrest.Matchers.containsInAnyOrder;
//import static org.hamcrest.Matchers.equalTo;
//import static org.hamcrest.Matchers.hasProperty;
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.samePropertyValuesAs;
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {SpringConfigTest.class}, loader = AnnotationConfigContextLoader.class)
//@SqlGroup({@Sql("classpath:initdatabase.sql"), @Sql("classpath:fulltestdatabase.sql")})
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//class FacultyDaoImplTest {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    @Autowired
//    private FacultyDaoHibernateImpl facultyDao;
//
//    @Test
//    void addFaculty_shouldReturnNewFaculty_whenAddNewFaculty() {
//        Faculty expected = makeFaculty(4, "Chemistry");
//        Faculty faculty = makeFaculty(null, "Chemistry");
//        Faculty actual = facultyDao.add(faculty);
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void addFaculty_shouldAddNewRecord_whenAddNewFaculty() {
//        Faculty faculty = makeFaculty(null, "Programming");
//        facultyDao.add(faculty);
//        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "faculties");
//        assertThat("Actual result does not match expected", actual, is(equalTo(4)));
//        int actualWithWhereClause = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "faculties", "faculty_name = 'Programming'");
//        assertThat("Actual result does not match expected", actualWithWhereClause, is(equalTo(1)));
//    }
//
//    @Test
//    void updateFaculty_shouldUpdateFaculty_whenPassedFaculty() {
//        Faculty faculty = makeFaculty(2, "Programming");
//        facultyDao.update(faculty);
//        int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "faculties", "id = 2 and faculty_name = 'Programming'");
//        assertThat("Actual result does not match expected", actual, is(equalTo(1)));
//    }
//
//    @Test
//    void deleteFaculty_shouldDeleteFaculty_whenPassedId() {
//        facultyDao.removeById(1);
//        int actualCountRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "faculties");
//        int actualCountRowsWithWhereClause = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "faculties", "id = 1");
//        assertThat("Actual result does not match", actualCountRows, is(equalTo(2)));
//        assertThat("Actual result does not match", actualCountRowsWithWhereClause, is(equalTo(0)));
//    }
//
//    @Test
//    void getFacultyById_shouldReturnCorrectFacultyByGivenId() {
//        List<Course> courses = new ArrayList<>();
//        List<Group> groups = new ArrayList<>();
//        courses.add(makeCourse(2, 2, "History of Ukraine"));
//        courses.add(makeCourse(5, 2, "English"));
//        courses.add(makeCourse(6, 2, "Chemistry"));
//        groups.add(makeGroup(2, 2, "ag-21"));
//        groups.add(makeGroup(4, 2, "rt-56"));
//        groups.add(makeGroup(7, 2, "tt-33"));
//        groups.add(makeGroup(8, 2, "ii-77"));
//        Faculty expected = makeFaculty(2, "History faculty", courses, groups);
//        Faculty actual = facultyDao.getById(2).get();
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getFacultyById_shouldReturnEmpty_whenFacultyNotExist() {
//        Optional<Faculty> expected = Optional.empty();
//        Optional<Faculty> actual = facultyDao.getById(4);
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getAll_shouldReturnAllFaculties() {
//        List<Course> firstCourse = new ArrayList<>();
//        List<Course> secondCourse = new ArrayList<>();
//        List<Course> thirdCourse = new ArrayList<>();
//        List<Group> firstGroup = new ArrayList<>();
//        List<Group> secondGroup = new ArrayList<>();
//        List<Group> thirdGroup = new ArrayList<>();
//        firstCourse.add(makeCourse(1, 1, "Math"));
//        firstCourse.add(makeCourse(4, 1, "Geography"));
//        secondCourse.add(makeCourse(2, 2, "History of Ukraine"));
//        secondCourse.add(makeCourse(5, 2, "English"));
//        secondCourse.add(makeCourse(6, 2, "Chemistry"));
//        thirdCourse.add(makeCourse(3, 3, "Physic"));
//        firstGroup.add(makeGroup(1, 1, "jk-56"));
//        firstGroup.add(makeGroup(6, 1, "aa-22"));
//        secondGroup.add(makeGroup(2, 2, "ag-21"));
//        secondGroup.add(makeGroup(4, 2, "rt-56"));
//        secondGroup.add(makeGroup(7, 2, "tt-33"));
//        secondGroup.add(makeGroup(8, 2, "ii-77"));
//        thirdGroup.add(makeGroup(3, 3, "jk-56"));
//        thirdGroup.add(makeGroup(5, 3, "oo-89"));
//        Faculty firstFaculty = makeFaculty(1, "Cybernetics faculty", firstCourse, firstGroup);
//        Faculty secondFaculty = makeFaculty(2, "History faculty", secondCourse, secondGroup);
//        Faculty thirdFaculty = makeFaculty(3, "Geography", thirdCourse, thirdGroup);
//        List<Faculty> expected = Arrays.asList(firstFaculty, secondFaculty, thirdFaculty);
//        List<Faculty> actual = facultyDao.getAll();
//        assertThat(actual, contains(expected.stream()
//                .map(e -> allOf(samePropertyValuesAs(e, "courses", "groups"),
//                        hasProperty("courses", containsInAnyOrder(e.getCourses().toArray())),
//                        hasProperty("groups", containsInAnyOrder(e.getGroups().toArray()))))
//                .collect(Collectors.toList())));
//    }
//
//    @Test
//    void getAll_shouldReturnCorrectFacultyRecordsCount() {
//        int actualFacultySize = facultyDao.getAll().size();
//        int expectedFacultySize = JdbcTestUtils.countRowsInTable(jdbcTemplate, "faculties");
//        assertThat("Actual result does not match expected", actualFacultySize, is(equalTo(expectedFacultySize)));
//    }
//
//    @Test
//    void getAllAccordingLimitOffset_shouldReturnAllCourses_whenPassedPageWithLimitAndOffset() {
//        Page page = new Page(3, 1);
//        List<Course> courses = new ArrayList<>();
//        List<Group> groups = new ArrayList<>();
//        courses.add(makeCourse(4, 1, "Geography"));
//        courses.add(makeCourse(1, 1, "Math"));
//        groups.add(makeGroup(1, 1, "jk-56"));
//        groups.add(makeGroup(6, 1, "aa-22"));
//        Faculty faculty = makeFaculty(1, "Cybernetics faculty", courses, groups);
//        List<Faculty> expected = Arrays.asList(faculty);
//        List<Faculty> actual = facultyDao.getAll(page);
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getAllAccordingLimitOffset_shouldReturnCorrectFacultyRecordsCount_whenPassedPageWithLimitAndOffset() {
//        Page page = new Page(1, 1);
//        int actualCourseSize = facultyDao.getAll(page).size();
//        assertThat("Actual result does not match expected", actualCourseSize, is(equalTo(1)));
//    }
//
//    private static Faculty makeFaculty(Integer id, String name, List<Course> courses, List<Group> groups) {
//        return Faculty.builder()
//                .setId(id)
//                .setName(name)
//                .setCourses(courses)
//                .setGroups(groups)
//                .build();
//    }
//
//    private static Faculty makeFaculty(Integer id, String name) {
//        return Faculty.builder()
//                .setId(id)
//                .setName(name)
//                .build();
//    }
//
//    private static Course makeCourse(Integer id, Integer facultyId, String name) {
//        return Course.builder()
//                .setId(id)
//                .setFacultyId(facultyId)
//                .setName(name)
//                .build();
//    }
//
//    private static Group makeGroup(Integer id, Integer facultyId, String name) {
//        return Group.builder()
//                .setId(id)
//                .setFacultyId(facultyId)
//                .setName(name)
//                .build();
//    }
//}
//
