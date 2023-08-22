//package com.degtyaruk.university.controller;
//
//import com.degtyaruk.university.config.ConfigTestMock;
//import com.degtyaruk.university.config.WebConfigTest;
//import com.degtyaruk.university.exception.EntityNotFoundException;
//import com.degtyaruk.university.model.Course;
//import com.degtyaruk.university.model.Faculty;
//import com.degtyaruk.university.model.Group;
//import com.degtyaruk.university.model.dto.FacultyDto;
//import com.degtyaruk.university.model.mapper.FacultyDtoMapper;
//import com.degtyaruk.university.service.CourseService;
//import com.degtyaruk.university.service.FacultyService;
//import com.degtyaruk.university.service.GroupService;
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
//class FacultyControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Autowired
//    private FacultyService facultyService;
//
//    @Autowired
//    private CourseService courseService;
//
//    @Autowired
//    private GroupService groupService;
//
//    @Autowired
//    private FacultyDtoMapper facultyDtoMapper;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @BeforeEach
//    public void setup() {
//        Mockito.reset(facultyService, courseService, groupService, facultyDtoMapper);
//        this.mockMvc = MockMvcBuilders
//                .webAppContextSetup(webApplicationContext)
//                .build();
//    }
//
//    @Test
//    void shouldGetAllFaculties() throws Exception {
//        List<Course> courses1 = Arrays.asList(createCourse(1,1, "Course 1"), createCourse(2,1, "Course 2"));
//        List<Group> groups1 = Arrays.asList(createGroup(1,1, "Group 1"), createGroup(2,1, "Group 2"));
//        List<Course> courses2 = Arrays.asList(createCourse(3,2, "Course 3"), createCourse(4,2, "Course 4"));
//        List<Group> groups2 = Arrays.asList(createGroup(3,2, "Group 3"), createGroup(4,2, "Group 4"));
//
//        List<Faculty> faculties = Arrays.asList(
//                createFaculty(1, "Faculty 1", courses1, groups1),
//                createFaculty(2, "Faculty 2", courses2, groups2)
//        );
//
//        when(facultyService.getAll()).thenReturn(faculties);
//        when(courseService.getAll()).thenReturn(Arrays.asList(
//                createCourse(1,1, "Course 1"),
//                createCourse(2,1, "Course 2"),
//                createCourse(3,2, "Course 3"),
//                createCourse(4,2, "Course 4")
//        ));
//        when(groupService.getAll()).thenReturn(Arrays.asList(
//                createGroup(1,1, "Group 1"),
//                createGroup(2,1, "Group 2"),
//                createGroup(3,2, "Group 3"),
//                createGroup(4,2, "Group 4")
//        ));
//
//        mockMvc.perform(get("/faculties"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("faculty/faculties"))
//                .andExpect(model().attributeExists("faculties"))
//                .andExpect(model().attribute("faculties", hasSize(2)))
//                .andExpect(model().attribute("faculties", hasItem(
//                        allOf(
//                                hasProperty("id", is(1)),
//                                hasProperty("name", is("Faculty 1")),
//                                hasProperty("courses", hasSize(2)),
//                                hasProperty("groups", hasSize(2))
//                        )
//                )))
//                .andExpect(model().attribute("faculties", hasItem(
//                        allOf(
//                                hasProperty("id", is(2)),
//                                hasProperty("name", is("Faculty 2")),
//                                hasProperty("courses", hasSize(2)),
//                                hasProperty("groups", hasSize(2))
//                        )
//                )));
//
//        verify(facultyService, times(1)).getAll();
//        verify(courseService, times(1)).getAll();
//        verify(groupService, times(1)).getAll();
//        verifyNoMoreInteractions(facultyService, courseService, groupService);
//    }
//
//    @Test
//    void shouldHandleExceptionWhenGetFacultyByIdWithIncorrectId() throws Exception {
//        int id = 55;
//        when(facultyService.getById(id)).thenThrow(new EntityNotFoundException("Can't find Faculty."));
//
//        mockMvc.perform(get("/faculties/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(view().name("errors/errorNoSuchElem"))
//                .andExpect(model().attributeExists("message"))
//                .andExpect(model().attribute("message", "Can't find Faculty."));
//
//        verify(facultyService, times(1)).getById(id);
//        verifyNoMoreInteractions(facultyService);
//    }
//
//    @Test
//    void shouldGetFacultyById() throws Exception {
//        List<Course> courses = Arrays.asList(createCourse(1,1, "Course 1"), createCourse(2,1, "Course 2"));
//        List<Group> groups = Arrays.asList(createGroup(1,1, "Group 1"), createGroup(2,1, "Group 2"));
//        int id = 1;
//        Faculty faculty = createFaculty(id, "Faculty 1", courses, groups);
//        when(facultyService.getById(id)).thenReturn(faculty);
//
//        mockMvc.perform(get("/faculties/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(view().name("faculty/faculty"))
//                .andExpect(model().attributeExists("faculty"))
//                .andExpect(model().attribute("faculty", hasProperty("id", is(id))))
//                .andExpect(model().attribute("faculty", hasProperty("name", is("Faculty 1"))))
//                .andExpect(model().attribute("faculty", hasProperty("courses", hasSize(2))))
//                .andExpect(model().attribute("faculty", hasProperty("groups", hasSize(2))));
//
//        verify(facultyService, times(1)).getById(id);
//        verifyNoMoreInteractions(facultyService);
//    }
//
//    @Test
//    void shouldAddFaculty() throws Exception {
//        FacultyDto facultyDto = new FacultyDto();
//        facultyDto.setName("Faculty 1");
//        Faculty facultyWithoutId = Faculty.builder()
//                .setName("Faculty 1")
//                .build();
//        Faculty facultyWithId = Faculty.builder()
//                .setId(1)
//                .setName("Faculty 1")
//                .build();
//        when(facultyDtoMapper.toEntity(facultyDto)).thenReturn(facultyWithoutId);
//        when(facultyService.add(facultyWithoutId)).thenReturn(facultyWithId);
//
//        mockMvc.perform(post("/faculties/add")
//                        .flashAttr("faculty", facultyDto))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/faculties"));
//
//        verify(facultyService, times(1)).add(facultyWithoutId);
//        verify(facultyDtoMapper, times(1)).toEntity(facultyDto);
//        verifyNoMoreInteractions(facultyService, facultyDtoMapper);
//    }
//
//    @Test
//    void shouldDeleteFaculty() throws Exception {
//        int id = 1;
//        mockMvc.perform(get("/faculties/delete/{id}", id))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/faculties"));
//
//        verify(facultyService, times(1)).removeById(id);
//        verifyNoMoreInteractions(facultyService);
//    }
//
//    private Faculty createFaculty(Integer id, String name, List<Course> courses, List<Group> groups) {
//        return Faculty.builder()
//                .setId(id)
//                .setName(name)
//                .setCourses(courses)
//                .setGroups(groups)
//                .build();
//    }
//
//    private Course createCourse(Integer id, Integer facultyId, String name) {
//        return Course.builder()
//                .setId(id)
//                .setFacultyId(facultyId)
//                .setName(name)
//                .build();
//    }
//
//    private Group createGroup(Integer id, Integer facultyId, String name) {
//        return Group.builder()
//                .setId(id)
//                .setFacultyId(facultyId)
//                .setName(name)
//                .build();
//    }
//}
//
