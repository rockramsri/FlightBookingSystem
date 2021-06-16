import java.io.*;
import java.sql.*;
import java.util.*;

public class RefundCancel {
        private String UserId;
    
        private Scanner Summaryscanner;
      
        private int noOfseats=0;
      // private String flightId="";
      
       private String Departurecity;
   private String Arrivalcity;
   private String flightClass;
 
   private List<String> ticketid=new ArrayList<String>();
    RefundCancel(){
       Summaryscanner=new Scanner(System.in);
    }
    int updateAirlines(String flightId) throws ClassNotFoundException, SQLException
    {
       // updStatement=DatabaseLoader.databaseloadcaller();
        return new DatabaseHandler().airlinesUpdater(flightId, noOfseats, "+");
       //return  updStatement.executeUpdate(new SqlQuery().UpdateFlightquery(Resource.AIRLINE_TICKET_TABLE_NAME, noOfseats, flightId,"+"));
       

        
    }




    TicketInfo listofbookings() throws IOException, ClassNotFoundException, SQLException
    {
       
          BufferedReader reader = new BufferedReader(new FileReader(Resource.USERLOG_LOCATION));
          String line = null;
          UserId="";
        while ((line = reader.readLine()) != null) 
        {
	       UserId+=line;
        }
        if(UserId.substring(0, 4).equals("null"))
            UserId=UserId.substring(4);
        reader.close();
        
  
     
   // System.out.println(Useremail);
         

   //  System.out.println(new SqlQuery().AvailableSummaryQuery("Bookedtickets",UserId));
    //summaryResultSet=summaryReqStatment.executeQuery(new SqlQuery().AvailableSummaryQuery(Resource.BOOKED_TICKET_TABLE_NAME, UserId,"no"));
    
     
     int optionSelection=0;
   
    
     List<String> distinctOrder=new ArrayList<String>();
     CommandLineTable OrderId=new CommandLineTable();
     OrderId.setShowVerticalLines(true);
     CommandLineTable listOrderId=new CommandLineTable();
     listOrderId.setShowVerticalLines(true);

     OrderId.setHeaders("  CODE  ","  FlightId  ","   BookingId   ","   AirLines   ","   DepartureTime   ","   ArrivalTime  ","   DepartureCity      ","      ArrivalCity    ");
   
     for(BookedTickets bookedTickets:new DatabaseHandler().getBookedList(UserId,"no"))
     {  
        if(!distinctOrder.contains(bookedTickets.getBookingId()))
        {
          distinctOrder.add(bookedTickets.getBookingId());
          optionSelection+=1;
          List<String> deparrtimeclasslist=new DatabaseHandler().depArrivalFlightClass(bookedTickets.getFlightId());
          //airlineDepArrFliset=airStatement.executeQuery(new SqlQuery().depArrivalFlightClassquery(Resource.AIRLINE_TICKET_TABLE_NAME, summaryResultSet.getString(5)));
         // airlineDepArrFliset.next();
        //  System.out.printf("%15s%15s%15s%15s%15s%15s%15s%15s%n",String.valueOf(optionSelection),summaryResultSet.getString(5),summaryResultSet.getString(7),summaryResultSet.getString(8),summaryResultSet.getString(9),summaryResultSet.getString(10),airlineDepArrFliset.getString(1),airlineDepArrFliset.getString(2));
          OrderId.addRow(String.valueOf(optionSelection),bookedTickets.getFlightId(),bookedTickets.getBookingId(),bookedTickets.getFlight(),bookedTickets.getDepartureTime(),bookedTickets.getArrivalTime(),deparrtimeclasslist.get(0),deparrtimeclasslist.get(1));
        }
         
     }
     if(optionSelection==0)
     {
       //ExtraProcess.clearscreen();
      System.out.println("********Empty Booking history *******");
     }
     else{
       OrderId.print();
     System.out.println("Enter the Corresponding CODE for Cancelation:");
     int optionSelected=Integer.parseInt(Summaryscanner.nextLine());

     
    // ResultSet ticketResultSet=ticketReqsStatement.executeQuery(new SqlQuery().ticketlistquery(Resource.BOOKED_TICKET_TABLE_NAME, distinctOrder.get(optionSelected-1)));
     optionSelection=0;
     listOrderId.setHeaders("Code","Username","UserAge","Usergender","FlightId ","TicketNumber","BookingId","AirLines "," DepartureCity "," ArrivalCity ","  DepartureTime ","  ArrivalTime "," FlightClass "," Amount");
    // System.out.println("    Username   |   UserAge  |   Usergender    |    FlightId   |   TicketNumber  |   BookingId  |   AirLines  |   DepartureCity  |       ArrivalCity      |      DepartureTime      |      ArrivalTime      |      FlightClass      | Amount");
    List<BookedTickets> bookedTicketsList=new DatabaseHandler().getBookedIdsBookingList( distinctOrder.get(optionSelected-1));
    for(BookedTickets bTickets:bookedTicketsList)
    { 
      optionSelection+=1;
      
      
      List<String> deparrtimeclasslist=new DatabaseHandler().depArrivalFlightClass(bTickets.getFlightId());
      
      Departurecity=deparrtimeclasslist.get(0);
      Arrivalcity=deparrtimeclasslist.get(1);
      flightClass=deparrtimeclasslist.get(2);
      listOrderId.addRow(String.valueOf(optionSelection),bTickets.getUserlist().getUname(),String.valueOf(bTickets.getUserlist().getUage()),ExtraProcess.genderCalculate(bTickets.getUserlist().getUgender()),bTickets.getFlightId(),bTickets.getTicketId(),bTickets.getBookingId(),bTickets.getFlight(),deparrtimeclasslist.get(0),deparrtimeclasslist.get(1),bTickets.getDepartureTime(),bTickets.getArrivalTime(),deparrtimeclasslist.get(2),"Rs."+String.valueOf(bTickets.getAmount()));
      //System.out.printf("%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%n",String.valueOf(optionSelection)+".",ticketResultSet.getString(2),ticketResultSet.getString(3),ticketResultSet.getString(4),flightId,ticketResultSet.getString(6),ticketResultSet.getString(7),ticketResultSet.getString(8),Departurecity,Arrivalcity," "+ticketResultSet.getString(9)," "+ticketResultSet.getString(10),flightClass,"Rs."+String.valueOf(ticketResultSet.getFloat(14)));
    }


    listOrderId.print();

    System.out.println("1.Do you want to Cancel all \n2.Do you want to Cancel specific:");
    int tempchoice=Integer.parseInt(Summaryscanner.nextLine());

    List<String> options = new ArrayList<String>();
    if(tempchoice==1)
    { 
         
      for(int i=1;i<=optionSelection;i++)
               options.add(String.valueOf(i));
               
    }
    else
    {
    System.out.println("Enter the Corresponding CODE Separated by space for Cancelation:");
    options=Arrays.asList(Summaryscanner.nextLine().split(" "));
    }
    
    for (String tickets : options)
    { 
     // System.out.println(tickets);
     // ticketResultSet.absolute(Integer.parseInt(tickets));
      BookedTickets bTickets=bookedTicketsList.get(Integer.parseInt(tickets)-1);
      SeatsAllocate.seats.get(bookedTicketsList.get(0).getFlightId()).add(Integer.parseInt(bTickets.getTicketId().substring(1)));
      ticketid.add(bTickets.getTicketId());
      //System.out.println(new SqlQuery().ticketCancelingQuery("Bookedtickets", distinctOrder.get(optionSelected-1),ticketResultSet.getString(6).substring(1)));


     // summaryReqStatment.executeUpdate(new SqlQuery().ticketCancelingQuery(Resource.BOOKED_TICKET_TABLE_NAME, distinctOrder.get(optionSelected-1),ticketResultSet.getString(6).substring(1)));
       new DatabaseHandler().ticketCanceling(distinctOrder.get(optionSelected-1), bTickets.getTicketId());
    }
    SeatsAllocate.seatStorer(SeatsAllocate.seats);
    Collections.sort(SeatsAllocate.seats.get(bookedTicketsList.get(0).getFlightId()));
    noOfseats=options.size();






     
      
      TicketInfo ticketInfo=new TicketInfo(Departurecity, Arrivalcity, noOfseats, flightClass, new DatabaseHandler().getMailbyId(UserId), bookedTicketsList.get(0).getDepartureTime(), bookedTicketsList.get(0).getArrivalTime() ,  bookedTicketsList.get(0).getFlight(), distinctOrder.get(optionSelected-1), ticketid, null);
     //int Iscancelled=summaryReqStatment.executeUpdate(new SqlQuery().CancelingticketQuery("Bookedtickets",summaryResultSet.getString(2)));
      
     int isrestored=updateAirlines(bookedTicketsList.get(0).getFlightId());
     if(isrestored==0)
     {

     System.out.println("*********Your Cancelation coundnt get processed.Please try again later********");
     
     }
     else{
        System.out.println("*********Your Cancelation is Successfully processed********");
        System.out.println("SENDING YOUR MAIL PLEASE WAIT..............");
        return ticketInfo;
     }
     


          }

    
     return null;
    

    

    }
    
}
