package com.degtyaruk.university.dao.mapper.impl;

import com.degtyaruk.university.dao.mapper.StudentMapper;
import com.degtyaruk.university.model.Course;
import com.degtyaruk.university.model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentMapperImpl implements StudentMapper {
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer studentId = rs.getInt("id");
        Integer groupId = rs.getInt("group_id");
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
        while (rs.next() && rs.getInt("id") == studentId);
        rs.previous();
        return new Student.StudentBuilder()
                .withId(studentId)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .withCourses(courses)
                .build();
    }
}

