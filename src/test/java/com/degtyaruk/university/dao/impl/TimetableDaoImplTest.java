//package com.degtyaruk.university.dao.impl;
//
//import com.degtyaruk.university.config.SpringConfigTest;
//import com.degtyaruk.university.dao.Page;
//import com.degtyaruk.university.dao.implhibernate.TimetableDaoHibernateImpl;
//import com.degtyaruk.university.model.Classroom;
//import com.degtyaruk.university.model.Course;
//import com.degtyaruk.university.model.Group;
//import com.degtyaruk.university.model.Lecture;
//import com.degtyaruk.university.model.Professor;
//import com.degtyaruk.university.model.Timetable;
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
//import java.time.LocalDate;
//import java.time.LocalTime;
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
//class TimetableDaoImplTest {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    @Autowired
//    private TimetableDaoHibernateImpl timetableDao;
//
//    @Test
//    void addTimetable_shouldReturnNewTimetable_whenAddNewTimetable() {
//        LocalDate date = LocalDate.of(2023, 5, 26);
//        Timetable expected = makeTimetable(4, date);
//        Timetable timetable = makeTimetable(null, date);
//        Timetable actual = timetableDao.add(timetable);
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void addTimetable_shouldAddNewRecord_whenAddNewTimetable() {
//        LocalDate date = LocalDate.of(2023, 5, 26);
//        Timetable timetable = makeTimetable(null, date);
//        timetableDao.add(timetable);
//        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "timetables");
//        assertThat("Actual result does not match expected", actual, is(equalTo(4)));
//        int actualWithWhereClause = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "timetables",
//                "date = '2023-05-26'");
//        assertThat("Actual result does not match expected", actualWithWhereClause, is(equalTo(1)));
//    }
//
//    @Test
//    void updateTimetable_shouldUpdateTimetable_whenPassedTimetable() {
//        LocalDate date = LocalDate.of(2023, 12, 2);
//        Timetable timetable = makeTimetable(1, date);
//        timetableDao.update(timetable);
//        int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "timetables",
//                "id = 1 and date = '2023-12-02'");
//        assertThat("Actual result does not match expected", actual, is(equalTo(1)));
//    }
//
//    @Test
//    void deleteTimetable_shouldDeleteTimetable_whenPassedId() {
//        timetableDao.removeById(1);
//        int actualCountRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "timetables");
//        int actualCountRowsWithWhereClause = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "timetables", "id = 1");
//        assertThat("Actual result does not match", actualCountRows, is(equalTo(2)));
//        assertThat("Actual result does not match", actualCountRowsWithWhereClause, is(equalTo(0)));
//    }
//
//    @Test
//    void getTimetableById_shouldReturnCorrectTimetableByGivenId() {
//        LocalTime timeStart = LocalTime.of(9, 5, 0);
//        LocalTime timeFinish = LocalTime.of(10, 35, 0);
//        Course course = makeCourse(2, "History of Ukraine", 2);
//        Group group = makeGroup(3, 3, "jk-56");
//        Classroom classroom = makeClassroom(1, 36);
//        Professor professor = makeProfessor(1, "Andrii", "Nemchinskiy", "andrii@gmail.com");
//        Lecture lecture = makeLecture(3, timeStart, timeFinish, course, group, classroom, professor, 3);
//        List<Lecture> lectures = new ArrayList<>();
//        lectures.add(lecture);
//        LocalDate date = LocalDate.of(2023, 11, 23);
//        Timetable expected = makeTimetable(3, date, lectures);
//        Timetable actual = timetableDao.getById(3).get();
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getTimetableById_shouldReturnEmpty_whenTimetableNotExist() {
//        Optional<Timetable> expected = Optional.empty();
//        Optional<Timetable> actual = timetableDao.getById(4);
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getAll_shouldReturnAllTimetables() {
//        LocalTime timeStartFirst = LocalTime.of(11, 40, 0);
//        LocalTime timeFinishFirst = LocalTime.of(13, 10, 0);
//        Course courseFirst = makeCourse(1, "Math", 1);
//        Group groupFirst = makeGroup(1, 1, "jk-56");
//        Classroom classroomFirst = makeClassroom(3, 56);
//        Professor professorFirst = makeProfessor(2, "Karl", "Lagerfeld", "karl@gmail.com");
//        Lecture lectureFirst = makeLecture(2, timeStartFirst, timeFinishFirst, courseFirst, groupFirst, classroomFirst, professorFirst, 1);
//        List<Lecture> lecturesFirst = new ArrayList<>();
//        lecturesFirst.add(lectureFirst);
//        LocalDate dateFirst = LocalDate.of(2023, 11, 21);
//        Timetable firstTimetable = makeTimetable(1, dateFirst, lecturesFirst);
//
//        LocalTime timeStartSecond = LocalTime.of(9, 5, 0);
//        LocalTime timeFinishSecond = LocalTime.of(10, 35, 0);
//        Course courseSecond = makeCourse(3, "Physic", 3);
//        Group groupSecond = makeGroup(2, 2, "ag-21");
//        Classroom classroomSecond = makeClassroom(2, 47);
//        Professor professorSecond = makeProfessor(2, "Karl", "Lagerfeld", "karl@gmail.com");
//        Lecture lectureSecond = makeLecture(1, timeStartSecond, timeFinishSecond, courseSecond, groupSecond, classroomSecond, professorSecond, 2);
//        List<Lecture> lecturesSecond = new ArrayList<>();
//        lecturesSecond.add(lectureSecond);
//        LocalDate dateSecond = LocalDate.of(2023, 11, 22);
//        Timetable secondTimetable = makeTimetable(2, dateSecond, lecturesSecond);
//
//        LocalTime timeStartThird = LocalTime.of(9, 5, 0);
//        LocalTime timeFinishThird = LocalTime.of(10, 35, 0);
//        Course courseThird = makeCourse(2, "History of Ukraine", 2);
//        Group groupThird = makeGroup(3, 3, "jk-56");
//        Classroom classroomThird = makeClassroom(1, 36);
//        Professor professorThird = makeProfessor(1, "Andrii", "Nemchinskiy", "andrii@gmail.com");
//        Lecture lectureThird = makeLecture(3, timeStartThird, timeFinishThird, courseThird, groupThird, classroomThird, professorThird, 3);
//        List<Lecture> lecturesThird = new ArrayList<>();
//        lecturesThird.add(lectureThird);
//        LocalDate dateThird = LocalDate.of(2023, 11, 23);
//        Timetable thirdTimetable = makeTimetable(3, dateThird, lecturesThird);
//
//        List<Timetable> expected = Arrays.asList(firstTimetable, secondTimetable, thirdTimetable);
//        List<Timetable> actual = timetableDao.getAll();
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getAll_shouldReturnCorrectTimetableRecordsCount() {
//        int actualTimetablesSize = timetableDao.getAll().size();
//        int expectedTimetablesSize = JdbcTestUtils.countRowsInTable(jdbcTemplate, "timetables");
//        assertThat("Actual result does not match expected", actualTimetablesSize, is(equalTo(expectedTimetablesSize)));
//    }
//
//    @Test
//    void getAllAccordingLimitOffset_shouldReturnAllTimetables_whenPassedPageWithLimitAndOffset() {
//        Page page = new Page(1, 1);
//        LocalTime timeStart = LocalTime.of(9, 5, 0);
//        LocalTime timeFinish = LocalTime.of(10, 35, 0);
//        Course course = makeCourse(3, "Physic", 3);
//        Group group = makeGroup(2, 2, "ag-21");
//        Classroom classroom = makeClassroom(2, 47);
//        Professor professor = makeProfessor(2, "Karl", "Lagerfeld", "karl@gmail.com");
//        Lecture lecture = makeLecture(1, timeStart, timeFinish, course, group, classroom, professor, 2);
//        List<Lecture> lectures = new ArrayList<>();
//        lectures.add(lecture);
//        LocalDate date = LocalDate.of(2023, 11, 22);
//        Timetable timetable = makeTimetable(2, date, lectures);
//        List<Timetable> expected = Arrays.asList(timetable);
//        List<Timetable> actual = timetableDao.getAll(page);
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getAllAccordingLimitOffset_shouldReturnCorrectTimetableRecordsCount_whenPassedPageWithLimitAndOffset() {
//        Page page = new Page(1, 1);
//        int actualTimetableSize = timetableDao.getAll(page).size();
//        assertThat("Actual result does not match expected", actualTimetableSize, is(equalTo(1)));
//    }
//
//    private static Timetable makeTimetable(Integer id, LocalDate date) {
//        return Timetable.builder()
//                .setId(id)
//                .setDate(date)
//                .build();
//    }
//
//    private static Course makeCourse(Integer id, String courseName, Integer facultyId) {
//        return Course.builder()
//                .setId(id)
//                .setFacultyId(facultyId)
//                .setName(courseName)
//                .build();
//    }
//
//    private static Group makeGroup(Integer id, Integer facultyId, String groupName) {
//        return Group.builder()
//                .setId(id)
//                .setFacultyId(facultyId)
//                .setName(groupName)
//                .build();
//    }
//
//    private static Classroom makeClassroom(Integer id, Integer number) {
//        return Classroom.builder()
//                .setId(id)
//                .setNumber(number)
//                .build();
//    }
//
//    private static Professor makeProfessor(Integer id, String firstName, String lastName, String email) {
//        return new Professor.ProfessorBuilder()
//                .withId(id)
//                .withFirstName(firstName)
//                .withLastName(lastName)
//                .withEmail(email)
//                .build();
//    }
//
//    private static Timetable makeTimetable(Integer id, LocalDate date, List<Lecture> lectures) {
//        return Timetable.builder()
//                .setId(id)
//                .setDate(date)
//                .setLectures(lectures)
//                .build();
//    }
//
//
//    private static Lecture makeLecture(Integer id, LocalTime timeStartLecture, LocalTime timeEndLecture, Course course, Group group, Classroom classroom, Professor professor, Integer timetableId) {
//        return Lecture.builder()
//                .setId(id)
//                .setTimeStartLecture(timeStartLecture)
//                .setTimeEndLecture(timeEndLecture)
//                .setCourse(course)
//                .setGroup(group)
//                .setClassroom(classroom)
//                .setProfessor(professor)
//                .setTimetableId(timetableId)
//                .build();
//    }
//}
//
