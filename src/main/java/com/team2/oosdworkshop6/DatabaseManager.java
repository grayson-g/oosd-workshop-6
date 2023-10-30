package com.team2.oosdworkshop6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    // Database connection parameters
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/travelexperts";
    private static final String DB_USER = "deepa";
    private static final String DB_PASSWORD = "deepa";

    private Connection connection;

    // Constructor to initialize the database connection
    public DatabaseManager() {
        // Grayson - I added the mariadb driver to the pom.xml, so it should be found automatically
        try {
            connection = DriverManager.getConnection(
                    DB_URL,
                    DB_USER,
                    DB_PASSWORD);
        } catch (Exception e) {
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
