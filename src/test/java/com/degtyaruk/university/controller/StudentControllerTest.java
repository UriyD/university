package com.degtyaruk.university.controller;

import com.degtyaruk.university.config.ConfigTestMock;
import com.degtyaruk.university.config.WebConfigTest;
import com.degtyaruk.university.exception.EntityNotFoundException;
import com.degtyaruk.university.exception.InvalidEntityException;
import com.degtyaruk.university.model.Course;
import com.degtyaruk.university.model.Student;
import com.degtyaruk.university.model.dto.StudentDto;
import com.degtyaruk.university.model.mapper.StudentDtoMapper;
import com.degtyaruk.university.service.CourseService;
import com.degtyaruk.university.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ConfigTestMock.class, WebConfigTest.class})
class StudentControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentDtoMapper studentDtoMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        Mockito.reset(studentService, courseService, studentDtoMapper);
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    void shouldGetAllStudents() throws Exception {
        List<Student> students = Arrays.asList(
                new Student.StudentBuilder()
                        .withId(1)
                        .withFirstName("Student 1")
                        .withLastName("Student 1")
                        .withCourses(Collections.emptyList())
                        .withEmail("email1")
                        .withParole("parole1")
                        .build(),
                new Student.StudentBuilder()
                        .withId(2)
                        .withFirstName("Student 2")
                        .withLastName("Student 2")
                        .withCourses(Collections.emptyList())
                        .withEmail("email2")
                        .withParole("parole2")
                        .build()
        );
        List<Course> courses = Arrays.asList(
                Course.builder()
                        .setId(1)
                        .setName("Course 1")
                        .build(),
                Course.builder()
                        .setId(2)
                        .setName("Course 2")
                        .build()
        );
        when(studentService.getAll()).thenReturn(students);
        when(courseService.getAll()).thenReturn(courses);

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/students"))
                .andExpect(model().attributeExists("students"))
                .andExpect(model().attributeExists("courses"))
                .andExpect(model().attribute("students", hasSize(2)))
                .andExpect(model().attribute("students", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("firstName", is("Student 1")),
                                hasProperty("lastName", is("Student 1")),
                                hasProperty("courses"),
                                hasProperty("groupId", is(1)),
                                hasProperty("email"),
                                hasProperty("parole")
                        )
                )))
                .andExpect(model().attribute("students", hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("firstName", is("Student 2")),
                                hasProperty("lastName", is("Student 2")),
                                hasProperty("courses"),
                                hasProperty("groupId", is(1)),
                                hasProperty("email"),
                                hasProperty("parole")
                        )
                )));

        verify(studentService, times(1)).getAll();
        verify(courseService, times(1)).getAll();
        verifyNoMoreInteractions(studentService, courseService);
    }

    @Test
    void shouldHandleExceptionWhenGetStudentByIdWithIncorrectId() throws Exception {
        int id = 55;
        when(studentService.getById(id)).thenThrow(new EntityNotFoundException("Can't find Student."));
        mockMvc.perform(get("/students/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("errors/errorNoSuchElem"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", "Can't find Student."));
        verify(studentService, times(1)).getById(id);
        verifyNoMoreInteractions(studentService);
    }

    @Test
    void shouldGetStudentById() throws Exception {
        int id = 1;
        Student student = new Student.StudentBuilder()
                .withId(id)
                .withFirstName("Student 1")
                .withLastName("Student 1")
                .withCourses(Collections.emptyList())
                .withEmail("email1")
                .withParole("parole1")
                .build();
        when(studentService.getById(id)).thenReturn(student);

        mockMvc.perform(get("/students/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("student/student"))
                .andExpect(model().attributeExists("student"))
                .andExpect(model().attribute("student", student));

        verify(studentService, times(1)).getById(id);
        verifyNoMoreInteractions(studentService);
    }

    @Test
    void shouldThrowExceptionWhenAddStudentWithInvalidGroupId() throws Exception {
        int groupId = 999;
        String firstName = "Student 1";
        String lastName = "Student 1";
        String email = "email1";
        String parole = "parole1";
        StudentDto studentDto = new StudentDto();
        studentDto.setGroupId(groupId);
        studentDto.setFirstName(firstName);
        studentDto.setLastName(lastName);
        studentDto.setEmail(email);
        studentDto.setParole(parole);
        Student student = new Student.StudentBuilder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .withParole(parole)
                .build();
        when(studentDtoMapper.toEntity(studentDto)).thenReturn(student);
        doThrow(new InvalidEntityException("Invalid Student: group with passed id absent."))
                .when(studentService).add(student);

        mockMvc.perform(post("/students/add")
                        .flashAttr("student", studentDto))
                .andExpect(status().isOk())
                .andExpect(view().name("errors/errorInvalidEntity"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", "Invalid Student: group with passed id absent."));

        verify(studentDtoMapper, times(1)).toEntity(studentDto);
        verify(studentService, times(1)).add(student);
        verifyNoMoreInteractions(studentDtoMapper, studentService);
    }

    @Test
    void shouldAddStudent() throws Exception {
        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName("Student 1");
        studentDto.setLastName("Student 1");
        studentDto.setGroupId(1);
        studentDto.setEmail("email1");
        studentDto.setParole("parole1");
        Student studentWithoutId = new Student.StudentBuilder()
                .withFirstName(studentDto.getFirstName())
                .withLastName(studentDto.getLastName())
                .withEmail(studentDto.getEmail())
                .withParole(studentDto.getParole())
                .build();
        Student studentWithId = new Student.StudentBuilder()
                .withId(1)
                .withFirstName(studentDto.getFirstName())
                .withLastName(studentDto.getLastName())
                .withEmail(studentDto.getEmail())
                .withParole(studentDto.getParole())
                .build();
        when(studentDtoMapper.toEntity(studentDto)).thenReturn(studentWithoutId);
        when(studentService.add(studentWithoutId)).thenReturn(studentWithId);

        mockMvc.perform(post("/students/add")
                        .flashAttr("student", studentDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"));

        verify(studentService, times(1)).add(studentWithoutId);
        verify(studentDtoMapper, times(1)).toEntity(studentDto);
        verifyNoMoreInteractions(studentService, studentDtoMapper);
    }

    @Test
    void shouldDeleteStudent() throws Exception {
        int id = 1;
        mockMvc.perform(get("/students/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"));

        verify(studentService, times(1)).removeById(id);
        verifyNoMoreInteractions(studentService);
    }
}

