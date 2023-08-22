package com.degtyaruk.university.model.mapper;

import com.degtyaruk.university.model.Classroom;
import com.degtyaruk.university.model.dto.ClassroomDto;
import org.springframework.stereotype.Component;

@Component
public class ClassroomDtoMapper {

    public Classroom toEntity(ClassroomDto classroomDto) {
        return Classroom.builder()
                .setId(classroomDto.getId())
                .setNumber(classroomDto.getNumber())
                .build();

    }
}

