package com.kurylchyk.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/parking_system";
    private static final String USER_NAME = "sa";
    private static final String PASSWORD = "";


    public final Connection getConnection() {

        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            System.out.println("Connected");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("is not connected");
            e.printStackTrace();
        }
        return connection;
    }
}
