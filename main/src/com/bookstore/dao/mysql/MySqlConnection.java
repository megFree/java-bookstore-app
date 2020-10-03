package com.bookstore.dao.mysql;

import com.bookstore.dao.ConnectionManager;
import com.bookstore.util.annotations.ApplicationProperty;
import com.bookstore.util.annotations.Singleton;
import com.bookstore.util.enums.ApplicationPropertyTypesEnum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class MySqlConnection implements ConnectionManager {
    @ApplicationProperty(type = ApplicationPropertyTypesEnum.STRING)
    private static String host;
    @ApplicationProperty(type = ApplicationPropertyTypesEnum.STRING)
    private static String pass;
    @ApplicationProperty(type = ApplicationPropertyTypesEnum.STRING)
    private static String user;

    private Connection connection;

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        if (connection == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(host, user, pass);
        }
        return connection;
    }
}
