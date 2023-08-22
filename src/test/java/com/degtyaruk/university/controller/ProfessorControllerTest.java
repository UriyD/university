//package com.degtyaruk.university.controller;
//
//import com.degtyaruk.university.config.ConfigTestMock;
//import com.degtyaruk.university.config.WebConfigTest;
//import com.degtyaruk.university.exception.EntityNotFoundException;
//import com.degtyaruk.university.model.Course;
//import com.degtyaruk.university.model.Professor;
//import com.degtyaruk.university.model.dto.ProfessorDto;
//import com.degtyaruk.university.model.mapper.ProfessorDtoMapper;
//import com.degtyaruk.university.service.CourseService;
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
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.hamcrest.Matchers.allOf;
//import static org.hamcrest.Matchers.hasItem;
//import static org.hamcrest.Matchers.hasProperty;
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.is;
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
//class ProfessorControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ProfessorService professorService;
//
//    @Autowired
//    private CourseService courseService;
//
//    @Autowired
//    private ProfessorDtoMapper professorDtoMapper;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @BeforeEach
//    public void setup() {
//        Mockito.reset(professorService, courseService, professorDtoMapper);
//        this.mockMvc = MockMvcBuilders
//                .webAppContextSetup(webApplicationContext)
//                .build();
//    }
//
//    @Test
//    void shouldGetAllProfessors() throws Exception {
//        List<Professor> professors = Arrays.asList(
//                new Professor.ProfessorBuilder()
//                        .withId(1)
//                        .withFirstName("Professor 1")
//                        .withLastName("Professor 1")
//                        .withCourses(Collections.emptyList())
//                        .withEmail("email1")
//                        .withParole("parole1")
//                        .build(),
//                new Professor.ProfessorBuilder()
//                        .withId(2)
//                        .withFirstName("Professor 2")
//                        .withLastName("Professor 2")
//                        .withCourses(Collections.emptyList())
//                        .withEmail("email2")
//                        .withParole("parole2")
//                        .build()
//        );
//        List<Course> courses = Arrays.asList(
//                Course.builder()
//                        .setId(1)
//                        .setFacultyId(1)
//                        .setName("Course 1")
//                        .build(),
//                Course.builder()
//                        .setId(2)
//                        .setFacultyId(1)
//                        .setName("Course 2")
//                        .build()
//        );
//        when(professorService.getAll()).thenReturn(professors);
//        when(courseService.getAll()).thenReturn(courses);
//
//        mockMvc.perform(get("/professors"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("professor/professors"))
//                .andExpect(model().attributeExists("professors"))
//                .andExpect(model().attributeExists("courses"))
//                .andExpect(model().attribute("professors", hasSize(2)))
//                .andExpect(model().attribute("professors", hasItem(
//                        allOf(
//                                hasProperty("id", is(1)),
//                                hasProperty("firstName", is("Professor 1")),
//                                hasProperty("lastName", is("Professor 1")),
//                                hasProperty("courses"),
//                                hasProperty("email"),
//                                hasProperty("parole")
//
//                        )
//                )))
//                .andExpect(model().attribute("professors", hasItem(
//                        allOf(
//                                hasProperty("id", is(2)),
//                                hasProperty("firstName", is("Professor 2")),
//                                hasProperty("lastName", is("Professor 2")),
//                                hasProperty("courses"),
//                                hasProperty("email"),
//                                hasProperty("parole")
//                        )
//                )));
//
//        verify(professorService, times(1)).getAll();
//        verify(courseService, times(1)).getAll();
//        verifyNoMoreInteractions(professorService, courseService);
//    }
//    @Test
//    void shouldHandleExceptionWhenGetGroupByIdWithIncorrectId() throws Exception {
//        int id = 999;
//        when(professorService.getById(id)).thenThrow(new EntityNotFoundException("Can't find Professor."));
//        mockMvc.perform(get("/professors/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(view().name("errors/errorNoSuchElem"))
//                .andExpect(model().attributeExists("message"))
//                .andExpect(model().attribute("message", "Can't find Professor."));
//        verify(professorService, times(1)).getById(id);
//        verifyNoMoreInteractions(professorService);
//    }
//
//    @Test
//    void shouldGetProfessorById() throws Exception {
//        int id = 1;
//        Professor professor = new Professor.ProfessorBuilder()
//                .withId(1)
//                .withFirstName("Professor 1")
//                .withLastName("Professor 1")
//                .withCourses(Collections.emptyList())
//                .withEmail("email")
//                .withParole("parole1")
//                .build();
//
//        when(professorService.getById(1)).thenReturn(professor);
//
//        mockMvc.perform(get("/professors/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(view().name("professor/professor"))
//                .andExpect(model().attributeExists("professor"))
//                .andExpect(model().attribute("professor", professor));
//
//        verify(professorService, times(1)).getById(1);
//        verifyNoMoreInteractions(professorService);
//    }
//
//    @Test
//    void shouldAddGroup() throws Exception {
//        ProfessorDto professorDto = new ProfessorDto();
//        professorDto.setFirstName("Professor 1");
//        professorDto.setLastName("Professor 1");
//        Professor professor = new Professor.ProfessorBuilder()
//                .withFirstName("Professor 1")
//                .withLastName("Professor 1")
//                .build();
//        Professor professorWithoutId = new Professor.ProfessorBuilder().withFirstName("Professor 1").withLastName("Professor 1").build();
//        Professor professorWithId = new Professor.ProfessorBuilder().withId(1).withFirstName("Professor 1").withLastName("Professor 1").build();
//        when(professorDtoMapper.toEntity(professorDto)).thenReturn(professorWithoutId);
//        when(professorService.add(professorWithoutId)).thenReturn(professorWithId);
//
//        mockMvc.perform(post("/professors/add")
//                        .flashAttr("professor", professorDto))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/professors"));
//
//        verify(professorService, times(1)).add(professorWithoutId);
//        verify(professorDtoMapper, times(1)).toEntity(professorDto);
//        verifyNoMoreInteractions(professorService, professorDtoMapper);
//    }
//
//    @Test
//    void shouldDeleteProfessor() throws Exception {
//        int id = 1;
//        mockMvc.perform(get("/professors/delete/{id}", id))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/professors"));
//
//        verify(professorService, times(1)).removeById(1);
//        verifyNoMoreInteractions(professorService);
//    }
//}
//
