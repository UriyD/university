package com.degtyaruk.university.dao.mapper.impl;

import com.degtyaruk.university.dao.mapper.ProfessorMapper;
import com.degtyaruk.university.model.Course;
import com.degtyaruk.university.model.Professor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfessorMapperImpl implements ProfessorMapper {
    @Override
    public Professor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer professorId = rs.getInt("id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String email = rs.getString("email");
        List<Course> courses = new ArrayList<>();
        do {
            Integer courseId = rs.getInt("course_id");
            String courseName = rs.getString("course_name");
            Integer facultyId = rs.getInt("faculty_id");
            courses.add(Course.builder()
                    .setId(courseId)
                    .setName(courseName)
                    .build());
        }
        while (rs.next() && rs.getInt("id") == professorId);
        rs.previous();
        return new Professor.ProfessorBuilder()
                .withId(professorId)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .withCourses(courses)
                .build();
    }
}

