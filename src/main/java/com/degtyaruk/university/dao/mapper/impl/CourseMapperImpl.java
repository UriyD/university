package com.degtyaruk.university.dao.mapper.impl;

import com.degtyaruk.university.dao.mapper.CourseMapper;
import com.degtyaruk.university.model.Course;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseMapperImpl implements CourseMapper {
    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer id = rs.getInt("id");
        Integer facultyId = rs.getInt("faculty_id");
        String name = rs.getString("course_name");
        return Course.builder()
                .setId(id)
                .setName(name)
                .build();
    }
}

