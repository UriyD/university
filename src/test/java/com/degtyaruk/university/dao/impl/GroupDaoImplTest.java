//package com.degtyaruk.university.dao.impl;
//
//import com.degtyaruk.university.config.SpringConfigTest;
//import com.degtyaruk.university.dao.Page;
//import com.degtyaruk.university.dao.implhibernate.GroupDaoHibernateImpl;
//import com.degtyaruk.university.model.Group;
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
//class GroupDaoImplTest {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    @Autowired
//    private GroupDaoHibernateImpl groupDao;
//
//    @Test
//    void addGroup_shouldReturnNewGroup_whenAddNewGroup() {
//        Group expected = makeGroup(9, 2, "fg-26");
//        Group group = makeGroup(null, 2, "fg-26");
//        Group actual = groupDao.add(group);
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void addGroup_shouldAddNewRecord_whenAddNewGroup() {
//        Group group = makeGroup(null,2, "fg-26");
//        groupDao.add(group);
//        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "groups");
//        assertThat("Actual result does not match expected", actual, is(equalTo(9)));
//        int actualWithWhereClause = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "groups", "faculty_id = 2 and group_name = 'fg-26'");
//        assertThat("Actual result does not match expected", actualWithWhereClause, is(equalTo(1)));
//    }
//
//    @Test
//    void updateGroup_shouldUpdateGroup_whenPassedGroup() {
//        Group group = makeGroup(1, 3,"zk-90");
//        groupDao.update(group);
//        int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "groups", "id = 1 and faculty_id = 3 and group_name ='zk-90'");
//        assertThat("Actual result does not match expected", actual, is(equalTo(1)));
//    }
//
//    @Test
//    void deleteGroup_shouldDeleteGroup_whenPassedId() {
//        groupDao.removeById(1);
//        int actualCountRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "groups");
//        int actualCountRowsWithWhereClause = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "groups", "id = 1");
//        assertThat("Actual result does not match", actualCountRows, is(equalTo(7)));
//        assertThat("Actual result does not match", actualCountRowsWithWhereClause, is(equalTo(0)));
//    }
//
//    @Test
//    void getGroupById_shouldReturnCorrectGroupByGivenId() {
//        List<Student> students = new ArrayList<>();
//        students.add(makeStudent(3,3, "Dony", "Zell", "dony@gmail.com"));
//        Group expected = makeGroup(3, 3, "jk-56", students);
//        Group actual = groupDao.getById(3).get();
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getGroupById_shouldReturnEmpty_whenGroupNotExist() {
//        Optional<Group> expected = Optional.empty();
//        Optional<Group> actual = groupDao.getById(9);
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getAll_shouldReturnAllGroups() {
//        List<Student> firstStudents = new ArrayList<>();
//        List<Student> secondStudents = new ArrayList<>();
//        List<Student> thirdStudents = new ArrayList<>();
//        List<Student> fourthStudents = new ArrayList<>();
//        firstStudents.add(makeStudent(1,2, "Kate", "Moss", "kate@gmail.com"));
//        secondStudents.add(makeStudent(2,1, "Mark", "Zorin", "mark@gmail.com"));
//        thirdStudents.add(makeStudent(3,3, "Dony", "Zell", "dony@gmail.com"));
//        fourthStudents.add(makeStudent(0,0, null, null, null));
//        Group firstGroup = makeGroup(1,1,"jk-56", secondStudents);
//        Group secondGroup = makeGroup(2,2, "ag-21", firstStudents);
//        Group thirdGroup = makeGroup(3,3, "jk-56", thirdStudents);
//        Group fourthGroup = makeGroup(4,2, "rt-56", fourthStudents);
//        Group fifthGroup = makeGroup(5,3,"oo-89", fourthStudents);
//        Group sixthGroup = makeGroup(6,1, "aa-22", fourthStudents);
//        Group seventhGroup = makeGroup(7,2, "tt-33", fourthStudents);
//        Group eighthGroup = makeGroup(8,2, "ii-77", fourthStudents);
//        List<Group> expected = Arrays.asList(firstGroup, secondGroup, thirdGroup, fourthGroup, fifthGroup, sixthGroup, seventhGroup, eighthGroup);
//        List<Group> actual = groupDao.getAll();
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getAll_shouldReturnCorrectGroupRecordsCount() {
//        int actualGroupsSize = groupDao.getAll().size();
//        int expectedGroupsSize = JdbcTestUtils.countRowsInTable(jdbcTemplate, "groups");
//        assertThat("Actual result does not match expected", actualGroupsSize, is(equalTo(expectedGroupsSize)));
//    }
//
//    @Test
//    void getAllAccordingLimitOffset_shouldReturnAllGroups_whenPassedPageWithLimitAndOffset() {
//        Page page = new Page(1, 1);
//        List<Student> students = new ArrayList<>();
//        students.add(makeStudent(1,2, "Kate", "Moss", "kate@gmail.com"));
//        Group group = makeGroup(2, 2,"ag-21", students);
//        List<Group> expected = Arrays.asList(group);
//        List<Group> actual = groupDao.getAll(page);
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getAllAccordingLimitOffset_shouldReturnCorrectGroupRecordsCount_whenPassedPageWithLimitAndOffset() {
//        Page page = new Page(1, 1);
//        int actualGroupSize = groupDao.getAll(page).size();
//        assertThat("Actual result does not match expected", actualGroupSize, is(equalTo(1)));
//    }
//
//    private static Group makeGroup(Integer id, Integer facultyId, String name) {
//        return Group.builder()
//                .setId(id)
//                .setName(name)
//                .build();
//    }
//
//    private static Group makeGroup(Integer id, Integer facultyId, String name, List<Student> students) {
//        return Group.builder()
//                .setId(id)
//                .setName(name)
//                .setStudents(students)
//                .build();
//    }
//
//    private static Student makeStudent(Integer id, Integer groupId, String firstName, String lastName, String email) {
//        return new Student.StudentBuilder()
//                .withId(id)
//                .withFirstName(firstName)
//                .withLastName(lastName)
//                .withEmail(email)
//                .build();
//    }
//}
//
