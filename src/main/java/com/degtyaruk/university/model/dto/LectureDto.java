package com.degtyaruk.university.model.dto;

import com.degtyaruk.university.model.Classroom;
import com.degtyaruk.university.model.Course;
import com.degtyaruk.university.model.Group;
import com.degtyaruk.university.model.Professor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class LectureDto {

    private Integer id;
    private LocalTime timeStartLecture;
    private LocalTime timeEndLecture;
    private Integer courseId;
    private Integer groupId;
    private Integer classroomId;
    private Integer professorId;
    private Integer timetableId;
}

