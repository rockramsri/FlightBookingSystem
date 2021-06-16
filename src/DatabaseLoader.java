import java.sql.*;

public class DatabaseLoader {
   static  Connection LoginConnection;
  
    public static Statement databaseloadcaller() throws  SQLException, ClassNotFoundException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        LoginConnection=DriverManager.getConnection("jdbc:mysql://localhost:3306/Userdetails", "root", "root");
        return LoginConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);  
       
    }
    
    public static void Connectioncloser() throws SQLException
    {
        if(LoginConnection!=null)
        LoginConnection.close();
    }
   
}
