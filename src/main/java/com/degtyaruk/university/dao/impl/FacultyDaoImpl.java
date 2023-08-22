package com.degtyaruk.university.dao.impl;

import com.degtyaruk.university.dao.FacultyDao;
import com.degtyaruk.university.dao.mapper.impl.FacultyMapperImpl;
import com.degtyaruk.university.model.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
@Profile("jdbctemplate")
public class FacultyDaoImpl extends AbstractCrudDaoImpl<Faculty> implements FacultyDao {

    private static final String ADD_QUERY = "INSERT INTO faculties (faculty_name) values(?)";
    private static final String UPDATE_QUERY = "UPDATE faculties SET faculty_name =? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM faculties WHERE id = ?";
    private static final String GET_BY_ID_QUERY = "SELECT f.id, faculty_name, c.id as courses_course_id, course_name, g.id as groups_group_id, group_name\n" +
                                                  "FROM faculties f\n" +
                                                  "LEFT JOIN courses c ON f.id = c.faculty_id\n" +
                                                  "LEFT JOIN groups g ON f.id = g.faculty_id\n" +
                                                  "WHERE f.id = ?\n" +
                                                  "ORDER BY f.id;";
    private static final String GET_ALL_QUERY = "SELECT f.id, faculty_name, c.id as courses_course_id, course_name, g.id as groups_group_id, group_name\n" +
                                                "FROM faculties f\n" +
                                                "LEFT JOIN courses c ON f.id = c.faculty_id\n" +
                                                "LEFT JOIN groups g ON f.id = g.faculty_id\n" +
                                                "ORDER BY id;";
    private static final String GET_ALL_QUERY_ON_PAGE = "SELECT f.id, faculty_name, c.id as courses_course_id, course_name, g.id as groups_group_id, group_name\n" +
                                                        "FROM faculties f\n" +
                                                        "LEFT JOIN courses c ON f.id = c.faculty_id\n" +
                                                        "LEFT JOIN groups g ON f.id = g.faculty_id\n" +
                                                        "ORDER BY id\n" +
                                                        "LIMIT ? OFFSET ?;";

    @Autowired
    public FacultyDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected void insert(Faculty faculty, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, faculty.getName());
    }

    @Override
    protected void update(Faculty entity, PreparedStatement preparedStatement) throws SQLException {
        insert(entity, preparedStatement);
        preparedStatement.setInt(2, entity.getId());
    }

    @Override
    protected Faculty getEntity(Faculty faculty, KeyHolder keyHolder) {
        return Faculty.builder()
                .setId(keyHolder.getKey().intValue())
                .setName(faculty.getName())
                .build();
    }

    @Override
    protected FacultyMapperImpl getMapper() {
        return new FacultyMapperImpl();
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

