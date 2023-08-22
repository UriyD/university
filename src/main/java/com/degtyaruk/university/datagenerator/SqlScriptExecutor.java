package com.degtyaruk.university.datagenerator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public interface SqlScriptExecutor {
    void executeSqlScript(String fileNameSqlScript) throws IOException, SQLException;
}

