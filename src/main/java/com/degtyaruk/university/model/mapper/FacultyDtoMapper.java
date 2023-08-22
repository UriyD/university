package com.degtyaruk.university.model.mapper;

import com.degtyaruk.university.model.Faculty;
import com.degtyaruk.university.model.dto.FacultyDto;
import com.degtyaruk.university.service.CourseService;
import com.degtyaruk.university.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FacultyDtoMapper {

    private final CourseService courseService;
    private final GroupService groupService;

    @Autowired
    public FacultyDtoMapper(CourseService courseService, GroupService groupService) {
        this.courseService = courseService;
        this.groupService = groupService;
    }

    public Faculty toEntity(FacultyDto facultyDto) {
        return Faculty.builder()
                .setId(facultyDto.getId())
                .setName(facultyDto.getName())
                .build();
    }
}

