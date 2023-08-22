package com.degtyaruk.university.model.mapper;

import com.degtyaruk.university.model.Professor;
import com.degtyaruk.university.model.dto.ProfessorDto;
import com.degtyaruk.university.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProfessorDtoMapper {

    private final CourseService courseService;

    @Autowired
    public ProfessorDtoMapper(CourseService courseService) {
        this.courseService = courseService;
    }

    public Professor toEntity(ProfessorDto professorDto) {
        return new Professor.ProfessorBuilder()
                .withId(professorDto.getId())
                .withFirstName(professorDto.getFirstName())
                .withLastName(professorDto.getLastName())
                .withCourses(professorDto.getCoursesId().stream().map(courseService::getById).collect(Collectors.toList()))
                .withEmail(professorDto.getEmail())
                .withParole(professorDto.getParole())
                .build();
    }
}

