package com.degtyaruk.university.model.mapper;

import com.degtyaruk.university.model.Student;
import com.degtyaruk.university.model.dto.StudentDto;
import com.degtyaruk.university.service.CourseService;
import com.degtyaruk.university.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class StudentDtoMapper {

    private final CourseService courseService;

    private final GroupService groupService;

    @Autowired
    public StudentDtoMapper(CourseService courseService, GroupService groupService) {
        this.courseService = courseService;
        this.groupService = groupService;
    }

    public Student toEntity(StudentDto studentDto) {
        return new Student.StudentBuilder()
                .withId(studentDto.getId())
                .withFirstName(studentDto.getFirstName())
                .withLastName(studentDto.getLastName())
                .withCourses(studentDto.getCoursesId().stream().map(courseService::getById).collect(Collectors.toList()))
                .withEmail(studentDto.getEmail())
                .withParole(studentDto.getParole())
                .withGroup(groupService.getById(studentDto.getGroupId()))
                .build();
    }
}

