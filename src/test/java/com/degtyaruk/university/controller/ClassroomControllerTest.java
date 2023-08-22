package com.degtyaruk.university.controller;


import com.degtyaruk.university.config.ConfigTestMock;

import com.degtyaruk.university.config.WebConfigTest;
import com.degtyaruk.university.exception.EntityNotFoundException;
import com.degtyaruk.university.model.Classroom;
import com.degtyaruk.university.model.dto.ClassroomDto;
import com.degtyaruk.university.model.mapper.ClassroomDtoMapper;
import com.degtyaruk.university.service.ClassroomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ConfigTestMock.class, WebConfigTest.class})
class ClassroomControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private ClassroomDtoMapper classroomDtoMapper;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        Mockito.reset(classroomService, classroomDtoMapper);
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    void shouldGetAllClassrooms() throws Exception {
        List<Classroom> classrooms = Arrays.asList(
                Classroom.builder().setId(1).setNumber(8).build(),
                Classroom.builder().setId(2).setNumber(9).build()
        );
        when(classroomService.getAll()).thenReturn(classrooms);

        mockMvc.perform(get("/classrooms"))
                .andExpect(status().isOk())
                .andExpect(view().name("classroom/classrooms"))
                .andExpect(model().attributeExists("classrooms"))
                .andExpect(model().attribute("classrooms", hasSize(2)))
                .andExpect(model().attribute("classrooms", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("number", is(8))
                        )
                )))
                .andExpect(model().attribute("classrooms", hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("number", is(9))
                        )
                )));

        verify(classroomService, times(1)).getAll();
        verifyNoMoreInteractions(classroomService);
    }

    @Test
    void getClassroomById_shouldHandleExceptionWhenGetClassroomById_WhenPassedIncorrectId() throws Exception {
        int id = 55;
        when(classroomService.getById(id)).thenThrow(new EntityNotFoundException("Can't find Classroom. "));
        mockMvc.perform(get("/classrooms/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("errors/errorNoSuchElem"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", "Can't find Classroom. "));
        verify(classroomService, times(1)).getById(id);
        verifyNoMoreInteractions(classroomService);
    }

    @Test
    void shouldGetClassroomById() throws Exception {
        int id = 1;
        Classroom classroom = Classroom.builder().setId(id).setNumber(8).build();
        when(classroomService.getById(id)).thenReturn(classroom);

        mockMvc.perform(get("/classrooms/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("classroom/classroom"))
                .andExpect(model().attributeExists("classroom"))
                .andExpect(model().attribute("classroom", hasProperty("id", is(id))))
                .andExpect(model().attribute("classroom", hasProperty("number", is(8))));

        verify(classroomService, times(1)).getById(id);
        verifyNoMoreInteractions(classroomService);
    }

    @Test
    void shouldAddClassroom() throws Exception {
        ClassroomDto classroomDto = new ClassroomDto();
        classroomDto.setNumber(8);
        Classroom classroomWithId = Classroom.builder().setId(1).setNumber(8).build();
        Classroom classroomWithoutId = Classroom.builder().setNumber(8).build();
        when(classroomDtoMapper.toEntity(classroomDto)).thenReturn(classroomWithoutId);
        when(classroomService.add(classroomWithoutId)).thenReturn(classroomWithId);

        mockMvc.perform(post("/classrooms/add")
                        .flashAttr("classroom", classroomDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/classrooms"));

        verify(classroomService, times(1)).add(classroomWithoutId);
        verify(classroomDtoMapper, times(1)).toEntity(classroomDto);
        verifyNoMoreInteractions(classroomService, classroomDtoMapper);
    }

    @Test
    void shouldDeleteClassroom() throws Exception {
        int id = 1;
        mockMvc.perform(get("/classrooms/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/classrooms"));

        verify(classroomService, times(1)).removeById(id);
        verifyNoMoreInteractions(classroomService);
    }

    @Test
    void shouldEditClassroom() throws Exception {
        int id = 1;
        Classroom classroom = Classroom.builder().setId(id).setNumber(8).build();
        when(classroomService.getById(id)).thenReturn(classroom);

        mockMvc.perform(get("/classrooms/edit/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("classroom/editform"))
                .andExpect(model().attributeExists("classroom"))
                .andExpect(model().attribute("classroom", equalTo(classroom)));

        verify(classroomService, times(1)).getById(id);
        verifyNoMoreInteractions(classroomService);
    }

    @Test
    void shouldUpdateClassroom() throws Exception {
        ClassroomDto classroomDto = new ClassroomDto(1, 5);
        Classroom updateClassroom = Classroom.builder().setId(1).setNumber(5).build();

        when(classroomDtoMapper.toEntity(classroomDto)).thenReturn(updateClassroom);
        mockMvc.perform(patch("/classrooms/{id}", updateClassroom.getId())
                        .flashAttr("classroom", classroomDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/classrooms"));

        ArgumentCaptor<Classroom> classroomArgumentCaptor = ArgumentCaptor.forClass(Classroom.class);
        verify(classroomService, times(1)).update(classroomArgumentCaptor.capture());
        verify(classroomDtoMapper, times(1)).toEntity(classroomDto);
        verifyNoMoreInteractions(classroomService, classroomDtoMapper);
        Classroom formObject = classroomArgumentCaptor.getValue();
        assertThat(formObject.getId(), is(1));
        assertThat(formObject.getNumber(), is(5));
    }
}

