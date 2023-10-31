package com.team2.oosdworkshop6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/* OOSD Workshop 6 - Team 2
 * This class handles creating a database connection so that we store URL/username/password
 * in a single place for editing
 */
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
