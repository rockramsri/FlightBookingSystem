package Database;

import java.sql.*;

public class DatabaseLoader {
    static Connection databaseConnection;
    static Statement statement;

    // Used to load the crate connection object and load the statment for the
    // database
    public static Statement databaseLoadCaller() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException classNotFoundException) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException classNotFoundExceptionRetry) {
                System.out.println("--Driver class have not been loaded--");
                System.exit(1);
            }

        }

        try {
            databaseConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Userdetails", "root", "root");
        } catch (SQLException sqlException) {
            if (databaseConnection == null)
                try {
                    databaseConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Userdetails", "root",
                            "root");
                } catch (SQLException sqlExceptionRetry) {
                    System.out.println("--Could not create statment--");
                    System.exit(1);
                }

        }

        try {
            statement = databaseConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException sqlException) {
            if (statement == null)
                try {
                    statement = databaseConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
                } catch (SQLException sqlExceptionRetry) {
                    System.out.println("--Could not create statment--");
                    System.exit(1);
                }

        }
        return statement;

    }

    public static void connectionCloser() {
        try {
            if (databaseConnection != null)
                databaseConnection.close();
        } catch (SQLException sqlException) {
            System.out.println("Could not close connections");
        }
        try {
            if (statement != null)
                statement.close();
        } catch (SQLException sqlException) {
            System.out.println("Could not close Statments");
        }

    }

}
