import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/** For Handling MysqlDatabase to store and retrieve Information*/
public class DatabaseHandler {
    ResultSet dbResult,dbResult2;
    Statement dbStatement,dbStatement2;

    DatabaseHandler() throws ClassNotFoundException, SQLException
    {
        dbStatement=DatabaseLoader.databaseloadcaller();
        dbStatement2=DatabaseLoader.databaseloadcaller();
    }
    //Used to covert gender based on value passed
    public int gendercalcula(String gender)
    {
       if(gender.equals("Male"))
       return 1;
       else if(gender.equals("Female"))
       return 2;
       else
       return 3;
    }
  
    //Used to Update the password of the user
    public void updatePassword(String password) throws SQLException, IOException
    {
       dbStatement.executeUpdate(new SqlQuery().passwordUpdater(password));
    } 
 
    //Used to return the Current profile of the User logged in
    public ProfileDetails currentProfileDetails() throws SQLException, IOException
    {
        dbResult=dbStatement.executeQuery(new SqlQuery().userInfoQuery(Resource.User_INFO_TABLE_NAME));
        dbResult.next();
        return new ProfileDetails(dbResult.getString(1), dbResult.getString(2), dbResult.getString(3), dbResult.getString(4), dbResult.getString(5), dbResult.getString(6));
        
    }
  
    //Used to clear the File and overWrite with User Id which is logged in
    public void clearTheuserlog(String UserId) throws IOException {
        
        FileWriter fwOb = new FileWriter(Resource.USERLOG_LOCATION, false); 
        PrintWriter writer = new PrintWriter(fwOb);
        writer.print(UserId);
        writer.close();
        fwOb.close();
    }
     
    //Used to validate the login info
    public boolean loginCheck(String id,String password) throws Exception
    {
        String RedUrID="";
        if(id.contains("@"))
        {
       dbResult=dbStatement.executeQuery(new SqlQuery().recordExistMailQuery(Resource.User_INFO_TABLE_NAME, id, password));
        

        }
       else
       {
        dbResult=dbStatement.executeQuery(new SqlQuery().recordExistIDQuery(Resource.User_INFO_TABLE_NAME, id, password));
            
            RedUrID=id;
       }

        //System.out.print("Enter your Password:");
       
      
        dbResult.next();
        if(dbResult.getInt(1)==1)
        {   if(RedUrID.length()==0)
            {
                dbResult=dbStatement.executeQuery(new SqlQuery().getIdByMailQuery(Resource.User_INFO_TABLE_NAME, id));
                dbResult.next();
             RedUrID=dbResult.getString(1);
            }
            clearTheuserlog(RedUrID);
        ExtraProcess.passwordHolder=password;
        return true;
        }
        else
        return false;
    }
     
    //Used to register the and add the data in the database
    public int registerCheck(ProfileDetails profileRegister) throws Exception
    {
        dbResult=dbStatement.executeQuery(new SqlQuery().noOfrecords(Resource.User_INFO_TABLE_NAME));
        dbResult.next();
        //System.out.println(result.getInt(0));
       //  System.out.println(new SqlQuery().insertquery("UserProfile", result.getInt(1)+1,RegName, RegDate, RegMail,Regpassword,RegPhonenumber));
       int tempcount=dbResult.getInt(1)+1;
       dbStatement.executeUpdate(new SqlQuery().insertquery("UserProfile", "Usr"+String.valueOf(tempcount),profileRegister.getName(), profileRegister.getDob(), profileRegister.getEmail(),profileRegister.getPassword(),profileRegister.getPhonenumber()));
         
         clearTheuserlog("Usr"+String.valueOf(tempcount));
         return tempcount;
         
    }

    //Used to return the List of airlines based on condition from the Database
    public List<Airlines> bookingList(String Departurecity,String Arrivalcity,String Dateticket,int NofSeats,String flightClass ) throws SQLException
    {
     List<Airlines> airlinesList=new ArrayList<Airlines>();
     dbResult=dbStatement.executeQuery(new SqlQuery().AvailableFlightquery(Resource.AIRLINE_TICKET_TABLE_NAME, Departurecity, Arrivalcity, Dateticket, NofSeats, flightClass));
     while(dbResult.next())
     {
       airlinesList.add(new Airlines(dbResult.getString(4), dbResult.getString(6), dbResult.getInt(10), dbResult.getInt(9), dbResult.getString(3), dbResult.getString(5), dbResult.getString(2), flightClass, dbResult.getString(1), dbResult.getInt(8)));
     }
     return airlinesList;

    }

    //Used to update the tickets in the airlines
    public int airlinesUpdater(String flightId,int noOfSeats,String sign) throws SQLException
    {
        return dbStatement.executeUpdate(new SqlQuery().UpdateFlightquery(Resource.AIRLINE_TICKET_TABLE_NAME, noOfSeats, flightId,sign));
    }
 
    //used to register the booked ticket in the Bookedtickets table in the database
    public void bookingRegister(BookedTickets bookedTickets) throws SQLException
    {
        String gender="";
        if(bookedTickets.getUserlist().getUgender()==1)
         gender="male";
         else if(bookedTickets.getUserlist().getUgender()==2)
         gender="female";
         else
         gender="other";
        dbStatement.executeUpdate(new SqlQuery().InsertBookedTicketsQuery(Resource.BOOKED_TICKET_TABLE_NAME,bookedTickets.getId(),bookedTickets.getUserlist().getUname(),bookedTickets.getUserlist().getUage(),gender,bookedTickets.getFlightId(),bookedTickets.getTicketId(),bookedTickets.getBookingId(),bookedTickets.getFlight(),bookedTickets.getDepartureTime(),bookedTickets.getArrivalTime(),bookedTickets.getBookedOn(),bookedTickets.getCancelledOn(),bookedTickets.getIsCancelled(),bookedTickets.getAmount()));
    }

    //Used to get mail based on the id form the database
    public String getMailbyId(String UserId) throws SQLException
    {
        dbResult=dbStatement.executeQuery(new SqlQuery().getMailByIdQuery(Resource.User_INFO_TABLE_NAME, UserId));
        dbResult.next();
        return dbResult.getString(1);


    }

    //used to get Bookedtickets form the databse based on condition
  public  List<BookedTickets> getBookedList(String UserId,String isCancelled) throws SQLException
  {
      List<BookedTickets> bookedTicketsList=new ArrayList<BookedTickets>();
    dbResult=dbStatement.executeQuery(new SqlQuery().AvailableSummaryQuery(Resource.BOOKED_TICKET_TABLE_NAME, UserId,isCancelled));
    while(dbResult.next())
    {   UserDetails userDetails=new UserDetails(dbResult.getString(2),Integer.parseInt(dbResult.getString(3)),gendercalcula(dbResult.getString(4)));

        bookedTicketsList.add(new BookedTickets(dbResult.getFloat(14), dbResult.getString(10), dbResult.getString(11),  dbResult.getString(7),  dbResult.getString(12),  dbResult.getString(9),  dbResult.getString(8),  dbResult.getString(5), dbResult.getString(1),  dbResult.getString(13),  dbResult.getString(6), userDetails));
        //upto here
      
    }
    return bookedTicketsList;

  }

  //Used to get the departurecity,arrivalcity and flightclass form the Airlines based in the flightId
  public List<String> depArrivalFlightClass(String flightId) throws SQLException
  {
       List<String> deparriFliclasslist=new ArrayList<String>();
       dbResult=dbStatement.executeQuery(new SqlQuery().depArrivalFlightClassquery(Resource.AIRLINE_TICKET_TABLE_NAME, flightId));
       dbResult.next();
       deparriFliclasslist.add(dbResult.getString(1));
       deparriFliclasslist.add(dbResult.getString(2));
       deparriFliclasslist.add(dbResult.getString(3));
       return deparriFliclasslist;
  }
  
  //Used to get bookedTickets list
  public List<BookedTickets> getBookedIdsBookingList(String bookingId) throws SQLException
  {
    dbResult=dbStatement.executeQuery(new SqlQuery().ticketlistquery(Resource.BOOKED_TICKET_TABLE_NAME, bookingId));
    List<BookedTickets> bookedTicketsList=new ArrayList<BookedTickets>();
    while(dbResult.next())
    {   UserDetails userDetails=new UserDetails(dbResult.getString(2),Integer.parseInt(dbResult.getString(3)),gendercalcula(dbResult.getString(4)));

        bookedTicketsList.add(new BookedTickets(dbResult.getFloat(14), dbResult.getString(10), dbResult.getString(11),  dbResult.getString(7),  dbResult.getString(12),  dbResult.getString(9),  dbResult.getString(8),  dbResult.getString(5), dbResult.getString(1),  dbResult.getString(13),  dbResult.getString(6), userDetails));
        //upto here
      
    }
    return bookedTicketsList;
  }

  //Used to update the the Iscancelled column in booked list 
 public void ticketCanceling(String flightId,String ticketId) throws SQLException
 {
    dbStatement.executeUpdate(new SqlQuery().ticketCancelingQuery(Resource.BOOKED_TICKET_TABLE_NAME, flightId,ticketId.substring(1)));
 }

 //Used search Airplanes based on flighId
 public List<Airlines> searchAirlinesbyflight(String flight) throws SQLException
 {
    List<Airlines> airlinesList=new ArrayList<Airlines>();
    dbResult=dbStatement.executeQuery(new SqlQuery().searchByflight(Resource.AIRLINE_TICKET_TABLE_NAME,flight));
    while(dbResult.next())
    {
        airlinesList.add(new Airlines(dbResult.getString(4), dbResult.getString(6), dbResult.getInt(10), dbResult.getInt(9), dbResult.getString(3), dbResult.getString(5), dbResult.getString(2), dbResult.getString(7), dbResult.getString(1), dbResult.getInt(8)));
    }
    return airlinesList;
    
 }

 //Used to return airlines object list based on city
 public List<Airlines> searchAirlinesbycity(String city) throws SQLException
 {
    List<Airlines> airlinesList=new ArrayList<Airlines>();
    dbResult=dbStatement.executeQuery(new SqlQuery().searchBycity(Resource.AIRLINE_TICKET_TABLE_NAME,city));
    while(dbResult.next())
    {
        airlinesList.add(new Airlines(dbResult.getString(4), dbResult.getString(6), dbResult.getInt(10), dbResult.getInt(9), dbResult.getString(3), dbResult.getString(5), dbResult.getString(2), dbResult.getString(7), dbResult.getString(1), dbResult.getInt(8)));
    }
    return airlinesList;
    
 }

 //Used to return airlines object list based on DepartureDate
 public List<Airlines> searchAirlinesbyDate(String date) throws SQLException
 {
    List<Airlines> airlinesList=new ArrayList<Airlines>();
    dbResult=dbStatement.executeQuery(new SqlQuery().searchByDate(Resource.AIRLINE_TICKET_TABLE_NAME,date));
    while(dbResult.next())
    {
        airlinesList.add(new Airlines(dbResult.getString(4), dbResult.getString(6), dbResult.getInt(10), dbResult.getInt(9), dbResult.getString(3), dbResult.getString(5), dbResult.getString(2), dbResult.getString(7), dbResult.getString(1), dbResult.getInt(8)));
    }
    return airlinesList;
    
 }
 
 //Used to get the nOofSeats in which is cancelled and not cancelled
 public String noOfSeats(String bookingId,String isCancelled) throws SQLException
 {
    dbResult=dbStatement.executeQuery(new SqlQuery().noOfSeatsQuery(Resource.BOOKED_TICKET_TABLE_NAME, bookingId, isCancelled));
    dbResult.next();
   return dbResult.getString(1);
 }
  
 
}
