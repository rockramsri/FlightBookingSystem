import java.util.*;
/**
 * This class contains the Constant values for the application
 */
public class Resource {
    static final String BOOKED_TICKET_TABLE_NAME="Bookedtickets";
    static final String AIRLINE_TICKET_TABLE_NAME="Airlines";
    static final String User_INFO_TABLE_NAME="Userprofile";
    static final String MYACCOUNT_ID="internzh2021@gmail.com";
    static final String MYACCOUNT_PASSWORD="internzh";
    static final String USERLOG_LOCATION="D:\\Userlog.txt";
    static final String SEATALLOCTION_LOCATION="D:\\Seatallocation.txt";
    

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
    {   int sizeArray[]={20,20,20,20,20,20,20,20,20,20,20,20,20,50,40,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30};
        Map<String,Integer> seatSizeMap=new HashMap<String,Integer>();
                for(int i=1;i<=30;i++)
                seatSizeMap.put("Fl00"+String.valueOf(i),sizeArray[i-1]);
                
         
        return seatSizeMap;
    }
    
    
}
