package com.team2.oosdworkshop6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    // Database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/travelexperts";
    private static final String DB_USER = "deepa";
    private static final String DB_PASSWORD = "deepa";

    private Connection connection;

    // Constructor to initialize the database connection
    public DatabaseManager() {
        try {
            // Load the JDBC driver (you may need to add the JDBC driver JAR to your project)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the database connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Get the database connection
    public Connection getConnection() {
        return connection;
    }

    // Close the database connection
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
