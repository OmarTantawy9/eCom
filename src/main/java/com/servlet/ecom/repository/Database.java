package com.servlet.ecom.repository;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.servlet.ecom.config.AppConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Database {

    private static final DataSource dataSource;

    static {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");  // Load MySQL DB Driver
            MysqlDataSource ds = new MysqlDataSource();
            ds.setUrl(AppConfig.JDBC_URL);      // MySQL DB URL
            ds.setServerName("localhost");
            ds.setUser(AppConfig.MYSQL_USERNAME);  // MySQL username
            ds.setPassword(AppConfig.MYSQL_PASSWORD); // MySQL password
            dataSource = ds;

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }


}
