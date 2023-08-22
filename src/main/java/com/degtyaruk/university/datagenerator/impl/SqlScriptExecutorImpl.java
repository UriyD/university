package com.degtyaruk.university.datagenerator.impl;


import com.degtyaruk.university.datagenerator.SqlScriptExecutor;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class SqlScriptExecutorImpl implements SqlScriptExecutor {

    private final DataSource dataSource;

    @Autowired
    public SqlScriptExecutorImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void executeSqlScript(String fileNameSqlScript) throws IOException, SQLException {
        try (final InputStream fileInputStream = SqlScriptExecutorImpl.class.getClassLoader().getResourceAsStream(fileNameSqlScript);
             final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
             Connection connection = dataSource.getConnection()) {
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            scriptRunner.runScript(bufferedReader);
        }
    }
}

