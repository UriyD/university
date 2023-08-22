//package com.degtyaruk.university.controller;
//
//import com.degtyaruk.university.config.ConfigTestMock;
//import com.degtyaruk.university.config.WebConfigTest;
//import com.degtyaruk.university.exception.EntityNotFoundException;
//import com.degtyaruk.university.model.Classroom;
//import com.degtyaruk.university.model.Course;
//import com.degtyaruk.university.model.Group;
//import com.degtyaruk.university.model.Lecture;
//import com.degtyaruk.university.model.Professor;
//import com.degtyaruk.university.model.Timetable;
//import com.degtyaruk.university.model.dto.TimetableDto;
//import com.degtyaruk.university.model.mapper.TimetableDtoMapper;
//import com.degtyaruk.university.service.LectureService;
//import com.degtyaruk.university.service.TimetableService;
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
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.hamcrest.Matchers.hasItem;
//import static org.hamcrest.Matchers.hasSize;
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
//
//@ExtendWith(SpringExtension.class)
//@WebAppConfiguration
//@ContextConfiguration(classes = {ConfigTestMock.class, WebConfigTest.class})
//class TimetableControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Autowired
//    private TimetableService timetableService;
//
//    @Autowired
//    private LectureService lectureService;
//
//    @Autowired
//    private TimetableDtoMapper timetableDtoMapper;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @BeforeEach
//    public void setup() {
//        Mockito.reset(timetableService, lectureService, timetableDtoMapper);
//        this.mockMvc = MockMvcBuilders
//                .webAppContextSetup(webApplicationContext)
//                .build();
//    }
//
//    @Test
//    void shouldGetAllTimetables() throws Exception {
//        List<Lecture> lectures = Arrays.asList(
//                Lecture.builder()
//                        .setId(1)
//                        .setTimeStartLecture(LocalTime.of(13, 0))
//                        .setTimeEndLecture(LocalTime.of(15, 0))
//                        .setCourse(new Course())
//                        .setGroup(new Group())
//                        .setClassroom(new Classroom())
//                        .setProfessor(new Professor.ProfessorBuilder().build())
//                        .setTimetableId(1)
//                        .build(),
//                Lecture.builder()
//                        .setId(2)
//                        .setTimeStartLecture(LocalTime.of(17, 0))
//                        .setTimeEndLecture(LocalTime.of(19, 0))
//                        .setCourse(new Course())
//                        .setGroup(new Group())
//                        .setClassroom(new Classroom())
//                        .setProfessor(new Professor.ProfessorBuilder().build())
//                        .setTimetableId(2)
//                        .build()
//        );
//
//        List<Timetable> timetables = Arrays.asList(
//                Timetable.builder()
//                        .setId(1)
//                        .setDate(LocalDate.of(1, 1, 1))
//                        .setLectures(new ArrayList<>())
//                        .build(),
//                Timetable.builder()
//                        .setId(2)
//                        .setDate(LocalDate.of(2, 2, 2))
//                        .setLectures(new ArrayList<>())
//                        .build()
//        );
//
//        when(timetableService.getAll()).thenReturn(timetables);
//        when(lectureService.getAll()).thenReturn(lectures);
//
//        mockMvc.perform(get("/timetables"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("timetable/timetables"))
//                .andExpect(model().attributeExists("timetables"))
//                .andExpect(model().attributeExists("lectures"))
//                .andExpect(model().attribute("timetables", hasSize(2)))
//                .andExpect(model().attribute("timetables", hasItem(timetables.get(0))))
//                .andExpect(model().attribute("timetables", hasItem(timetables.get(1))))
//                .andExpect(model().attribute("lectures", hasSize(2)))
//                .andExpect(model().attribute("lectures", hasItem(lectures.get(0))))
//                .andExpect(model().attribute("lectures", hasItem(lectures.get(1))));
//
//        verify(timetableService, times(1)).getAll();
//        verify(lectureService, times(1)).getAll();
//        verifyNoMoreInteractions(timetableService, lectureService);
//    }
//
//    @Test
//    void shouldHandleExceptionWhenGetTimetableByIdWithIncorrectId() throws Exception {
//        int id = 55;
//        when(timetableService.getById(id)).thenThrow(new EntityNotFoundException("Can't find Timetable."));
//        mockMvc.perform(get("/timetables/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(view().name("errors/errorNoSuchElem"))
//                .andExpect(model().attributeExists("message"))
//                .andExpect(model().attribute("message", "Can't find Timetable."));
//        verify(timetableService, times(1)).getById(id);
//        verifyNoMoreInteractions(timetableService);
//    }
//
//    @Test
//    void shouldGetTimetableById() throws Exception {
//        int id = 1;
//        Timetable timetable = Timetable.builder()
//                .setId(id)
//                .setDate(LocalDate.of(2, 2, 2))
//                .setLectures(new ArrayList<>())
//                .build();
//        when(timetableService.getById(id)).thenReturn(timetable);
//
//        mockMvc.perform(get("/timetables/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(view().name("timetable/timetable"))
//                .andExpect(model().attributeExists("timetable"))
//                .andExpect(model().attribute("timetable", timetable));
//
//        verify(timetableService, times(1)).getById(id);
//        verifyNoMoreInteractions(timetableService);
//    }
//
//    @Test
//    void shouldAddTimetable() throws Exception {
//        TimetableDto timetableDto = new TimetableDto();
//        timetableDto.setDate(LocalDate.of(1, 1, 1));
//        timetableDto.setLectureIds(new ArrayList<>());
//        Timetable timetableWithoutId = Timetable.builder()
//                .setDate(LocalDate.of(1, 1, 1))
//                .setLectures(new ArrayList<>())
//                .build();
//        Timetable timetableWithId = Timetable.builder()
//                .setId(1)
//                .setDate(LocalDate.of(1, 1, 1))
//                .setLectures(new ArrayList<>())
//                .build();
//        when(timetableDtoMapper.toEntity(timetableDto)).thenReturn(timetableWithoutId);
//        when(timetableService.add(timetableWithoutId)).thenReturn(timetableWithId);
//
//        mockMvc.perform(post("/timetables/add")
//                        .flashAttr("timetable", timetableDto))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/timetables"));
//
//        verify(timetableService, times(1)).add(timetableWithoutId);
//        verify(timetableDtoMapper, times(1)).toEntity(timetableDto);
//        verifyNoMoreInteractions(timetableService, timetableDtoMapper);
//    }
//
//    @Test
//    void shouldDeleteTimetable() throws Exception {
//        int id = 1;
//        mockMvc.perform(get("/timetables/delete/{id}", id))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/timetables"));
//
//        verify(timetableService, times(1)).removeById(id);
//        verifyNoMoreInteractions(timetableService);
//    }
//
//    @Test
//    void shouldGetLectures() throws Exception {
//        int id = 1;
//        List<Lecture> lectures = Arrays.asList(
//                Lecture.builder()
//                        .setId(1)
//                        .setTimeStartLecture(LocalTime.of(13, 0))
//                        .setTimeEndLecture(LocalTime.of(15, 0))
//                        .setCourse(new Course())
//                        .setGroup(new Group())
//                        .setClassroom(new Classroom())
//                        .setProfessor(new Professor.ProfessorBuilder().build())
//                        .setTimetableId(1)
//                        .build(),
//                Lecture.builder()
//                        .setId(2)
//                        .setTimeStartLecture(LocalTime.of(17, 0))
//                        .setTimeEndLecture(LocalTime.of(19, 0))
//                        .setCourse(new Course())
//                        .setGroup(new Group())
//                        .setClassroom(new Classroom())
//                        .setProfessor(new Professor.ProfessorBuilder().build())
//                        .setTimetableId(2)
//                        .build()
//        );
//        Timetable timetable = Timetable.builder()
//                .setId(id)
//                .setDate(LocalDate.of(2, 2, 2))
//                .setLectures(lectures)
//                .build();
//
//        when(timetableService.getById(id)).thenReturn(timetable);
//
//        mockMvc.perform(get("/timetables/lectures/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(view().name("timetable/lectures"))
//                .andExpect(model().attributeExists("lectures"))
//                .andExpect(model().attributeExists("date"))
//                .andExpect(model().attribute("lectures", hasSize(2)))
//                .andExpect(model().attribute("lectures", hasItem(lectures.get(0))))
//                .andExpect(model().attribute("lectures", hasItem(lectures.get(1))));
//
//        verify(timetableService, times(1)).getById(id);
//        verifyNoMoreInteractions(timetableService);
//    }
//}
//
