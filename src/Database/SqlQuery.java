package Database;
import java.io.IOException;
import Utils.*;
 
/**
 * This Classs is used to return the SqlQuery 
 */
public class SqlQuery {
    
   /* public String noOfrecords(String tablename)
    {
        return  "select count(*) from "+tablename;
    }
    */
    public String selectquery(String tablename)
    {
       return  "select * from "+tablename;
    }
    public String passwordUpdater(String password) throws IOException
    {
      return "update "+Resource.User_INFO_TABLE_NAME+" SET password='"+password+"' where ID='"+ExtraProcess.currentUserDetails.getId()+"'";
    }
    public String userInfoQuery(String tablename) throws IOException
    {
        return selectquery(tablename)+" ID='"+ExtraProcess.currentUserDetails.getId()+"'";
    }
    public String getIdByMailQuery(String tablename,String mail)
    {
        return "select ID from "+tablename+" where Email='"+mail+"'";
    }
    public String getMailByIdQuery(String tablename,String ID)
    {
        return "select Email from "+tablename+" where ID='"+ID+"'";
    }
    public String insertQuery(String tablename,String name,String dob,String email,String password,String Phonenumber)
    {
    return "insert into "+tablename+" (name,DOB,Email,password,phonenumber) values('"+name+"','"+dob+"','"+email+"','"+password+"','"+Phonenumber+"')";
    }
    public String uniqueKeySetter()
    {
        return "UPDATE userprofile  SET ID=concat('Usr',cast(auto as char(200))) WHERE auto=last_insert_id();";
    }
    public String uniqueKeyGetter()
    {
        return "select last_insert_id();";
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
    public String availableFlightquery(String flight,String flightdetails,String depcity,String arrcity,String depdate,int NofSeats,String flightClass)
    {   
        String alternateDeptimeStr=" and Departuretime like '%"+depdate+"%'";
        if(depdate.length()==0)
        {
            alternateDeptimeStr="";
        }
         return "select "+flight+".FlightNumber, "+flight+".Flight, "+flight+".Departurecity,"+flight+".Arrivalcity,"+flightdetails+".Departuretime,"+flightdetails+".Arrivaltime,"+flightdetails+".flightclass,"+flightdetails+".noofseats,"+flightdetails+".currentSeatsAvailable,"+flightdetails+".Costperseat from "+flight+" INNER JOIN "+flightdetails +" ON "+flight+".FlightNumber="+flightdetails+".FlightNumber where "+flight+".Departurecity='"+depcity+"' and "+flight+".Arrivalcity='"+arrcity+"' and "+flightdetails+".currentSeatsAvailable>="+String.valueOf(NofSeats)+alternateDeptimeStr+" and "+flightdetails+".flightclass='"+flightClass+"'";
    }
    public String updateFlightquery(String tablename,int noofseats,String flightId,String sign)
    {
        return "Update "+tablename+" SET currentSeatsAvailable=currentSeatsAvailable"+sign+String.valueOf(noofseats)+" Where FlightNumber='"+flightId+"'";
    }
    public String insertBookedTicketsQuery(String tablename,String userId,String username,int userage,String usergender,String flightId,String  ticketId,String orderId,String flight,String Deptime,String Arrtime,String bookedOn,String cancelledOn,String isCancelled,float amount,String flightClass)
    {   
        return "insert into "+tablename+" values('"+userId+"','"+username+"','"+String.valueOf(userage)+"','"+usergender+"','"+flightId+"','"+ticketId+"','"+orderId+"','"+flight+"','"+Deptime+"','"+Arrtime+"','"+bookedOn+"','"+cancelledOn+"','"+isCancelled+"','"+flightClass+"',"+String.valueOf(amount)+")";
    }
    public String availableSummaryQuery(String tablename,String userId,String status)
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
        return "select Departurecity,Arrivalcity from "+tablename+" where FlightNumber='"+flightId+"'";
    }
    public String noOfSeatsQuery(String tablename,String bookingId,String cancel)
    {
        return "select count(*) from "+tablename+" where BookingId='"+bookingId+"' and IsCancelled='"+cancel+"' group by BookingId";
    }
    public String getUserInfo(String tablename,String id)
    {
        return "select * from "+tablename+" where ID='"+id+"'";
    }
    public String distinctColumn(String tablename,String columnName)
    {
        return "select distinct "+columnName+" from "+tablename;
    }
    
    //search functions
    public String searchByflight(String flight,String flightdetails,String flightname)
    {
        return "select "+flight+".FlightNumber, "+flight+".Flight, "+flight+".Departurecity,"+flight+".Arrivalcity,"+flightdetails+".Departuretime,"+flightdetails+".Arrivaltime,"+flightdetails+".flightclass,"+flightdetails+".noofseats,"+flightdetails+".currentSeatsAvailable,"+flightdetails+".Costperseat from "+flight+" INNER JOIN "+flightdetails +" ON "+flight+".FlightNumber="+flightdetails+".FlightNumber where "+flight+".Flight='"+flightname+"'";
    }
    public String searchBycity(String flight,String flightdetails,String cityname)
    {   
        return "select "+flight+".FlightNumber, "+flight+".Flight, "+flight+".Departurecity,"+flight+".Arrivalcity,"+flightdetails+".Departuretime,"+flightdetails+".Arrivaltime,"+flightdetails+".flightclass,"+flightdetails+".noofseats,"+flightdetails+".currentSeatsAvailable,"+flightdetails+".Costperseat from "+flight+" INNER JOIN "+flightdetails +" ON "+flight+".FlightNumber="+flightdetails+".FlightNumber where "+flight+".Departurecity='"+cityname+"' or "+flight+".Arrivalcity='"+cityname+"'";
       
    }
    public String searchByDate(String flight,String flightdetails,String date)
    {
        return "select "+flight+".FlightNumber, "+flight+".Flight, "+flight+".Departurecity,"+flight+".Arrivalcity,"+flightdetails+".Departuretime,"+flightdetails+".Arrivaltime,"+flightdetails+".flightclass,"+flightdetails+".noofseats,"+flightdetails+".currentSeatsAvailable,"+flightdetails+".Costperseat from "+flight+" INNER JOIN "+flightdetails +" ON "+flight+".FlightNumber="+flightdetails+".FlightNumber where "+flightdetails+".Departuretime like '%"+date+"%' or "+flightdetails+".Arrivaltime='%"+date+"%'"; 
       
    }



  /*  public String CancelingticketQuery(String tablename,String flightId)
    {
       return "delete from "+tablename+" where Flightid='"+flightId+"'";
    } */
    
}
