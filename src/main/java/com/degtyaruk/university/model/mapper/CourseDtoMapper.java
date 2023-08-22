package com.degtyaruk.university.model.mapper;

import com.degtyaruk.university.model.Course;
import com.degtyaruk.university.model.dto.CourseDto;
import com.degtyaruk.university.service.FacultyService;
import com.degtyaruk.university.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.DataBinder;
import org.springframework.web.method.annotation.ModelAttributeMethodProcessor;

@Component
public class CourseDtoMapper {

    private final FacultyService facultyService;

    @Autowired
    public CourseDtoMapper(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    public Course toEntity(CourseDto courseDto) {
        return Course.builder()
                .setId(courseDto.getId())
                .setFaculty(facultyService.getById(courseDto.getFacultyId()))
                .setName(courseDto.getName())
                .build();
    }
}

