package com.degtyaruk.university.dao.mapper.impl;

import com.degtyaruk.university.dao.mapper.ClassroomMapper;
import com.degtyaruk.university.model.Classroom;

import java.sql.ResultSet;
import java.sql.SQLException;
public class ClassroomMapperImpl implements ClassroomMapper {
    @Override
    public Classroom mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer id = rs.getInt("id");
        Integer number = rs.getInt("number");
        return Classroom.builder()
                .setId(id)
                .setNumber(number)
                .build();
    }
}

