import java.io.*;
import java.sql.SQLException;
import java.util.*;

/**
 * This class is used to process and store the seats alloction for different flights
 */
public  class SeatsAllocate {

    
    
 public  static  Map<String, List<Integer>> seats = new HashMap<String, List<Integer>>();
 
 public static void seatUpdater() throws ClassNotFoundException, SQLException
 {
     if(SeatsAllocate.seats!=null)
     {
      List<BookedTickets> bookedTicketsList=new DatabaseHandler().fullBookedTicketStable();  
      for(BookedTickets bTickets:bookedTicketsList)
        {
            int element=Integer.parseInt(bTickets.getTicketId().substring(1));
            SeatsAllocate.seats.get(bTickets.getFlightId()).remove( SeatsAllocate.seats.get(bTickets.getFlightId()).indexOf(element) );
            if(bTickets.getIsCancelled().equals("yes"))
            { 
              
              SeatsAllocate.seats.get(bTickets.getFlightId()).add(element);

            }
            
        }
        //System.out.println(SeatsAllocate.seats);

     }
 }
    public static void initiator() throws IOException
        {
            Map<String,Integer> flightseats=Resource.FlightId();

            for (Map.Entry<String,Integer> entry : flightseats.entrySet())
                {
                    List<Integer> list=new ArrayList<Integer>(){{
                    for(int i=1;i<=entry.getValue();i++)
                    add(i);
                    }};
                    seats.put(entry.getKey(), list);
                }
            
        }


  
   /* public static void seatStorer(Map<String, List<Integer>> storeSeats) throws IOException
    {
       
        FileOutputStream fout=new FileOutputStream(Resource.SEATALLOCTION_LOCATION); 
        ObjectOutputStream obj=new ObjectOutputStream(fout);
           ExtraProcess.clearTheUserLog("Seatallocation");
       
       
        obj.writeObject(storeSeats);
        obj.close();
        fout.close();
    }*/


    public Map<String, List<Integer>> getSeats() {
        return seats;
    }
    public void setSeats(Map<String, List<Integer>> seats) {
        SeatsAllocate.seats = seats;
    }


}
