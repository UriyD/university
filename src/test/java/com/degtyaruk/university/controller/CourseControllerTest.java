//package com.degtyaruk.university.controller;
//
//import com.degtyaruk.university.config.ConfigTestMock;
//import com.degtyaruk.university.config.WebConfigTest;
//import com.degtyaruk.university.exception.EntityNotFoundException;
//import com.degtyaruk.university.exception.InvalidEntityException;
//import com.degtyaruk.university.model.Course;
//import com.degtyaruk.university.model.dto.CourseDto;
//import com.degtyaruk.university.model.mapper.CourseDtoMapper;
//import com.degtyaruk.university.service.CourseService;
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
//class CourseControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Autowired
//    private CourseService courseService;
//
//    @Autowired
//    private CourseDtoMapper courseDtoMapper;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @BeforeEach
//    public void setup() {
//        Mockito.reset(courseService, courseDtoMapper);
//        this.mockMvc = MockMvcBuilders
//                .webAppContextSetup(webApplicationContext)
//                .build();
//    }
//
//    @Test
//    void shouldGetAllCourses() throws Exception {
//        List<Course> courses = Arrays.asList(
//                Course.builder().setId(1).setFacultyId(1).setName("Biology").build(),
//                Course.builder().setId(2).setFacultyId(1).setName("Math").build()
//        );
//        when(courseService.getAll()).thenReturn(courses);
//
//        mockMvc.perform(get("/courses"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("course/courses"))
//                .andExpect(model().attributeExists("courses"))
//                .andExpect(model().attribute("courses", hasSize(2)))
//                .andExpect(model().attribute("courses", hasItem(
//                        allOf(
//                                hasProperty("id", is(1)),
//                                hasProperty("facultyId", is(1)),
//                                hasProperty("name", is("Biology"))
//                        )
//                )))
//                .andExpect(model().attribute("courses", hasItem(
//                        allOf(
//                                hasProperty("id", is(2)),
//                                hasProperty("facultyId", is(1)),
//                                hasProperty("name", is("Math"))
//                        )
//                )));
//
//        verify(courseService, times(1)).getAll();
//        verifyNoMoreInteractions(courseService);
//    }
//
//    @Test
//    void shouldHandleExceptionWhenGetCourseByIdWithIncorrectId() throws Exception {
//        int id = 55;
//        when(courseService.getById(id)).thenThrow(new EntityNotFoundException("Can't find Course."));
//        mockMvc.perform(get("/courses/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(view().name("errors/errorNoSuchElem"))
//                .andExpect(model().attributeExists("message"))
//                .andExpect(model().attribute("message", "Can't find Course."));
//        verify(courseService, times(1)).getById(id);
//        verifyNoMoreInteractions(courseService);
//    }
//
//    @Test
//    void shouldGetCourseById() throws Exception {
//        int id = 1;
//        Course course = Course.builder().setId(id).setFacultyId(1).setName("Math").build();
//        when(courseService.getById(id)).thenReturn(course);
//
//        mockMvc.perform(get("/courses/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(view().name("course/course"))
//                .andExpect(model().attributeExists("course"))
//                .andExpect(model().attribute("course", hasProperty("id", is(id))))
//                .andExpect(model().attribute("course", hasProperty("facultyId", is(1))))
//                .andExpect(model().attribute("course", hasProperty("name", is("Math"))));
//
//        verify(courseService, times(1)).getById(id);
//        verifyNoMoreInteractions(courseService);
//    }
//
//    @Test
//    void shouldThrowExceptionWhenAddCourseWithInvalidFacultyId() throws Exception {
//        int facultyId = 999;
//        String name = "Biology";
//        CourseDto courseDto = new CourseDto();
//        courseDto.setFacultyId(facultyId);
//        courseDto.setName(name);
//        Course course = Course.builder()
//                .setFacultyId(facultyId)
//                .setName(name)
//                .build();
//        when(courseDtoMapper.toEntity(courseDto)).thenReturn(course);
//        doThrow(new InvalidEntityException("Invalid Course: faculty with passed id absent. "))
//                .when(courseService).add(course);
//
//        mockMvc.perform(post("/courses/add")
//                        .flashAttr("course", courseDto))
//                .andExpect(status().isOk())
//                .andExpect(view().name("errors/errorInvalidEntity"))
//                .andExpect(model().attributeExists("message"))
//                .andExpect(model().attribute("message", "Invalid Course: faculty with passed id absent. "));
//
//        verify(courseDtoMapper, times(1)).toEntity(courseDto);
//        verify(courseService, times(1)).add(course);
//        verifyNoMoreInteractions(courseDtoMapper, courseService);
//    }
//
//    @Test
//    void shouldAddCourse() throws Exception {
//        CourseDto courseDto = new CourseDto();
//        courseDto.setFacultyId(1);
//        courseDto.setName("Math");
//        Course courseWithoutId = Course.builder().setFacultyId(1).setName("Math").build();
//        Course courseWithId = Course.builder().setId(1).setFacultyId(1).setName("Math").build();
//        when(courseDtoMapper.toEntity(courseDto)).thenReturn(courseWithoutId);
//        when(courseService.add(courseWithoutId)).thenReturn(courseWithId);
//
//        mockMvc.perform(post("/courses/add")
//                        .flashAttr("course", courseDto))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/courses"));
//
//        verify(courseService, times(1)).add(courseWithoutId);
//        verify(courseDtoMapper, times(1)).toEntity(courseDto);
//        verifyNoMoreInteractions(courseService, courseDtoMapper);
//    }
//
//    @Test
//    void shouldDeleteCourse() throws Exception {
//        int id = 1;
//        mockMvc.perform(get("/courses/delete/{id}", id))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/courses"));
//
//        verify(courseService, times(1)).removeById(id);
//        verifyNoMoreInteractions(courseService);
//    }
//}
//
