package com.degtyaruk.university.dao.impl;

import com.degtyaruk.university.dao.CrudDao;
import com.degtyaruk.university.dao.Page;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Profile("jdbctemplate")
public abstract class AbstractCrudDaoImpl<E> implements CrudDao<E, Integer> {

    protected final JdbcTemplate jdbcTemplate;

    protected AbstractCrudDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <S extends E> E add(S entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(getStatementCreatorForInsert(entity, getAddQuery()), keyHolder);
        return getEntity(entity, keyHolder);
    }

    @Override
    public void update(E entity) {
        jdbcTemplate.update(getUpdateQuery(), ps -> update(entity, ps));
    }

    @Override
    public void removeById(Integer id) {
        jdbcTemplate.update(getDeleteByIdQuery(), id);
    }

    @Override
    public Optional<E> getById(Integer id) {
        try (Stream<E> stream = jdbcTemplate.queryForStream(getStatementCreatorForGetById(getByIdQuery(), id), getMapper())) {
            return stream.findFirst();
        }
    }

    @Override
    public List<E> getAll() {
        return jdbcTemplate.query(getStatementCreatorForGetAll(getAllQuery()), getMapper());
    }

    @Override
    public List<E> getAll(Page page) {
        return jdbcTemplate.query(getStatementCreatorForGetAllOnPage(getAllOnPageQuery(), page), getMapper());
    }

    private PreparedStatementCreator getStatementCreatorForInsert(E entity, String query) {
        return connection -> {
            PreparedStatement statement = connection.prepareStatement(query, new String[]{"id"});
            insert(entity, statement);
            return statement;
        };
    }

    private PreparedStatement getScrollableStatement(Connection connection, String query) throws SQLException {
        return connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
    }

    private PreparedStatementCreator getStatementCreatorForGetAll(String query) {
        return connection -> getScrollableStatement(connection, query);
    }

    private PreparedStatementCreator getStatementCreatorForGetById(String query, Integer id) {
        return connection -> {
            PreparedStatement statement = getScrollableStatement(connection, query);
            statement.setInt(1, id);
            return statement;
        };
    }

    private PreparedStatementCreator getStatementCreatorForGetAllOnPage(String query, Page page) {
        return connection -> {
            PreparedStatement statement = getScrollableStatement(connection, query);
            statement.setInt(1, page.getLimit());
            statement.setInt(2, page.getOffset());
            return statement;
        };
    }

    protected abstract void insert(E entity, PreparedStatement preparedStatement) throws SQLException;

    protected abstract void update(E entity, PreparedStatement preparedStatement) throws SQLException;

    protected abstract <S extends E> E getEntity(S entity, KeyHolder keyHolder);

    protected abstract RowMapper<E> getMapper();

    protected abstract String getAddQuery();
    protected abstract String getUpdateQuery();
    protected abstract String getDeleteByIdQuery();
    protected abstract String getByIdQuery();
    protected abstract String getAllQuery();
    protected abstract String getAllOnPageQuery();
}

