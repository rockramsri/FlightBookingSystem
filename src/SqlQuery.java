import java.io.IOException;
 
/**
 * This Classs is used to return the SqlQuery 
 */
public class SqlQuery {
    
    public String noOfrecords(String tablename)
    {
        return  "select count(*) from "+tablename;
    }
    public String selectquery(String tablename)
    {
       return  "select * from "+tablename;
    }
    public String passwordUpdater(String password) throws IOException
    {
      return "update "+Resource.User_INFO_TABLE_NAME+" SET password='"+password+"' where ID='"+ExtraProcess.userIdgetter()+"'";
    }
    public String userInfoQuery(String tablename) throws IOException
    {
        return selectquery(tablename)+" ID='"+ExtraProcess.userIdgetter()+"'";
    }
    public String getIdByMailQuery(String tablename,String mail)
    {
        return "select ID from "+tablename+" where Email='"+mail+"'";
    }
    public String getMailByIdQuery(String tablename,String ID)
    {
        return "select Email from "+tablename+" where ID='"+ID+"'";
    }
    public String insertquery(String tablename,String id,String name,String dob,String email,String password,String Phonenumber)
    {
        
    return "insert into "+tablename+" values('"+id+"','"+name+"','"+dob+"','"+email+"','"+password+"','"+Phonenumber+"')";
    }
    public  String recordExistMailQuery(String tablename,String email,String pass)
    {  
        String addedString=" and password='"+pass+"'";
        if(pass.length()==0)
        addedString="";
        return "select exists("+selectquery(tablename)+" where Email='"+email+"'"+addedString+")";
    }
    public  String recordExistIDQuery(String tablename,String userid,String pass)
    {
        String addedString=" and password='"+pass+"'";
        if(pass.length()==0)
        addedString="";
        return "select exists("+selectquery(tablename)+" where ID='"+userid+"'"+addedString+")";
    }
 /* tick*/   public String AvailableFlightquery(String tablename,String depcity,String arrcity,String depdate,int NofSeats,String flightClass)
    {   
        String alternateDeptimeStr=" and Departuretime like '%"+depdate+"%'";
        if(depdate.length()==0)
        {
            alternateDeptimeStr="";
        }
         return "select * from "+tablename+" where Departurecity='"+depcity+"' and Arrivalcity='"+arrcity+"' and currentSeatsAvailable>="+String.valueOf(NofSeats)+alternateDeptimeStr+" and flightclass='"+flightClass+"'";
    }
  /* tick*/   public String UpdateFlightquery(String tablename,int noofseats,String flightId,String sign)
    {
        return "Update "+tablename+" SET currentSeatsAvailable=currentSeatsAvailable"+sign+String.valueOf(noofseats)+" Where Flightid='"+flightId+"'";
    }
   /* tick*/  public String InsertBookedTicketsQuery(String tablename,String userId,String username,int userage,String usergender,String flightId,String  ticketId,String orderId,String flight,String Deptime,String Arrtime,String bookedOn,String cancelledOn,String isCancelled,float amount)
    {   
        return "insert into "+tablename+" values('"+userId+"','"+username+"','"+String.valueOf(userage)+"','"+usergender+"','"+flightId+"','"+ticketId+"','"+orderId+"','"+flight+"','"+Deptime+"','"+Arrtime+"','"+bookedOn+"','"+cancelledOn+"','"+isCancelled+"',"+String.valueOf(amount)+")";
    }
    public String AvailableSummaryQuery(String tablename,String userId,String status)
    {
    return  "select * from "+tablename+" where ID='"+userId+"' and IsCancelled='"+status+"'";
    }
    public String ticketlistquery(String tablename,String bookingId)
    {
       return "select * from "+tablename+" where BookingId='"+bookingId+"' and IsCancelled='no'";
    }
    public String ticketCancelingQuery(String tablename,String BookingId,String ticketId)
    {
        return "Update "+tablename+" SET CancelledOn='"+ExtraProcess.dateTimeGetter()+"',IsCancelled='yes' where BookingId='"+BookingId+"' and TicketId like '_"+ticketId+"'";
    }
    public String depArrivalFlightClassquery(String tablename,String flightId)
    {
        return "select Departurecity,Arrivalcity,flightclass from "+tablename+" where FlightId='"+flightId+"'";
    }
    public String noOfSeatsQuery(String tablename,String bookingId,String cancel)
    {
        return "select count(*) from "+tablename+" where BookingId='"+bookingId+"' and IsCancelled='"+cancel+"' group by BookingId";
    }
    
    //search functions
    public String searchByflight(String tablename,String flightname)
    {
        return "select * from "+tablename+" where Flight='"+flightname+"'";
    }
    public String searchBycity(String tablename,String cityname)
    {
        return "select * from "+tablename+" where Departurecity='"+cityname+"' or Arrivalcity='"+cityname+"'";
    }
    public String searchByDate(String tablename,String date)
    {
        return "select * from "+tablename+" where Departuretime like '%"+date+"%' or Arrivaltime like '%"+date+"%'";
    }



  /*  public String CancelingticketQuery(String tablename,String flightId)
    {
       return "delete from "+tablename+" where Flightid='"+flightId+"'";
    } */
    
}
