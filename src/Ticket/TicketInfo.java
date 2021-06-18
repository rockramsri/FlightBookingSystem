package Ticket;
import java.util.*;

/**
 * This class is POJO class for ticket Information
 */
public class TicketInfo {
    private String Departurecity;
    private String Arrivalcity;
    private int NofSeats;
     private List<UserDetails> ticketlist;
     private String flightClass;
     private String Useremail;
     private String orderId;
     private List<String> ticketIds;
     private String DepTime;
     private String ArrTime;
     private String FlightName;
     public TicketInfo(String Departurecity,String Arrivalcity,int NofSeats,String flightClass,String Useremail,String DepTime,String ArrTime,String FlightName,String orderId,List<String> ticketid,List<UserDetails> userdetails)
     {
           this.Departurecity=Departurecity;
            this.Arrivalcity=Arrivalcity;
             this.NofSeats=NofSeats;
             this.flightClass=flightClass;
              this.Useremail=Useremail;
              this.ticketlist=userdetails;
              this.orderId=orderId;
              this.ticketIds=ticketid;
              this.DepTime=DepTime;
             this.ArrTime=ArrTime;
             this.FlightName=FlightName;
     }

      //Getter setter for Ticket Information 
     public String getOrderId() {
         return orderId;
     }
     public List<String> getTicketIds() {
         return ticketIds;
     }
     public List<UserDetails> getTicketlist() {
         return ticketlist;
     }
     public void setOrderId(String orderId) {
         this.orderId = orderId;
     }
     public void setTicketIds(List<String> ticketIds) {
         this.ticketIds = ticketIds;
     }
     public void setTicketlist(List<UserDetails> ticketlist) {
         this.ticketlist = ticketlist;
     }

    
     public String getArrTime() {
         return ArrTime;
     }
    public String getArrivalcity() {
        return Arrivalcity;
    }

    public String getDepTime() {
        return DepTime;
    }
    public String getDeparturecity() {
        return Departurecity;
    }
    public String getFlightClass() {
        return flightClass;
    }
    public String getFlightName() {
        return FlightName;
    }
    public int getNofSeats() {
        return NofSeats;
    }
    public String getUseremail() {
        return Useremail;
    }
    public void setArrTime(String arrTime) {
        ArrTime = arrTime;
    }
    public void setArrivalcity(String arrivalcity) {
        Arrivalcity = arrivalcity;
    }
    public void setDepTime(String depTime) {
        DepTime = depTime;
    }
    public void setDeparturecity(String departurecity) {
        Departurecity = departurecity;
    }
    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }
    public void setFlightName(String flightName) {
        FlightName = flightName;
    }
    public void setNofSeats(int nofSeats) {
        NofSeats = nofSeats;
    }
    public void setUseremail(String useremail) {
        Useremail = useremail;
    }

}
