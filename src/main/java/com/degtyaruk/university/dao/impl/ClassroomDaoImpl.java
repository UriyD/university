package com.degtyaruk.university.dao.impl;

import com.degtyaruk.university.dao.ClassroomDao;
import com.degtyaruk.university.dao.mapper.impl.ClassroomMapperImpl;
import com.degtyaruk.university.model.Classroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
@Profile("jdbctemplate")
public class ClassroomDaoImpl extends AbstractCrudDaoImpl<Classroom> implements ClassroomDao {

    private static final String ADD_QUERY = "INSERT INTO classrooms (number) values(?)";
    private static final String UPDATE_QUERY = "UPDATE classrooms SET number =? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM classrooms WHERE id = ?";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM classrooms WHERE id = ?";
    private static final String GET_ALL_QUERY = "SELECT * FROM classrooms";
    private static final String GET_ALL_QUERY_ON_PAGE = "SELECT * FROM classrooms LIMIT ? OFFSET ?";

    @Autowired
    public ClassroomDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected void insert(Classroom classroom, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, classroom.getNumber());
    }

    @Override
    protected void update(Classroom classroom, PreparedStatement preparedStatement) throws SQLException {
        insert(classroom, preparedStatement);
        preparedStatement.setInt(2, classroom.getId());
    }

    @Override
    protected Classroom getEntity(Classroom classroom, KeyHolder keyHolder) {
        return Classroom.builder()
                .setId(keyHolder.getKey().intValue())
                .setNumber(classroom.getNumber())
                .build();
    }

    @Override
    protected ClassroomMapperImpl getMapper() {
        return new ClassroomMapperImpl();
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

