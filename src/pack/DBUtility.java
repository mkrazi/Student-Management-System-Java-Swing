package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Handles all database connections for the application.
 */
public class DBUtility {

    // IMPORTANT: Make sure these details are correct for your local MySQL server.
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sms";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Razi@123";

    public static Connection getConnection() {
        try {
            // Ensure the MySQL JDBC driver is loaded.
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish the connection.
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found. Please add the MySQL connector JAR to your project libraries.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection failed. Check your URL, username, and password.");
            e.printStackTrace();
        }
        // Return null if any error occurs.
        return null;
    }
}