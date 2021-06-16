import java.io.*;
import java.util.*;

/**
 * This class is used to process and store the seats alloction for different flights
 */
public  class SeatsAllocate {

    
    
 public  static  Map<String, List<Integer>> seats = new HashMap<String, List<Integer>>();
   public static void iFnotexist() throws IOException, ClassNotFoundException
    {
        boolean exceptionReadingStream=false;
        String checkingstring="",temp;
        try
        {
        BufferedReader checkingStream = new BufferedReader(new FileReader(new File(Resource.SEATALLOCTION_LOCATION)));
       
        while ((temp = checkingStream.readLine()) != null)
        checkingstring+=temp;
       
        }
        catch(Exception e)
        {
            exceptionReadingStream=true;
        }
        if(exceptionReadingStream || checkingstring.length()==0  )
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
            seatStorer(seats);
        }
        else
        {  
           
            FileInputStream fin=new FileInputStream(Resource.SEATALLOCTION_LOCATION);   
            ObjectInputStream objin=new ObjectInputStream(fin);
           //@SuppressWarnings("unchecked")
         
           seats=(Map<String, List<Integer>>) objin.readObject()  ;
            
            fin.close();
            objin.close();
            
            
            
        }

    }
    public static void seatStorer(Map<String, List<Integer>> storeSeats) throws IOException
    {
       
        FileOutputStream fout=new FileOutputStream(Resource.SEATALLOCTION_LOCATION); 
        ObjectOutputStream obj=new ObjectOutputStream(fout);
           ExtraProcess.clearTheuserlog("Seatallocation");
       
       
        obj.writeObject(storeSeats);
        obj.close();
        fout.close();
    }


    public Map<String, List<Integer>> getSeats() {
        return seats;
    }
    public void setSeats(Map<String, List<Integer>> seats) {
        SeatsAllocate.seats = seats;
    }


}
