package com.degtyaruk.university.dao.impl;

import com.degtyaruk.university.dao.StudentDao;
import com.degtyaruk.university.dao.mapper.impl.StudentMapperImpl;
import com.degtyaruk.university.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
@Profile("jdbctemplate")
public class StudentDaoImpl extends AbstractCrudDaoImpl<Student> implements StudentDao {

    private static final String ADD_QUERY = "INSERT INTO students (group_id, first_name, last_name, email, parole) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE students SET group_id = ?, first_name = ?, last_name = ?, email =?, parole =? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM students WHERE id = ?";
    private static final String GET_BY_ID_QUERY = "SELECT s.id, group_id, first_name, last_name, email, course_id, course_name, faculty_id\n" +
                                                  "FROM students s\n" +
                                                  "LEFT JOIN student_course sc ON s.id = sc.student_id\n" +
                                                  "LEFT JOIN courses c ON sc.course_id = c.id\n" +
                                                  "WHERE s.id = ?";

    private static final String GET_ALL_QUERY = "SELECT s.id, group_id, first_name, last_name, email, course_id, course_name, faculty_id\n" +
                                                "FROM students s\n" +
                                                "LEFT JOIN student_course sc ON s.id = sc.student_id\n" +
                                                "LEFT JOIN courses c ON sc.course_id = c.id\n" +
                                                "ORDER BY s.id;";

    private static final String GET_ALL_QUERY_ON_PAGE = "SELECT s.id, group_id, first_name, last_name, email, course_id, course_name, faculty_id\n" +
                                                        "FROM students s\n" +
                                                        "LEFT JOIN student_course sc ON s.id = sc.student_id\n" +
                                                        "LEFT JOIN courses c ON sc.course_id = c.id\n" +
                                                        "ORDER BY s.id\n" +
                                                        "LIMIT ? OFFSET ?;";

    @Autowired
    public StudentDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected void insert(Student student, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setObject(1, student.getGroup());
        preparedStatement.setString(2, student.getFirstName());
        preparedStatement.setString(3, student.getLastName());
        preparedStatement.setString(4, student.getEmail());
        preparedStatement.setString(5, student.getParole());
    }

    @Override
    protected void update(Student student, PreparedStatement preparedStatement) throws SQLException {
        insert(student, preparedStatement);
        preparedStatement.setInt(6, student.getId());
    }

    @Override
    protected Student getEntity(Student student, KeyHolder keyHolder) {
        return new Student.StudentBuilder()
                .withId(keyHolder.getKey().intValue())
                .withGroup(student.getGroup())
                .withFirstName(student.getFirstName())
                .withLastName(student.getLastName())
                .withEmail(student.getEmail())
                .withParole(student.getParole())
                .build();
    }

    @Override
    protected StudentMapperImpl getMapper() {
        return new StudentMapperImpl();
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

