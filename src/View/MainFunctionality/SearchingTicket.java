package View.MainFunctionality;
import Utils.*;
import Database.*;
import Database.DBTableClass.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;



public class SearchingTicket {
  Scanner searchScanner;
  public SearchingTicket()
  {
     searchScanner=new Scanner(System.in);
  }

  public void finalize()
  {
    searchScanner.close();
  }
    
    //used to search ticket Availablity based on Every criteria given
    public void seachBySpecific() throws ClassNotFoundException, SQLException, ParseException
    {
   
      String Dateticket="";
      String Departurecity="";
      String Arrivalcity="";
      String flightClass="";
      int nofSeatsAdult=0;
      int nofSeatsInfant=0;
      int nofSeatsChild=0;




    // System.out.println("*********FOR THE  BELOW DETAILS IF YOU DONT KNOW THE INFORMATION TYPE AS (no)**************");
     ExtraProcess.clearScreen();
      
        ArrayList<String> cList=Resource.citiesList();
        int citynumber=0;         //Getting DepartureCity
        Date date1 = new Date();  
        while(true)
        {

       
        System.out.println("Enter your Departure Date(DD/MM/YYY):");
      Dateticket=searchScanner.nextLine();
      String pattern = "dd/MM/yyyy";
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
      
      Date date = simpleDateFormat.parse(Dateticket);
               if(date1.compareTo(date)!=1)
                 {
                  break;
                 }
                 System.out.println("The entered date is Expired");
                 
         }
         //here
         CommandLineTable cityTable=new CommandLineTable();
         cityTable.setShowVerticalLines(true);
         cityTable.setHeaders("CODE","CITY");
         for(String city:cList)
         {   citynumber=citynumber+1;
           cityTable.addRow(String.valueOf(citynumber),city);
            
         }
         cityTable.print();
        while(true)
        {
        System.out.println("Enter the  city of Departure (Corresponding Number):");
        int depcitychoice;
        while(true)
      {
        try{
          depcitychoice=Integer.parseInt(searchScanner.nextLine());
          break;
        }
        catch(NumberFormatException e )
        {
          System.out.println("*Entered number is not valid please enter again*");
        }
      
      }
   
          if(depcitychoice>=0 && depcitychoice<=cList.size())
          {
            Departurecity=cList.get(depcitychoice-1);
            cList.remove(depcitychoice-1); 
            break;
          }
        }
       // ExtraProcess.clearscreen();
        
        
        while(true)
        {
        System.out.println("Enter the  city for Arrival (Corresponding Number)::");
        int arrCityChoice;
        while(true)
      {
        try{
          arrCityChoice=Integer.parseInt(searchScanner.nextLine());
          break;
        }
        catch(NumberFormatException e )
        {
          System.out.println("*Entered number is not valid please enter again*");
        }
      
      }
        
        
          if(arrCityChoice>=0 && arrCityChoice<=cList.size())
          {
            Arrivalcity=cList.get(arrCityChoice-1);
            cList.remove(arrCityChoice-1); 
            break;
          }
        }
        
        
     //   ExtraProcess.clearscreen();
        System.out.println("Enter Number of seats for Adults(age above 15):");
        
        while(true)
      {
        try{
          nofSeatsAdult=Integer.parseInt(searchScanner.nextLine());
          break;
        }
        catch(NumberFormatException e )
        {
          System.out.println("*Entered number is not valid please enter again*");
        }
      
      }
        System.out.println("Enter Number of seats for child(age below 16):");
          
        while(true)
      {
        try{
          nofSeatsChild=Integer.parseInt(searchScanner.nextLine());
          break;
        }
        catch(NumberFormatException e )
        {
          System.out.println("*Entered number is not valid please enter again*");
        }
      
      }
       
        System.out.println("Enter Number of seats for infants(age above 3):");
       
        while(true)
        {
          try{
            nofSeatsInfant=Integer.parseInt(searchScanner.nextLine());
            break;
          }
          catch(NumberFormatException e )
          {
            System.out.println("*Entered number is not valid please enter again*");
          }
        
        }

      //  ExtraProcess.clearscreen();
        System.out.println("1.Economic:");
        System.out.println("2.Business:");
        int checker=Integer.parseInt(searchScanner.nextLine());
        
         while(true)
         {
           try{
            nofSeatsInfant=Integer.parseInt(searchScanner.nextLine());
             break;
           }
           catch(NumberFormatException e )
           {
             System.out.println("*Entered number is not valid please enter again*");
           }
         
         }
        System.out.println("\n");
        if(checker==1)
        {
          flightClass="Economic";
        }
        else
        {
          flightClass="Business";
        }
        List<Airlines> lAirlines=new DatabaseHandler().bookingList(Departurecity, Arrivalcity, Dateticket, nofSeatsAdult+nofSeatsChild+nofSeatsInfant, flightClass);
        int optionCount=0;
        System.out.println("1.Round Trip\n2.Single Trip");
        int tripOption=searchScanner.nextInt();
        
        List<Airlines> roundTripAirlines=new DatabaseHandler().bookingList(Arrivalcity, Departurecity,"", nofSeatsAdult+nofSeatsChild+nofSeatsInfant, flightClass);
        String patternwithTime = "dd/MM/yyyy hh:mm";
        SimpleDateFormat roundTripDateFormat = new SimpleDateFormat(patternwithTime);


        

         
        CommandLineTable tablebook=new CommandLineTable();
        tablebook.setShowVerticalLines(true);
        
        tablebook.setHeaders("  Code  ","   AirLines  ","   DepartureCity  ","       ArrivalCity      ","      DepartureTime      ","      ArrivalTime      ","      FlightClass      ","  Cost  ");
        
        for(Airlines aobject:lAirlines)
        {
         
          if(tripOption==1)
          {

            for(Airlines airline:roundTripAirlines)
            {
              Date roundtripDepDate=roundTripDateFormat.parse(airline.getDepartureTime());
              Date singletripArrDate=roundTripDateFormat.parse(aobject.getArrivalTime());
             if(singletripArrDate.compareTo(roundtripDepDate)!=1)
             {
              optionCount+=1;
              double totalCost=(nofSeatsAdult*airline.getCostPerSeat()+(nofSeatsChild+nofSeatsInfant)*airline.getCostPerSeat()*0.75)  + (nofSeatsAdult*aobject.getCostPerSeat()+(nofSeatsChild+nofSeatsInfant)*aobject.getCostPerSeat()*0.75);
            
              tablebook.addRow(String.valueOf(optionCount),aobject.getFlight(),aobject.getDepartureCity(),aobject.getArrivalCity(),aobject.getDepartureTime(),aobject.getArrivalTime(),aobject.getFlightClass(),"Rs."+String.valueOf(totalCost));
               break;
             }

            }
          
          }
          else
          {
            optionCount+=1;
            double totalCost=nofSeatsAdult*aobject.getCostPerSeat()+(nofSeatsChild+nofSeatsInfant)*aobject.getCostPerSeat()*0.75;

            tablebook.addRow(String.valueOf(optionCount),aobject.getFlight(),aobject.getDepartureCity(),aobject.getArrivalCity(),aobject.getDepartureTime(),aobject.getArrivalTime(),aobject.getFlightClass(),"Rs."+String.valueOf(totalCost));
          }
         
        }
        if(optionCount==0)
        {
          ExtraProcess.clearScreen();
         System.out.println("********No Flights is available Now*******");
        }
        else{
          tablebook.print();
        }
        


     


    }
  
     //used to search ticket Availablity based on particular Airlines
   public  void searchByFlight() throws ClassNotFoundException, SQLException 
    {
      
      int options=0;
      CommandLineTable flightTable=new CommandLineTable();
      flightTable.setShowVerticalLines(true);
     flightTable.setHeaders("CODE"," FLIGHT ");
      for(String flight:Resource.flightList())
      {   options=options+1;
        flightTable.addRow(String.valueOf(options),flight);
         
      }
      flightTable.print();
        System.out.println("Enter the Corresponding number of the flight to Search:");
        int optionSelected;
        while(true)
        {
          try{
            optionSelected=Integer.parseInt(searchScanner.nextLine());
            break;
          }
          catch(NumberFormatException e )
          {
            System.out.println("*Entered number is not valid please enter again*");
          }
        
        }
        
       
        CommandLineTable tablebook=new CommandLineTable();
        tablebook.setShowVerticalLines(true);
    
        tablebook.setHeaders("  Code ","      AirLines  "," DepartureCity ","  ArrivalCity  ","   DepartureTime     ","     ArrivalTime    ","  FlightClass  "," SeatsAvailabale  ","     CostperSeat");
        options=0;
       for(Airlines airlines:new DatabaseHandler().searchAirlinesbyflight(Resource.flightList().get(optionSelected-1)))
        {
          options+=1;
          tablebook.addRow(String.valueOf(options),airlines.getFlight(),airlines.getDepartureCity(),airlines.getArrivalCity(),airlines.getDepartureTime(),airlines.getArrivalTime(),airlines.getFlightClass(),String.valueOf(airlines.getCurrentSeatsAvailable()),"Rs."+String.valueOf(airlines.getCostPerSeat()));
          
        }
        tablebook.print();
        if(options==0)
        {
        ExtraProcess.clearScreen();
        System.out.println("No Flights available");
        }
        
    }

 //used to search ticket Availablity based on particular Date
    public  void searchByDate() throws ClassNotFoundException, SQLException, ParseException
    {
     
      
       String Dateticket="";
        Date date1 = new Date();  
        while(true)
        {

       
        System.out.println("Enter the  Date(DD/MM/YYY):");
       Dateticket=searchScanner.nextLine();
      String pattern = "dd/MM/yyyy";
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
      
      Date date = simpleDateFormat.parse(Dateticket);
               if(date1.compareTo(date)!=1)
                 {
                  break;
                 }
                 System.out.println("The entered date is Not available\n1.Do you want to go back\n2.Do you want to Enter Date again:");
                 int internalOption=Integer.parseInt(searchScanner.nextLine());
                 if(internalOption==1)
                 return;

                 
         }
         
    
      
      int options=0;
       CommandLineTable tablebook=new CommandLineTable();
       tablebook.setShowVerticalLines(true);
   
       tablebook.setHeaders("  Code ","      AirLines  "," DepartureCity ","  ArrivalCity  ","   DepartureTime     ","     ArrivalTime    ","  FlightClass  "," SeatsAvailabale  ","     CostperSeat");
     
      for(Airlines airlines:new DatabaseHandler().searchAirlinesbyDate(Dateticket))
       {
         options+=1;
         tablebook.addRow(String.valueOf(options),airlines.getFlight(),airlines.getDepartureCity(),airlines.getArrivalCity(),airlines.getDepartureTime(),airlines.getArrivalTime(),airlines.getFlightClass(),String.valueOf(airlines.getCurrentSeatsAvailable()),"Rs."+String.valueOf(airlines.getCostPerSeat()));
         
       }
       tablebook.print();
       if(options==0)
       {
       ExtraProcess.clearScreen();
       System.out.println("No Flights available");
       }
     
       
    }

    //used to search ticket Availablity based on particular City
    public  void searchByCity() throws ClassNotFoundException, SQLException
    {  
     
        int options=0;
        CommandLineTable cityTable=new CommandLineTable();
        cityTable.setShowVerticalLines(true);
        cityTable.setHeaders("CODE","CITY");
        for(String city:Resource.citiesList())
        {   options=options+1;
          cityTable.addRow(String.valueOf(options),city);
           
        }
        cityTable.print();
        System.out.println("Enter the Corresponding CODE of the city to Search:");
        int optionSelected;
        while(true)
        {
          try{
            optionSelected=Integer.parseInt(searchScanner.nextLine());
            break;
          }
          catch(NumberFormatException e )
          {
            System.out.println("*Entered number is not valid please enter again*");
          }
        
        }
      
        
        
      
        
        CommandLineTable tablebook=new CommandLineTable();
        tablebook.setShowVerticalLines(true);
    
        tablebook.setHeaders("  Code ","      AirLines  "," DepartureCity ","  ArrivalCity  ","   DepartureTime     ","     ArrivalTime    ","  FlightClass  "," SeatsAvailabale  ","     CostperSeat");
      options=0;
       for(Airlines airlines:new DatabaseHandler().searchAirlinesbycity(Resource.citiesList().get(optionSelected-1)))
        {
          options+=1;
          tablebook.addRow(String.valueOf(options),airlines.getFlight(),airlines.getDepartureCity(),airlines.getArrivalCity(),airlines.getDepartureTime(),airlines.getArrivalTime(),airlines.getFlightClass(),String.valueOf(airlines.getCurrentSeatsAvailable()),"Rs."+String.valueOf(airlines.getCostPerSeat()));
          
        }
        tablebook.print();
        if(options==0)
        {
        ExtraProcess.clearScreen();
        System.out.println("No Flights available");
        }
        
    }
    
    
    
}
