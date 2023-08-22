//package com.degtyaruk.university.controller;
//
//import com.degtyaruk.university.config.ConfigTestMock;
//import com.degtyaruk.university.config.WebConfigTest;
//import com.degtyaruk.university.exception.EntityNotFoundException;
//import com.degtyaruk.university.exception.InvalidEntityException;
//import com.degtyaruk.university.model.Classroom;
//import com.degtyaruk.university.model.Course;
//import com.degtyaruk.university.model.Group;
//import com.degtyaruk.university.model.Lecture;
//import com.degtyaruk.university.model.Professor;
//import com.degtyaruk.university.model.dto.LectureDto;
//import com.degtyaruk.university.model.mapper.LectureDtoMapper;
//import com.degtyaruk.university.service.ClassroomService;
//import com.degtyaruk.university.service.CourseService;
//import com.degtyaruk.university.service.GroupService;
//import com.degtyaruk.university.service.LectureService;
//import com.degtyaruk.university.service.ProfessorService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.time.LocalTime;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.hamcrest.Matchers.allOf;
//import static org.hamcrest.Matchers.hasItem;
//import static org.hamcrest.Matchers.hasProperty;
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.is;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//
//@ExtendWith(SpringExtension.class)
//@WebAppConfiguration
//@ContextConfiguration(classes = {ConfigTestMock.class, WebConfigTest.class})
//class LectureControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Autowired
//    private LectureService lectureService;
//
//    @Autowired
//    private CourseService courseService;
//
//    @Autowired
//    private GroupService groupService;
//
//    @Autowired
//    private ProfessorService professorService;
//
//    @Autowired
//    private ClassroomService classroomService;
//
//    @Autowired
//    private LectureDtoMapper lectureDtoMapper;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @BeforeEach
//    public void setup() {
//        Mockito.reset(lectureService, courseService, groupService, professorService, classroomService, lectureDtoMapper);
//        this.mockMvc = MockMvcBuilders
//                .webAppContextSetup(webApplicationContext)
//                .build();
//    }
//
//    @Test
//    void shouldGetAllLectures() throws Exception {
//        Course course1 = Course.builder()
//                .setId(1)
//                .setFacultyId(1)
//                .setName("Course 1")
//                .build();
//        Course course2 = Course.builder()
//                .setId(2)
//                .setFacultyId(1)
//                .setName("Course 2")
//                .build();
//
//        Group group1 = Group.builder()
//                .setId(1)
//                .setFacultyId(1)
//                .setName("Group 1")
//                .build();
//        Group group2 = Group.builder()
//                .setId(2)
//                .setFacultyId(1)
//                .setName("Group 2")
//                .build();
//
//        Classroom classroom1 = Classroom.builder()
//                .setId(1)
//                .setNumber(101)
//                .build();
//        Classroom classroom2 = Classroom.builder()
//                .setId(2)
//                .setNumber(102)
//                .build();
//
//        Professor professor1 = new Professor.ProfessorBuilder()
//                .withId(1)
//                .withFirstName("John")
//                .withLastName("Doe")
//                .withEmail("em@ui.com")
//                .withParole("pas")
//                .build();
//
//        Professor professor2 = new Professor.ProfessorBuilder()
//                .withId(2)
//                .withFirstName("Jane")
//                .withLastName("Smith")
//                .withEmail("yu@ui.com")
//                .withParole("pas")
//                .build();
//
//        List<Lecture> lectures = Arrays.asList(
//                Lecture.builder()
//                        .setId(1)
//                        .setCourse(course1)
//                        .setGroup(group1)
//                        .setClassroom(classroom1)
//                        .setProfessor(professor1)
//                        .setTimeStartLecture(LocalTime.of(9, 0))
//                        .setTimeEndLecture(LocalTime.of(11, 0))
//                        .setTimetableId(1)
//                        .build(),
//                Lecture.builder()
//                        .setId(2)
//                        .setCourse(course2)
//                        .setGroup(group2)
//                        .setClassroom(classroom2)
//                        .setProfessor(professor2)
//                        .setTimeStartLecture(LocalTime.of(13, 0))
//                        .setTimeEndLecture(LocalTime.of(15, 0))
//                        .setTimetableId(2)
//                        .build()
//        );
//
//        when(lectureService.getAll()).thenReturn(lectures);
//        when(courseService.getAll()).thenReturn(Arrays.asList(course1, course2));
//        when(groupService.getAll()).thenReturn(Arrays.asList(group1, group2));
//        when(classroomService.getAll()).thenReturn(Arrays.asList(classroom1, classroom2));
//        when(professorService.getAll()).thenReturn(Arrays.asList(professor1, professor2));
//
//        mockMvc.perform(get("/lectures"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("lecture/lectures"))
//                .andExpect(model().attributeExists("lectures"))
//                .andExpect(model().attribute("lectures", hasSize(2)))
//                .andExpect(model().attribute("lectures", hasItem(
//                        allOf(
//                                hasProperty("id", is(1)),
//                                hasProperty("course", is(course1)),
//                                hasProperty("group", is(group1)),
//                                hasProperty("classroom", is(classroom1)),
//                                hasProperty("professor", is(professor1)),
//                                hasProperty("timeStartLecture", is(LocalTime.of(9, 0))),
//                                hasProperty("timeEndLecture", is(LocalTime.of(11, 0))),
//                                hasProperty("timetableId", is(1)) // Added timetableId assertion
//                        )
//                )))
//            .andExpect(model().attribute("lectures", hasItem(
//                allOf(
//                        hasProperty("id", is(2)),
//                        hasProperty("course", is(course2)),
//                        hasProperty("group", is(group2)),
//                        hasProperty("classroom", is(classroom2)),
//                        hasProperty("professor", is(professor2)),
//                        hasProperty("timeStartLecture", is(LocalTime.of(13, 0))),
//                        hasProperty("timeEndLecture", is(LocalTime.of(15, 0))),
//                        hasProperty("timetableId", is(2)) // Added timetableId assertion
//                )
//        )));
//
//        verify(lectureService, times(1)).getAll();
//        verify(courseService, times(1)).getAll();
//        verify(groupService, times(1)).getAll();
//        verify(classroomService, times(1)).getAll();
//        verify(professorService, times(1)).getAll();
//        verifyNoMoreInteractions(lectureService, courseService, groupService, classroomService, professorService);
//    }
//
//    @Test
//    void shouldHandleExceptionWhenGetLectureByIdWithIncorrectId() throws Exception {
//        int id = 55;
//        when(lectureService.getById(id)).thenThrow(new EntityNotFoundException("Can't find Lecture."));
//        mockMvc.perform(get("/lectures/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(view().name("errors/errorNoSuchElem"))
//                .andExpect(model().attributeExists("message"))
//                .andExpect(model().attribute("message", "Can't find Lecture."));
//        verify(lectureService, times(1)).getById(id);
//        verifyNoMoreInteractions(lectureService);
//    }
//
//    @Test
//    void shouldGetLectureById() throws Exception {
//        int id = 1;
//
//        Course course = Course.builder()
//                .setId(1)
//                .setFacultyId(1)
//                .setName("Course Name")
//                .build();
//
//        Group group = Group.builder()
//                .setId(1)
//                .setFacultyId(1)
//                .setName("Group Name")
//                .build();
//
//        Classroom classroom = Classroom.builder()
//                .setId(1)
//                .setNumber(101)
//                .build();
//
//        Professor professor = new Professor.ProfessorBuilder()
//                .withId(1)
//                .withFirstName("John")
//                .withLastName("Doe")
//                .withEmail("em@ui.com")
//                .withParole("pas")
//                .build();
//
//        Lecture lecture = Lecture.builder()
//                .setId(id)
//                .setTimeStartLecture(LocalTime.of(9, 0))
//                .setTimeEndLecture(LocalTime.of(11, 0))
//                .setCourse(course)
//                .setGroup(group)
//                .setClassroom(classroom)
//                .setProfessor(professor)
//                .setTimetableId(1)
//                .build();
//
//        when(lectureService.getById(id)).thenReturn(lecture);
//
//        mockMvc.perform(get("/lectures/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(view().name("lecture/lecture"))
//                .andExpect(model().attributeExists("lecture"))
//                .andExpect(model().attribute("lecture", hasProperty("id", is(id))))
//                .andExpect(model().attribute("lecture", hasProperty("timeStartLecture", is(LocalTime.of(9, 0)))))
//                .andExpect(model().attribute("lecture", hasProperty("timeEndLecture", is(LocalTime.of(11, 0)))))
//                .andExpect(model().attribute("lecture", hasProperty("course", is(course))))
//                .andExpect(model().attribute("lecture", hasProperty("group", is(group))))
//                .andExpect(model().attribute("lecture", hasProperty("classroom", is(classroom))))
//                .andExpect(model().attribute("lecture", hasProperty("professor", is(professor))))
//                .andExpect(model().attribute("lecture", hasProperty("timetableId", is(1))))
//                .andReturn();
//
//        verify(lectureService, times(1)).getById(id);
//        verifyNoMoreInteractions(lectureService);
//    }
//
//    @Test
//    void shouldThrowExceptionWhenAddLectureWithInvalidCourseId() throws Exception {
//        Lecture lecture = new Lecture();
//        LectureDto lectureDto = new LectureDto();
//        when(lectureDtoMapper.toEntity(lectureDto)).thenReturn(lecture);
//        doThrow(new InvalidEntityException("Invalid Lecture: course with passed id absent. "))
//                .when(lectureService).add(lecture);
//
//        mockMvc.perform(post("/lectures/add")
//                        .flashAttr("lecture", lectureDto))
//                .andExpect(status().isOk())
//                .andExpect(view().name("errors/errorInvalidEntity"))
//                .andExpect(model().attributeExists("message"))
//                .andExpect(model().attribute("message", "Invalid Lecture: course with passed id absent. "));
//
//        verify(lectureDtoMapper, times(1)).toEntity(lectureDto);
//        verify(lectureService, times(1)).add(lecture);
//        verifyNoMoreInteractions(lectureDtoMapper, lectureService);
//    }
//
//    @Test
//    void shouldAddLecture() throws Exception {
//        LectureDto lectureDto = new LectureDto();
//        Lecture lectureWithoutId = Lecture.builder()
//                .setTimeStartLecture(LocalTime.of(9, 0))
//                .setTimeEndLecture(LocalTime.of(11, 0))
//                .build();
//        Lecture lectureWithId = Lecture.builder()
//                .setId(1)
//                .setTimeStartLecture(LocalTime.of(9, 0))
//                .setTimeEndLecture(LocalTime.of(11, 0))
//                .build();
//        when(lectureDtoMapper.toEntity(lectureDto)).thenReturn(lectureWithoutId);
//        when(lectureService.add(lectureWithoutId)).thenReturn(lectureWithId);
//
//        mockMvc.perform(post("/lectures/add")
//                        .flashAttr("lecture", lectureDto))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/lectures"));
//
//        verify(lectureService, times(1)).add(lectureWithoutId);
//        verify(lectureDtoMapper, times(1)).toEntity(lectureDto);
//        verifyNoMoreInteractions(lectureService, lectureDtoMapper);
//    }
//
//    @Test
//    void shouldDeleteLecture() throws Exception {
//        int id = 1;
//        mockMvc.perform(get("/lectures/delete/{id}", id))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/lectures"));
//
//        verify(lectureService, times(1)).removeById(id);
//        verifyNoMoreInteractions(lectureService);
//    }
//}
//
