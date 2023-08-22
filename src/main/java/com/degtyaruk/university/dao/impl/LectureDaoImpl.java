package com.degtyaruk.university.dao.impl;

import com.degtyaruk.university.dao.LectureDao;
import com.degtyaruk.university.dao.mapper.impl.LectureMapperImpl;
import com.degtyaruk.university.model.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
@Profile("jdbctemplate")
public class LectureDaoImpl extends AbstractCrudDaoImpl<Lecture> implements LectureDao {
    private static final String ADD_QUERY = "INSERT INTO lectures (lecture_time_start, lecture_time_finish, course_id, group_id, classroom_id, professor_id, timetable_id) values(?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE lectures SET lecture_time_start =?, lecture_time_finish =?, course_id =?, group_id =?, classroom_id =?, professor_id =?, timetable_id =? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM lectures WHERE id = ?";
    private static final String GET_BY_ID_QUERY = "SELECT l.id, l.lecture_time_start, l.lecture_time_finish, l.course_id, l.group_id, l.classroom_id, l.professor_id, l.timetable_id,\n" +
                                                  "c.id as courses_course_id, c.course_name, c.faculty_id as courses_faculty_id,\n" +
                                                  "g.id as groups_group_id, g.faculty_id as groups_faculty_id, g.group_name,\n" +
                                                  "cl.id as classrooms_classroom_id, cl.number,\n" +
                                                  "p.id as professors_professor_id, p.first_name, p.last_name, p.email\n" +
                                                  "FROM lectures l\n" +
                                                  "LEFT JOIN courses c ON l.course_id = c.id\n" +
                                                  "LEFT JOIN groups g ON l.group_id = g.id\n" +
                                                  "LEFT JOIN classrooms cl ON l.classroom_id = cl.id\n" +
                                                  "LEFT JOIN professors p ON l.professor_id = p.id\n" +
                                                  "WHERE l.id = ?\n" +
                                                  "ORDER BY l.id;";
    private static final String GET_ALL_QUERY = "SELECT l.id, l.lecture_time_start, l.lecture_time_finish, l.course_id, l.group_id, l.classroom_id, l.professor_id, l.timetable_id,\n" +
                                                "c.id as courses_course_id, c.course_name, c.faculty_id as courses_faculty_id,\n" +
                                                "g.id as groups_group_id, g.faculty_id as groups_faculty_id, g.group_name,\n" +
                                                "cl.id as classrooms_classroom_id, cl.number,\n" +
                                                "p.id as professors_professor_id, p.first_name, p.last_name, p.email\n" +
                                                "FROM lectures l\n" +
                                                "JOIN courses c ON l.course_id = c.id\n" +
                                                "JOIN groups g ON l.group_id = g.id\n" +
                                                "JOIN classrooms cl ON l.classroom_id = cl.id\n" +
                                                "JOIN professors p ON l.professor_id = p.id\n" +
                                                "ORDER BY l.id;";
    private static final String GET_ALL_QUERY_ON_PAGE = "SELECT l.id, l.lecture_time_start, l.lecture_time_finish, l.course_id, l.group_id, l.classroom_id, l.professor_id, l.timetable_id,\n" +
                                                        "c.id as courses_course_id, c.course_name, c.faculty_id as courses_faculty_id, \n" +
                                                        "g.id as groups_group_id, g.faculty_id as groups_faculty_id, g.group_name,\n" +
                                                        "cl.id as classrooms_classroom_id, cl.number,\n" +
                                                        "p.id as professors_professor_id, p.first_name, p.last_name, p.email\n" +
                                                        "FROM lectures l\n" +
                                                        "LEFT JOIN courses c ON l.course_id = c.id\n" +
                                                        "LEFT JOIN groups g ON l.group_id = g.id\n" +
                                                        "LEFT JOIN classrooms cl ON l.classroom_id = cl.id\n" +
                                                        "LEFT JOIN professors p ON l.professor_id = p.id\n" +
                                                        "ORDER BY l.id\n" +
                                                        "LIMIT ? OFFSET ?;";

    @Autowired
    public LectureDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected void insert(Lecture lecture, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setObject(1, lecture.getTimeStartLecture());
        preparedStatement.setObject(2, lecture.getTimeEndLecture());
        preparedStatement.setObject(3, lecture.getCourse().getId());
        preparedStatement.setObject(4, lecture.getGroup().getId());
        preparedStatement.setObject(5, lecture.getClassroom().getId());
        preparedStatement.setObject(6, lecture.getProfessor().getId());
        preparedStatement.setObject(7, lecture.getTimetable());
    }

    @Override
    protected void update(Lecture lecture, PreparedStatement preparedStatement) throws SQLException {
        insert(lecture, preparedStatement);
        preparedStatement.setInt(8, lecture.getId());
    }

    @Override
    protected Lecture getEntity(Lecture lecture, KeyHolder keyHolder) {
        return Lecture.builder()
                .setId(keyHolder.getKey().intValue())
                .setTimeStartLecture(lecture.getTimeStartLecture())
                .setTimeEndLecture(lecture.getTimeEndLecture())
                .setCourse(lecture.getCourse())
                .setGroup(lecture.getGroup())
                .setClassroom(lecture.getClassroom())
                .setProfessor(lecture.getProfessor())
                .setTimetable(lecture.getTimetable())
                .build();
    }

    @Override
    protected LectureMapperImpl getMapper() {
        return new LectureMapperImpl();
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

