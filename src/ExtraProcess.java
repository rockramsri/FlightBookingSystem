import java.io.*;
import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter;  

public class ExtraProcess {
    public static String randomCodeForPassword="";
   
    //Used to Clear the screen in the Terminal
    public static void clearscreen()
    {
        System.out.print("\033[H\033[2J");  //ANSI Escape Code
        System.out.flush();  
    }
    public static String passwordHolder="";

    //Used to return Radom number based on the min and max range
    public static int sizeRandomizer(int min,int max)
    {
        
        int range = max - min + 1;
  
        // generate random numbers within 1 to 10
        
            return (int)(Math.random() * range) + min;
  
            // Output is different everytime this code is executed
            
        
    }

    //Used to get the usedId from the Userlog.txt file
    public static String userIdgetter() throws IOException
    {
     
        BufferedReader reader = new BufferedReader(new FileReader(Resource.USERLOG_LOCATION));
        String line = null;
       String UserId="";
      while ((line = reader.readLine()) != null) 
      {
         UserId+=line;
      }
      if(UserId.substring(0, 4).equals("null"))
          UserId=UserId.substring(4);
      reader.close();
      return UserId;

    }

    //Used to return the current time
    public static String dateTimeGetter()
    {
        LocalDateTime now = LocalDateTime.now();  
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");  
        String formatDateTime = now.format(format);  
        return formatDateTime;
    }

    //Used to clear the file
    public static void clearTheuserlog(String filename) throws IOException {
        File file = new File("D:\\"+filename+".txt"); 
        if(file.exists())
        file.delete();

        file.createNewFile();
    }
    //Used to convert gender based on number to String
    public static String genderCalculate(int num)
    {
        if(num==1)
        return "Male";
        else if(num==2)
        return "Female";
        else
        return "other";
    }
}
