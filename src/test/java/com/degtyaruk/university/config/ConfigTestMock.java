package com.degtyaruk.university.config;

import com.degtyaruk.university.model.mapper.ClassroomDtoMapper;
import com.degtyaruk.university.model.mapper.CourseDtoMapper;
import com.degtyaruk.university.model.mapper.FacultyDtoMapper;
import com.degtyaruk.university.model.mapper.GroupDtoMapper;
import com.degtyaruk.university.model.mapper.LectureDtoMapper;
import com.degtyaruk.university.model.mapper.ProfessorDtoMapper;
import com.degtyaruk.university.model.mapper.StudentDtoMapper;
import com.degtyaruk.university.model.mapper.TimetableDtoMapper;
import com.degtyaruk.university.service.ClassroomService;
import com.degtyaruk.university.service.CourseService;
import com.degtyaruk.university.service.FacultyService;
import com.degtyaruk.university.service.GroupService;
import com.degtyaruk.university.service.LectureService;
import com.degtyaruk.university.service.ProfessorService;
import com.degtyaruk.university.service.StudentService;
import com.degtyaruk.university.service.TimetableService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigTestMock {

    @Bean
    public ClassroomService doMockService() {
        return Mockito.mock(ClassroomService.class);
    }

    @Bean
    public ClassroomDtoMapper doMockDtoMapper() {
        return Mockito.mock(ClassroomDtoMapper.class);
    }

    @Bean
    public CourseService doMockCourseService() {
        return Mockito.mock(CourseService.class);
    }

    @Bean
    public CourseDtoMapper doMockCourseDtoMapper() {
        return Mockito.mock(CourseDtoMapper.class);
    }

    @Bean
    public FacultyService doMockFacultyService() {
        return Mockito.mock(FacultyService.class);
    }

    @Bean
    public FacultyDtoMapper doMockFacultyDtoMapper() {
        return Mockito.mock(FacultyDtoMapper.class);
    }

    @Bean
    public GroupService doMockGroupService() {
        return Mockito.mock(GroupService.class);
    }

    @Bean
    public GroupDtoMapper doMockGroupDtoMapper() {
        return Mockito.mock(GroupDtoMapper.class);
    }

    @Bean
    public LectureService doMockLectureService() {
        return Mockito.mock(LectureService.class);
    }

    @Bean
    public LectureDtoMapper doMockLectureDtoMapper() {
        return Mockito.mock(LectureDtoMapper.class);
    }

    @Bean
    public ProfessorService doMockProfessorService() {
        return Mockito.mock(ProfessorService.class);
    }

    @Bean
    public ProfessorDtoMapper doMockProfessorDtoMapper() {
        return Mockito.mock(ProfessorDtoMapper.class);
    }

    @Bean
    public StudentService doMockStudentService() {
        return Mockito.mock(StudentService.class);
    }

    @Bean
    public StudentDtoMapper doMockStudentDtoMapper() {
        return Mockito.mock(StudentDtoMapper.class);
    }

    @Bean
    public TimetableService doMockTimetableService() {
        return Mockito.mock(TimetableService.class);
    }

    @Bean
    public TimetableDtoMapper doMockTimetableDtoMapper() {
        return Mockito.mock(TimetableDtoMapper.class);
    }
}

