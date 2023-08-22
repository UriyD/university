package com.degtyaruk.university.dao.mapper.impl;

import com.degtyaruk.university.dao.mapper.LectureMapper;
import com.degtyaruk.university.model.Classroom;
import com.degtyaruk.university.model.Course;
import com.degtyaruk.university.model.Group;
import com.degtyaruk.university.model.Lecture;
import com.degtyaruk.university.model.Professor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public class LectureMapperImpl implements LectureMapper {
    @Override
    public Lecture mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer id = rs.getInt("id");
        LocalTime startTime = rs.getObject("lecture_time_start", LocalTime.class);
        LocalTime finishTime = rs.getObject("lecture_time_finish", LocalTime.class);
        Integer timetableId = rs.getInt("timetable_id");
        Course course = createCourseForLecture(rs);
        Group group = createGroupForLecture(rs);
        Classroom classroom = createClassroomForLecture(rs);
        Professor professor = createProfessorForLecture(rs);
        return Lecture.builder()
                .setId(id)
                .setTimeStartLecture(startTime)
                .setTimeEndLecture(finishTime)
                .setCourse(course)
                .setGroup(group)
                .setClassroom(classroom)
                .setProfessor(professor)
                .build();
    }
    private Course createCourseForLecture(ResultSet rs) throws SQLException {
        Integer courseId = rs.getInt("courses_course_id");
        Integer facultyId = rs.getInt("courses_faculty_id");
        String courseName = rs.getString("course_name");
        return Course.builder()
                .setId(courseId)
                .setName(courseName)
                .build();
    }
    private Group createGroupForLecture(ResultSet rs) throws SQLException {
        Integer groupId = rs.getInt("groups_group_id");
        Integer facultyId = rs.getInt("groups_faculty_id");
        String groupName = rs.getString("group_name");
        return Group.builder()
                .setId(groupId)
                .setName(groupName)
                .build();
    }
    private Classroom createClassroomForLecture(ResultSet rs) throws SQLException {
        Integer classroomId = rs.getInt("classrooms_classroom_id");
        Integer classroomNumber = rs.getInt("number");
        return Classroom.builder()
                .setId(classroomId)
                .setNumber(classroomNumber)
                .build();
    }
    private Professor createProfessorForLecture(ResultSet rs) throws SQLException {
        Integer professorId = rs.getInt("professors_professor_id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String email = rs.getString("email");
        return new Professor.ProfessorBuilder()
                .withId(professorId)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .build();
    }
}

