//package com.degtyaruk.university.dao.impl;
//
//import com.degtyaruk.university.config.SpringConfigTest;
//import com.degtyaruk.university.dao.Page;
//import com.degtyaruk.university.dao.implhibernate.LectureDaoHibernateImpl;
//import com.degtyaruk.university.model.Classroom;
//import com.degtyaruk.university.model.Course;
//import com.degtyaruk.university.model.Group;
//import com.degtyaruk.university.model.Lecture;
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
//import java.time.LocalTime;
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
//class LectureDaoImplTest {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    @Autowired
//    private LectureDaoHibernateImpl lectureDao;
//
//    @Test
//    void addLecture_shouldReturnNewLecture_whenAddNewLecture() {
//        LocalTime timeStart = LocalTime.of(10, 20, 0);
//        LocalTime timeFinish = LocalTime.of(11, 30, 0);
//        Course course = makeCourse(1, "Math", 1);
//        Group group = makeGroup(2, 2, "ag-21");
//        Classroom classroom = makeClassroom(1, 36);
//        Professor professor = makeProfessor(1, "Andrii", "Nemchinskiy", "andrii@gmail.com");
//        Lecture expected = makeLecture(4, timeStart, timeFinish, course, group, classroom, professor, 2);
//        Lecture lecture = makeLecture(null, timeStart, timeFinish, course, group, classroom, professor, 2);
//        Lecture actual = lectureDao.add(lecture);
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void addLecture_shouldAddNewRecord_whenAddNewLecture() {
//        LocalTime timeStart = LocalTime.of(10, 20, 0);
//        LocalTime timeFinish = LocalTime.of(11, 30, 0);
//        Course course = makeCourse(1, "Math", 1);
//        Group group = makeGroup(2, 2, "ag-21");
//        Classroom classroom = makeClassroom(1, 36);
//        Professor professor = makeProfessor(1, "Andrii", "Nemchinskiy", "andrii@gmail.com");
//        Lecture lecture = makeLecture(null, timeStart, timeFinish, course, group, classroom, professor, 2);
//        lectureDao.add(lecture);
//        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lectures");
//        assertThat("Actual result does not match expected", actual, is(equalTo(4)));
//        int actualWithWhereClause = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lectures",
//                "course_id = 1 and group_id = 2 and classroom_id = 1 and professor_id = 1");
//        assertThat("Actual result does not match expected", actualWithWhereClause, is(equalTo(1)));
//    }
//
//    @Test
//    void updateLecture_shouldUpdateLecture_whenPassedLecture() {
//        LocalTime timeStart = LocalTime.of(10, 20, 0);
//        LocalTime timeFinish = LocalTime.of(11, 30, 0);
//        Course course = makeCourse(1, "Math", 1);
//        Group group = makeGroup(2, 2, "ag-21");
//        Classroom classroom = makeClassroom(1, 36);
//        Professor professor = makeProfessor(1, "Andrii", "Nemchinskiy", "andrii@gmail.com");
//        Lecture lecture = makeLecture(1, timeStart, timeFinish, course, group, classroom, professor, 2);
//        lectureDao.update(lecture);
//        int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lectures",
//                "id = 1 and lecture_time_start = '10:20:00' and lecture_time_finish = '11:30:00' and " +
//                        "course_id = 1 and group_id = 2 and classroom_id = 1 and professor_id = 1 and timetable_id = 2");
//        assertThat("Actual result does not match expected", actual, is(equalTo(1)));
//    }
//
//    @Test
//    void deleteLecture_shouldDeleteLecture_whenPassedId() {
//        lectureDao.removeById(1);
//        int actualCountRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lectures");
//        int actualCountRowsWithWhereClause = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lectures", "id = 1");
//        assertThat("Actual result does not match", actualCountRows, is(equalTo(2)));
//        assertThat("Actual result does not match", actualCountRowsWithWhereClause, is(equalTo(0)));
//    }
//
//    @Test
//    void getLectureById_shouldReturnCorrectLectureByGivenId() {
//        LocalTime timeStart = LocalTime.of(9, 5, 0);
//        LocalTime timeFinish = LocalTime.of(10, 35, 0);
//        Course course = makeCourse(2, "History of Ukraine", 2);
//        Group group = makeGroup(3, 3, "jk-56");
//        Classroom classroom = makeClassroom(1, 36);
//        Professor professor = makeProfessor(1, "Andrii", "Nemchinskiy", "andrii@gmail.com");
//        Lecture expected = makeLecture(3, timeStart, timeFinish, course, group, classroom, professor, 3);
//        Lecture actual = lectureDao.getById(3).get();
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getLectureById_shouldReturnEmpty_whenLectureNotExist() {
//        Optional<Lecture> expected = Optional.empty();
//        Optional<Lecture> actual = lectureDao.getById(4);
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getAll_shouldReturnAllLectures() {
//        LocalTime timeStartFirst = LocalTime.of(9, 5, 0);
//        LocalTime timeFinishFirst = LocalTime.of(10, 35, 0);
//        Course courseFirst = makeCourse(2, "History of Ukraine", 2);
//        Group groupFirst = makeGroup(3, 3, "jk-56");
//        Classroom classroomFirst = makeClassroom(1, 36);
//        Professor professorFirst = makeProfessor(1, "Andrii", "Nemchinskiy", "andrii@gmail.com");
//        Lecture firstLecture = makeLecture(3, timeStartFirst, timeFinishFirst, courseFirst, groupFirst, classroomFirst, professorFirst, 3);
//
//        LocalTime timeStartSecond = LocalTime.of(11, 40, 0);
//        LocalTime timeFinishSecond = LocalTime.of(13, 10, 0);
//        Course courseSecond = makeCourse(1, "Math", 1);
//        Group groupSecond = makeGroup(1, 1, "jk-56");
//        Classroom classroomSecond = makeClassroom(3, 56);
//        Professor professorSecond = makeProfessor(2, "Karl", "Lagerfeld", "karl@gmail.com");
//        Lecture secondLecture = makeLecture(2, timeStartSecond, timeFinishSecond, courseSecond, groupSecond, classroomSecond, professorSecond, 1);
//
//        LocalTime timeStartThird = LocalTime.of(9, 5, 0);
//        LocalTime timeFinishThird = LocalTime.of(10, 35, 0);
//        Course courseThird = makeCourse(3, "Physic", 3);
//        Group groupThird = makeGroup(2, 2, "ag-21");
//        Classroom classroomThird = makeClassroom(2, 47);
//        Professor professorThird = makeProfessor(2, "Karl", "Lagerfeld", "karl@gmail.com");
//        Lecture thirdLecture = makeLecture(1, timeStartThird, timeFinishThird, courseThird, groupThird, classroomThird, professorThird, 2);
//
//        List<Lecture> expected = Arrays.asList(thirdLecture, secondLecture, firstLecture);
//        List<Lecture> actual = lectureDao.getAll();
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getAll_shouldReturnCorrectLectureRecordsCount() {
//        int actualLecturesSize = lectureDao.getAll().size();
//        int expectedLecturesSize = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lectures");
//        assertThat("Actual result does not match expected", actualLecturesSize, is(equalTo(expectedLecturesSize)));
//    }
//
//    @Test
//    void getAllAccordingLimitOffset_shouldReturnAllLectures_whenPassedPageWithLimitAndOffset() {
//        Page page = new Page(1, 1);
//        LocalTime timeStartSecond = LocalTime.of(11, 40, 0);
//        LocalTime timeFinishSecond = LocalTime.of(13, 10, 0);
//        Course courseSecond = makeCourse(1, "Math", 1);
//        Group groupSecond = makeGroup(1, 1, "jk-56");
//        Classroom classroomSecond = makeClassroom(3, 56);
//        Professor professorSecond = makeProfessor(2, "Karl", "Lagerfeld", "karl@gmail.com");
//        Lecture lecture = makeLecture(2, timeStartSecond, timeFinishSecond, courseSecond, groupSecond, classroomSecond, professorSecond, 1);
//        List<Lecture> expected = Arrays.asList(lecture);
//        List<Lecture> actual = lectureDao.getAll(page);
//        assertThat("Actual result does not match expected", actual, is(equalTo(expected)));
//    }
//
//    @Test
//    void getAllAccordingLimitOffset_shouldReturnCorrectLectureRecordsCount_whenPassedPageWithLimitAndOffset() {
//        Page page = new Page(1, 1);
//        int actualLectureSize = lectureDao.getAll(page).size();
//        assertThat("Actual result does not match expected", actualLectureSize, is(equalTo(1)));
//    }
//
//    private static Lecture makeLecture(Integer id, LocalTime timeStartLecture, LocalTime timeEndLecture,
//                                       Course course, Group group, Classroom classroom, Professor professor, Integer timetableId) {
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
//
//    private static Course makeCourse(Integer id, String courseName, Integer facultyId) {
//        return Course.builder()
//                .setId(id)
//                .setName(courseName)
//                .setFacultyId(facultyId)
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
//}
//
