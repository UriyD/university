package com.degtyaruk.university.dao.mapper.impl;

import com.degtyaruk.university.dao.mapper.TimetableMapper;
import com.degtyaruk.university.model.Classroom;
import com.degtyaruk.university.model.Course;
import com.degtyaruk.university.model.Group;
import com.degtyaruk.university.model.Lecture;
import com.degtyaruk.university.model.Professor;
import com.degtyaruk.university.model.Timetable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimetableMapperImpl implements TimetableMapper {
    @Override
    public Timetable mapRow(ResultSet rs, int rowNum) throws SQLException {
        int timetableId = rs.getInt("id");
        LocalDate date = rs.getObject("date", LocalDate.class);

        List<Lecture> lectures = new ArrayList<>();
        do {
            Integer lectureId = rs.getInt("lectures_lecture_id");
            LocalTime timeStart = rs.getObject("lecture_time_start", LocalTime.class);
            LocalTime timeFinish = rs.getObject("lecture_time_finish", LocalTime.class);

            lectures.add(Lecture.builder()
                    .setId(lectureId)
                    .setCourse(createCourseForLecture(rs))
                    .setGroup(createGroupForLecture(rs))
                    .setClassroom(createClassroomForLecture(rs))
                    .setProfessor(createProfessorForLecture(rs))
                    .setTimeStartLecture(timeStart)
                    .setTimeEndLecture(timeFinish)
                    .build());
        }
        while (rs.next() && rs.getInt("id") == timetableId);
        rs.previous();
        return Timetable.builder()
                .setId(timetableId)
                .setDate(date)
                .setLectures(lectures)
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

