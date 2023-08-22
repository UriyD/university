package com.degtyaruk.university.model.mapper;

import com.degtyaruk.university.model.Group;
import com.degtyaruk.university.model.dto.GroupDto;
import com.degtyaruk.university.service.FacultyService;
import com.degtyaruk.university.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GroupDtoMapper {

    private final FacultyService facultyService;

    @Autowired
    public GroupDtoMapper(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    public Group toEntity(GroupDto groupDto) {
        return Group.builder()
                .setId(groupDto.getId())
                .setFaculty(facultyService.getById(groupDto.getFacultyId()))
                .setName(groupDto.getName())
                .build();
    }
}

