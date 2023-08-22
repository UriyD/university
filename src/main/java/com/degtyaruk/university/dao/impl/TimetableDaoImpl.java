package com.degtyaruk.university.dao.impl;


import com.degtyaruk.university.dao.TimetableDao;
import com.degtyaruk.university.dao.mapper.impl.TimetableMapperImpl;
import com.degtyaruk.university.model.Professor;
import com.degtyaruk.university.model.Student;
import com.degtyaruk.university.model.Timetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
@Profile("jdbctemplate")
public class TimetableDaoImpl extends AbstractCrudDaoImpl<Timetable> implements TimetableDao {

    private static final String ADD_QUERY = "INSERT INTO timetables (date) values(?)";
    private static final String UPDATE_QUERY = "UPDATE timetables SET date =? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM timetables WHERE id = ?";

    private static final String JOIN_FOR_GET_QUERIES = "SELECT t.id, t.date, l.id as lectures_lecture_id, lecture_time_start,\n" +
                                                        "lecture_time_finish, l.course_id, l.group_id, l.classroom_id, l.professor_id,\n" +
                                                        "c.id as courses_course_id, c.course_name, c.faculty_id as courses_faculty_id,\n" +
                                                        "g.id as groups_group_id, g.faculty_id as groups_faculty_id, g.group_name,\n" +
                                                        "cl.id as classrooms_classroom_id, cl.number,\n" +
                                                        "p.id as professors_professor_id, p.first_name, p.last_name, p.email\n" +
                                                        "FROM timetables t\n" +
                                                        "LEFT JOIN lectures l ON t.id = l.timetable_id\n" +
                                                        "LEFT JOIN courses c ON l.course_id = c.id\n" +
                                                        "LEFT JOIN groups g ON l.group_id = g.id\n" +
                                                        "LEFT JOIN classrooms cl ON l.classroom_id = cl.id\n" +
                                                        "LEFT JOIN professors p ON l.professor_id = p.id\n";
    private static final String GET_BY_ID_QUERY = JOIN_FOR_GET_QUERIES +
                                                    "WHERE t.id = ?\n" +
                                                    "ORDER BY t.id;";
    private static final String GET_ALL_QUERY = JOIN_FOR_GET_QUERIES +
                                                    "ORDER BY t.id;";
    private static final String GET_ALL_QUERY_ON_PAGE = JOIN_FOR_GET_QUERIES +
                                                        "ORDER BY t.id\n" +
                                                        "LIMIT ? OFFSET ?;";

    private static final String GET_STUDENT_TIMETABLE_FOR_PERIOD = JOIN_FOR_GET_QUERIES +
                                    "WHERE t.date BETWEEN ? AND ? AND l.group_id = ?\n" +
                                    "ORDER BY t.id;";

    private static final String GET_PROFESSOR_TIMETABLE_FOR_PERIOD = JOIN_FOR_GET_QUERIES +
                                  "WHERE t.date BETWEEN ? AND ? AND l.professor_id = ?\n" +
                                  "ORDER BY t.id;";


    @Autowired
    public TimetableDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected void insert(Timetable timetable, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setObject(1, timetable.getDate());
    }

    @Override
    protected void update(Timetable timetable, PreparedStatement preparedStatement) throws SQLException {
        insert(timetable, preparedStatement);
        preparedStatement.setInt(2, timetable.getId());
    }

    @Override
    protected Timetable getEntity(Timetable timetable, KeyHolder keyHolder) {
        return Timetable.builder()
                .setId(keyHolder.getKey().intValue())
                .setDate(timetable.getDate())
                .build();
    }

    @Override
    protected TimetableMapperImpl getMapper() {
        return new TimetableMapperImpl();
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

    @Override
    public List<Timetable> selectStudentTimetableToDateFromPeriod(LocalDate startPeriod, LocalDate finishPeriod, Student student) {
        return jdbcTemplate.query(getStatementCreatorForStudentTimetableFromPeriod(startPeriod, finishPeriod, student), getMapper());
    }

    @Override
    public List<Timetable> selectProfessorTimetableToDateFromPeriod(LocalDate startPeriod, LocalDate finishPeriod, Professor professor) {
        return jdbcTemplate.query(getStatementCreatorForProfessorTimetableFromPeriod(startPeriod, finishPeriod, professor), getMapper());
    }

    private PreparedStatementCreator getStatementCreatorForStudentTimetableFromPeriod(LocalDate start, LocalDate finish, Student student) {
        return connection -> {
            PreparedStatement statement = connection.prepareStatement(GET_STUDENT_TIMETABLE_FOR_PERIOD, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            statement.setDate(1, Date.valueOf(start));
            statement.setDate(2, Date.valueOf(finish));
            statement.setInt(3, student.getGroup().getId());
            return statement;
        };
    }

    private PreparedStatementCreator getStatementCreatorForProfessorTimetableFromPeriod(LocalDate start, LocalDate finish, Professor professor) {
        return connection -> {
            PreparedStatement statement = connection.prepareStatement(GET_PROFESSOR_TIMETABLE_FOR_PERIOD, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            statement.setDate(1, Date.valueOf(start));
            statement.setDate(2, Date.valueOf(finish));
            statement.setInt(3, professor.getId());
            return statement;
        };
    }
}

