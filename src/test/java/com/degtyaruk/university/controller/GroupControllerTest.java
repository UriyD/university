package com.degtyaruk.university.controller;

import com.degtyaruk.university.config.ConfigTestMock;
import com.degtyaruk.university.config.WebConfigTest;
import com.degtyaruk.university.exception.EntityNotFoundException;
import com.degtyaruk.university.exception.InvalidEntityException;
import com.degtyaruk.university.model.Group;
import com.degtyaruk.university.model.Student;
import com.degtyaruk.university.model.dto.GroupDto;
import com.degtyaruk.university.model.mapper.GroupDtoMapper;
import com.degtyaruk.university.service.GroupService;
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
class GroupControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private GroupService groupService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private GroupDtoMapper groupDtoMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        Mockito.reset(groupService, studentService, groupDtoMapper);
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    void shouldGetAllGroups() throws Exception {
        List<Group> groups = Arrays.asList(
                Group.builder().setId(1).setName("Group 1").build(),
                Group.builder().setId(2).setName("Group 2").build()
        );
        List<Student> students = Arrays.asList(
                new Student.StudentBuilder()
                        .withId(1)
                        .withFirstName("Student 1")
                        .withLastName("Student 1")
                        .withEmail("email1")
                        .withParole("parole1")
                        .build(),
                new Student.StudentBuilder()
                        .withId(2)
                        .withFirstName("Student 2")
                        .withLastName("Student 2")
                        .withEmail("email2")
                        .withParole("parole2")
                        .build()
        );
        when(groupService.getAll()).thenReturn(groups);
        when(studentService.getAll()).thenReturn(students);

        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andExpect(view().name("group/groups"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attributeExists("students"))
                .andExpect(model().attribute("groups", hasSize(2)))
                .andExpect(model().attribute("groups", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("facultyId", is(1)),
                                hasProperty("name", is("Group 1"))
                        )
                )))
                .andExpect(model().attribute("groups", hasItem(
                allOf(
                        hasProperty("id", is(2)),
                        hasProperty("facultyId", is(1)),
                        hasProperty("name", is("Group 2"))
                )
        )));

        verify(groupService, times(1)).getAll();
        verify(studentService, times(1)).getAll();
        verifyNoMoreInteractions(groupService, studentService);
    }

    @Test
    void shouldHandleExceptionWhenGetGroupByIdWithIncorrectId() throws Exception {
        int id = 55;
        when(groupService.getById(id)).thenThrow(new EntityNotFoundException("Can't find Group."));
        mockMvc.perform(get("/groups/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("errors/errorNoSuchElem"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", "Can't find Group."));
        verify(groupService, times(1)).getById(id);
        verifyNoMoreInteractions(groupService);
    }

    @Test
    void shouldGetGroupById() throws Exception {
        int id = 1;
        Group group = Group.builder().setId(id).setName("Group 1").build();
        when(groupService.getById(id)).thenReturn(group);

        mockMvc.perform(get("/groups/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("group/group"))
                .andExpect(model().attributeExists("group"))
                .andExpect(model().attribute("group", hasProperty("id", is(id))))
                .andExpect(model().attribute("group", hasProperty("facultyId", is(1))))
                .andExpect(model().attribute("group", hasProperty("name", is("Group 1"))));

        verify(groupService, times(1)).getById(id);
        verifyNoMoreInteractions(groupService);
    }

    @Test
    void shouldThrowExceptionWhenAddGroupWithInvalidFacultyId() throws Exception {
        int facultyId = 999;
        String name = "Group 1";
        GroupDto groupDto = new GroupDto();
        groupDto.setFacultyId(facultyId);
        groupDto.setName(name);
        Group group = Group.builder()
                .setName(name)
                .build();
        when(groupDtoMapper.toEntity(groupDto)).thenReturn(group);
        doThrow(new InvalidEntityException("Invalid Group: faculty with passed id absent. "))
                .when(groupService).add(group);

        mockMvc.perform(post("/groups/add")
                        .flashAttr("group", groupDto))
                .andExpect(status().isOk())
                .andExpect(view().name("errors/errorInvalidEntity"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", "Invalid Group: faculty with passed id absent. "));

        verify(groupDtoMapper, times(1)).toEntity(groupDto);
        verify(groupService, times(1)).add(group);
        verifyNoMoreInteractions(groupDtoMapper, groupService);
    }

    @Test
    void shouldAddGroup() throws Exception {
        GroupDto groupDto = new GroupDto();
        groupDto.setFacultyId(1);
        groupDto.setName("Group 1");
        Group groupWithoutId = Group.builder().setName("Group 1").build();
        Group groupWithId = Group.builder().setId(1).setName("Group 1").build();
        when(groupDtoMapper.toEntity(groupDto)).thenReturn(groupWithoutId);
        when(groupService.add(groupWithoutId)).thenReturn(groupWithId);

        mockMvc.perform(post("/groups/add")
                        .flashAttr("group", groupDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/groups"));

        verify(groupService, times(1)).add(groupWithoutId);
        verify(groupDtoMapper, times(1)).toEntity(groupDto);
        verifyNoMoreInteractions(groupService, groupDtoMapper);
    }

    @Test
    void shouldDeleteGroup() throws Exception {
        int id = 1;
        mockMvc.perform(get("/groups/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/groups"));

        verify(groupService, times(1)).removeById(id);
        verifyNoMoreInteractions(groupService);
    }
}

