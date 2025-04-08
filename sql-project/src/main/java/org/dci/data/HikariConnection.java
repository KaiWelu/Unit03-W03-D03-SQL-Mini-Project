package org.dci.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariConnection {
    private static final HikariDataSource dataSource;

    static {
        // configure hikari
        HikariConfig config = new HikariConfig();

        // set up database connection in config
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/mini_project");
        config.setUsername("postgres");
        config.setPassword("your_secure_password");
        config.setMaximumPoolSize(10);

        // set up datasource with config
        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        System.out.println("lets go");
        return dataSource.getConnection();
    }
}
