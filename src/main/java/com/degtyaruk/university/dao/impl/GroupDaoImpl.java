package com.degtyaruk.university.dao.impl;

import com.degtyaruk.university.dao.GroupDao;
import com.degtyaruk.university.dao.mapper.impl.GroupMapperImpl;
import com.degtyaruk.university.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
@Profile("jdbctemplate")
public class GroupDaoImpl extends AbstractCrudDaoImpl<Group> implements GroupDao {
    private static final String ADD_QUERY = "INSERT INTO groups (faculty_id, group_name) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE groups SET faculty_id =?, group_name = ? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM groups WHERE id = ?";
    private static final String GET_BY_ID_QUERY = "SELECT g.id, g.faculty_id, g.group_name, s.id as students_student_id, s.first_name, s.last_name, s.email, s.group_id\n" +
                                                  "FROM groups g\n" +
                                                  "LEFT JOIN students s ON g.id = s.group_id\n" +
                                                  "WHERE g.id = ?\n" +
                                                  "ORDER BY id";
    private static final String GET_ALL_QUERY = "SELECT g.id, g.faculty_id, g.group_name, s.id as students_student_id, s.first_name, s.last_name, s.email, s.group_id\n" +
                                                "FROM groups g\n" +
                                                "LEFT JOIN students s ON g.id = s.group_id\n" +
                                                "ORDER BY id";
    private static final String GET_ALL_QUERY_ON_PAGE = "SELECT g.id, g.faculty_id, g.group_name, s.id as students_student_id, s.first_name, s.last_name, s.email, s.group_id\n" +
                                                        "FROM groups g\n" +
                                                        "JOIN students s ON g.id = s.group_id\n" +
                                                        "ORDER BY id\n" +
                                                        "LIMIT ? OFFSET ?";

    @Autowired
    public GroupDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected void insert(Group entity, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, entity.getFaculty().getId());
        preparedStatement.setString(2, entity.getName());
    }

    @Override
    protected void update(Group entity, PreparedStatement preparedStatement) throws SQLException {
        insert(entity, preparedStatement);
        preparedStatement.setInt(3, entity.getId());
    }

    @Override
    protected Group getEntity(Group group, KeyHolder keyHolder) {
        return Group.builder()
                .setId(keyHolder.getKey().intValue())
                .setFaculty(group.getFaculty())
                .setName(group.getName())
                .build();
    }

    @Override
    protected GroupMapperImpl getMapper() {
        return new GroupMapperImpl();
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

