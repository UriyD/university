package com.degtyaruk.university.dao.mapper.impl;

import com.degtyaruk.university.dao.mapper.FacultyMapper;
import com.degtyaruk.university.model.Course;
import com.degtyaruk.university.model.Faculty;
import com.degtyaruk.university.model.Group;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class FacultyMapperImpl implements FacultyMapper {
    @Override
    public Faculty mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer facultyId = rs.getInt("id");
        String facultyName = rs.getString("faculty_name");
        List<Course> courses = new ArrayList<>();
        List<Group> groups = new ArrayList<>();
        do {
            Integer courseId = rs.getInt("courses_course_id");
            while (isNotPresent(courses, courseId, Course::getId)) {
                String courseName = rs.getString("course_name");
                courses.add(Course.builder()
                        .setId(courseId)
                        .setName(courseName)
                        .build());
            }
            Integer groupId = rs.getInt("groups_group_id");
            while (isNotPresent(groups, groupId, Group::getId)) {
                String groupName = rs.getString("group_name");
                groups.add(Group.builder()
                        .setId(groupId)
                        .setName(groupName)
                        .build());
            }
        } while (rs.next() && rs.getInt("id") == facultyId);
        rs.previous();
        return Faculty.builder()
                .setId(facultyId)
                .setName(facultyName)
                .setCourses(courses)
                .setGroups(groups)
                .build();
    }

    private <T> boolean isNotPresent(List<T> list, Integer id, Function<T, Integer> getId) {
        return list.stream()
                .map(getId)
                .noneMatch(idFromList -> Objects.equals(idFromList, id));
    }
}

