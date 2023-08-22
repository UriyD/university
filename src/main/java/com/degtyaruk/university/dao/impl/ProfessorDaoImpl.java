package com.degtyaruk.university.dao.impl;

import com.degtyaruk.university.dao.ProfessorDao;
import com.degtyaruk.university.dao.mapper.impl.ProfessorMapperImpl;
import com.degtyaruk.university.model.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
@Profile("jdbctemplate")
public class ProfessorDaoImpl extends AbstractCrudDaoImpl<Professor> implements ProfessorDao {

    private static final String ADD_QUERY = "INSERT INTO professors (first_name, last_name, email, parole) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE professors SET first_name = ?, last_name = ?, email =?, parole =? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM professors WHERE id = ?";
    private static final String GET_BY_ID_QUERY = "SELECT p.id, first_name, last_name, email, course_id, course_name, faculty_id\n" +
                                                  "FROM professors p\n" +
                                                  "LEFT JOIN professor_course pc ON p.id = pc.professor_id\n" +
                                                  "LEFT JOIN courses c ON pc.course_id = c.id\n" +
                                                  "WHERE p.id = ?;";

    private static final String GET_ALL_QUERY = "SELECT p.id, first_name, last_name, email, course_id, course_name, faculty_id\n" +
                                                "FROM professors p\n" +
                                                "LEFT JOIN professor_course pc ON p.id = pc.professor_id\n" +
                                                "LEFT JOIN courses c ON pc.course_id = c.id\n" +
                                                "ORDER BY p.id;";

    private static final String GET_ALL_QUERY_ON_PAGE = "SELECT p.id, first_name, last_name, email, course_id, course_name, faculty_id\n" +
                                                        "FROM professors p\n" +
                                                        "LEFT JOIN professor_course pc ON p.id = pc.professor_id\n" +
                                                        "LEFT JOIN courses c ON pc.course_id = c.id\n" +
                                                        "ORDER BY p.id\n" +
                                                        "LIMIT ? OFFSET ?;";

    @Autowired
    public ProfessorDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected void insert(Professor professor, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, professor.getFirstName());
        preparedStatement.setString(2, professor.getLastName());
        preparedStatement.setString(3, professor.getEmail());
        preparedStatement.setString(4, professor.getParole());
    }

    @Override
    protected void update(Professor professor, PreparedStatement preparedStatement) throws SQLException {
        insert(professor, preparedStatement);
        preparedStatement.setInt(5, professor.getId());
    }

    @Override
    protected Professor getEntity(Professor professor, KeyHolder keyHolder) {
        return new Professor.ProfessorBuilder()
                .withId(keyHolder.getKey().intValue())
                .withFirstName(professor.getFirstName())
                .withLastName(professor.getLastName())
                .withEmail(professor.getEmail())
                .withParole(professor.getParole())
                .build();
    }

    @Override
    protected ProfessorMapperImpl getMapper() {
        return new ProfessorMapperImpl();
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

