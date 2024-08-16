package com.i2iintern.AOM_demo.Connecters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleDbConnecter {
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        String driver = "oracle.jdbc.OracleDriver";
        String url = "jdbc:oracle:thin:@34.123.74.34:1521/XE";
        String username = "c##emre";  // Kullanıcı adınızı buraya yazın
        String password = "coltu";  // Şifrenizi buraya yazın
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    public static void connectionTrue() {
        System.out.println("CONNECTED");
    }
}
