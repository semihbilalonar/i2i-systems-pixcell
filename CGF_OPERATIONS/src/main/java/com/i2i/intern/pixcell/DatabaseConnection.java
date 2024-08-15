package com.i2i.intern.pixcell;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Oracle Database connection details
    private static final String DB_URL = "jdbc:oracle:thin:@34.123.74.34:1521:xe";
    private static final String DB_USER = "c##emre";
    private static final String DB_PASSWORD = "coltu";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}