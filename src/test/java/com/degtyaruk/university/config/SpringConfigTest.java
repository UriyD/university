package com.degtyaruk.university.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.degtyaruk.university.dao.impl")
@PropertySource("classpath:database.properties")
@Profile("jdbctemplate")
public class SpringConfigTest {
    @Value("${db.user}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Value("${jdbcUrl}")
    private String jdbcUrl;
    @Bean
    public DataSource dataSourceHikariCp() {
        DataSource dataSource;
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        dataSource = new HikariDataSource(config);
        return dataSource;
    }
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSourceHikariCp());
    }
}

