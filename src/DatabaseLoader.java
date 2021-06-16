import java.sql.*;

public class DatabaseLoader {
   static  Connection LoginConnection;
    //Used to load the crate connection object and load the statment for the database
    public static Statement databaseloadcaller() throws  SQLException, ClassNotFoundException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        LoginConnection=DriverManager.getConnection("jdbc:mysql://localhost:3306/Userdetails", "root", "root");
        return LoginConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);  
       
    }
    
    public static void connectionCloser() throws SQLException
    {
        if(LoginConnection!=null)
        LoginConnection.close();
    }
   
}
