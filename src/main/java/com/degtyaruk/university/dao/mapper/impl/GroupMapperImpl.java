package com.degtyaruk.university.dao.mapper.impl;

import com.degtyaruk.university.dao.mapper.GroupMapper;
import com.degtyaruk.university.model.Group;
import com.degtyaruk.university.model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupMapperImpl implements GroupMapper {
    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
        int groupId = rs.getInt("id");
        int facultyId = rs.getInt("faculty_id");
        String groupName = rs.getString("group_name");
        List<Student> students = new ArrayList<>();
        do {
            Integer studentId = rs.getInt("students_student_id");
            Integer studentGroupId = rs.getInt("group_id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String email = rs.getString("email");
            students.add(new Student.StudentBuilder()
                    .withId(studentId)
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .withEmail(email)
                    .build());
        }
        while (rs.next() && rs.getInt("id") == groupId);
        rs.previous();
        return Group.builder()
                .setId(groupId)
                .setName(groupName)
                .setStudents(students)
                .build();

    }
}

