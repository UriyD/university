package com.degtyaruk.university.model.mapper;

import com.degtyaruk.university.model.Lecture;
import com.degtyaruk.university.model.dto.LectureDto;
import com.degtyaruk.university.service.ClassroomService;
import com.degtyaruk.university.service.CourseService;
import com.degtyaruk.university.service.GroupService;
import com.degtyaruk.university.service.ProfessorService;
import com.degtyaruk.university.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LectureDtoMapper {

    private final CourseService courseService;
    private final GroupService groupService;
    private final ClassroomService classroomService;
    private final ProfessorService professorService;
    private final TimetableService timetableService;

    @Autowired
    public LectureDtoMapper(CourseService courseService, GroupService groupService, ClassroomService classroomService, ProfessorService professorService, TimetableService timetableService) {
        this.courseService = courseService;
        this.groupService = groupService;
        this.classroomService = classroomService;
        this.professorService = professorService;
        this.timetableService = timetableService;
    }

    public Lecture toEntity(LectureDto lectureDto) {
        return Lecture.builder()
                .setId(lectureDto.getId())
                .setTimeStartLecture(lectureDto.getTimeStartLecture())
                .setTimeEndLecture(lectureDto.getTimeEndLecture())
                .setCourse(courseService.getById(lectureDto.getCourseId()))
                .setGroup(groupService.getById(lectureDto.getGroupId()))
                .setClassroom(classroomService.getById(lectureDto.getClassroomId()))
                .setProfessor(professorService.getById(lectureDto.getProfessorId()))
                .setTimetable(timetableService.getById(lectureDto.getTimetableId()))
                .build();

    }
}



