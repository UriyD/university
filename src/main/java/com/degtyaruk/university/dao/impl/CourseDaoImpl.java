package com.degtyaruk.university.dao.impl;

import com.degtyaruk.university.dao.CourseDao;
import com.degtyaruk.university.dao.mapper.impl.CourseMapperImpl;
import com.degtyaruk.university.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
@Profile("jdbctemplate")
public class CourseDaoImpl extends AbstractCrudDaoImpl<Course> implements CourseDao {

    private static final String ADD_QUERY = "INSERT INTO courses (faculty_id, course_name) values(?, ?)";
    private static final String UPDATE_QUERY = "UPDATE courses SET faculty_id =?, course_name =? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM courses WHERE id = ?";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM courses WHERE id = ?";
    private static final String GET_ALL_QUERY = "SELECT * FROM courses";
    private static final String GET_ALL_QUERY_ON_PAGE = "SELECT * FROM courses LIMIT ? OFFSET ?";

    @Autowired
    public CourseDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected void insert(Course course, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, course.getFaculty().getId());
        preparedStatement.setString(2, course.getName());
    }

    @Override
    protected void update(Course course, PreparedStatement preparedStatement) throws SQLException {
        insert(course, preparedStatement);
        preparedStatement.setInt(3, course.getId());
    }

    @Override
    protected Course getEntity(Course course, KeyHolder keyHolder) {
        return Course.builder()
                .setId(keyHolder.getKey().intValue())
                .setFaculty(course.getFaculty())
                .setName(course.getName())
                .build();
    }

    @Override
    protected CourseMapperImpl getMapper() {
        return new CourseMapperImpl();
    }

    @Override
    protected String getAddQuery() {
        return ADD_QUERY;
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String getDeleteByIdQuery() {
        return DELETE_BY_ID_QUERY;
    }

    @Override
    protected String getByIdQuery() {
        return GET_BY_ID_QUERY;
    }

    @Override
    protected String getAllQuery() {
        return GET_ALL_QUERY;
    }

    @Override
    protected String getAllOnPageQuery() {
        return GET_ALL_QUERY_ON_PAGE;
    }
}

