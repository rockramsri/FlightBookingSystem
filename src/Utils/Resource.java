package Utils;
import java.util.*;
/**
 * This class contains the Constant values for the application
 */
public class Resource {
    public static final String BOOKED_TICKET_TABLE_NAME="Bookedtickets";
    public  static final String AIRLINE_TICKET_TABLE_NAME="journeyinfo";
    public  static final String FLIGHT_TICKET_TABLE_NAME="bookingavailablity";
     public static final String User_INFO_TABLE_NAME="Userprofile";
    public static final String MYACCOUNT_ID="internzh2021@gmail.com";
    public static final String MYACCOUNT_PASSWORD="internzh";
  //  public static final String USERLOG_LOCATION="D:\\Userlog.txt";
   // public static final String SEATALLOCTION_LOCATION="D:\\Seatallocation.txt";
    
    static public ArrayList<String> tableCreationList()
    {
        ArrayList<String> tableCreationList = new ArrayList<String>() {
            {
                add("create table IF NOT EXISTS userprofile( auto int NOT NULL AUTO_INCREMENT unique,ID varchar(100) primary key default '',name varchar(100),DOB varchar(100),Email varchar(100),password varchar(100),phonenumber varchar(200) );");
                add("create table IF NOT EXISTS journeyinfo(FlightNumber    varchar(200) primary key,Flight   varchar(100),Departurecity    varchar(100),Arrivalcity    varchar(100) );");
                add("create table IF NOT EXISTS bookingavailablity(FlightNumber    varchar(200),Departuretime    varchar(100),Arrivaltime      varchar(100),flightclass       varchar(30),noofseats    int,currentSeatsAvailable   int,Costperseat    bigint   ); ");
                add("create table IF NOT EXISTS bookedtickets(ID  varchar(100),Username     varchar(100),Userage     varchar(100),Usergender      varchar(100),Flightid        varchar(100),TicketId     varchar(100),BookingId    varchar(100),Flight    varchar(100),DepartureTime   varchar(100),Arrivaltime    varchar(100),BookedOn        varchar(100),CancelledOn     varchar(100),IsCancelled     varchar(100) ,flightclass varchar(30),Amount   float);");
            }
        };
        return tableCreationList;
    }
    static public ArrayList<String> citiesList()
    {
        ArrayList<String> CityList = new ArrayList<String>() {
            {
                add("Raipur");
                add("Mysore");
                add("Tirupati");
                
                add("New Delhi");
                add("Mumbai");
                add("Bangalore");
                
                add("Chennai");
                add("Kolkata");
                add("Hyderabad");
                
                add("Cochin");
                add("Pune");
                add("Goa");
            }
        };
        return CityList;
    }
    static public ArrayList<String> flightList()
    {
        
        ArrayList<String> Flightlist = new ArrayList<String>() {
            {
                add("SpieceJet");
                add("Panam");
                add("Lufthansa");
                add("indigo");
            }
        };
        return Flightlist;
    }
    static public  Map<String,Integer> FlightId()
    {   int sizeArray[]={20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20};
        Map<String,Integer> seatSizeMap=new HashMap<String,Integer>();
                for(int i=1;i<=15;i++)
                {  
                    seatSizeMap.put("Fl00"+String.valueOf(i)+"-Economic",sizeArray[i-1]);
                    seatSizeMap.put("Fl00"+String.valueOf(i)+"-Business",sizeArray[i-1]);
                }
                
                
         
        return seatSizeMap;
    }
    
    
}
