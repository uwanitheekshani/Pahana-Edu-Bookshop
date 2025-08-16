//package com.example.pahanabillingsystem.utill;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DBUtil {
//    private static final String URL = "jdbc:mysql://localhost:3306/pahanaedubookshop";
//    private static final String USER = "root";
//    private static final String PASSWORD = "1234";
//
//    public static Connection getConnection() throws SQLException {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return DriverManager.getConnection(URL, USER, PASSWORD);
//    }
//}
package com.example.pahanabillingsystem.utill;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/pahanaedubookshop";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    private static Connection connection;   // Single connection instance

    // Private constructor so no one can create an object
    private DBUtil() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL Driver not found!", e);
            }
        }
        return connection;
    }
}