package com.degtyaruk.university.model.mapper;

import com.degtyaruk.university.model.Timetable;
import com.degtyaruk.university.model.dto.TimetableDto;
import com.degtyaruk.university.service.LectureService;
import com.degtyaruk.university.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TimetableDtoMapper {

    private final LectureService lectureService;

    @Autowired
    public TimetableDtoMapper(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    public Timetable toEntity(TimetableDto timetableDto) {
        return Timetable.builder()
                .setId(timetableDto.getId())
                .setDate(timetableDto.getDate())
                .build();
    }
}

