package MainFunctionality;
import java.io.*;
import java.sql.*;
import java.util.*;
import Ticket.*;
import Database.*;
import ExtraResources.*;
import DBTableClass.*;
public class RefundCancel {
        
    
        private Scanner Summaryscanner;
      
        private int noOfseats=0;
      // private String flightId="";
      
       private String Departurecity;
   private String Arrivalcity;
   private String flightClass;
 
   private List<String> ticketid=new ArrayList<String>();
    public RefundCancel(){
       Summaryscanner=new Scanner(System.in);
    }
    //Used to update the number of seats in the Airlines
    int updateAirlines(String flightId) throws ClassNotFoundException, SQLException
    {
      
        return new DatabaseHandler().airlinesUpdater(flightId, noOfseats, "+");
        
    }



     //Used to list and cancel the booked tickets of the user
    public TicketInfo listOfBookings() throws IOException, ClassNotFoundException, SQLException
    {
       
     int optionSelection=0;
   
    
     List<String> distinctOrder=new ArrayList<String>();
     CommandLineTable OrderId=new CommandLineTable();
     OrderId.setShowVerticalLines(true);
     CommandLineTable listOrderId=new CommandLineTable();
     listOrderId.setShowVerticalLines(true);

     OrderId.setHeaders("  CODE  ","  FlightId  ","   BookingId   ","   AirLines   ","   DepartureTime   ","   ArrivalTime  ","   DepartureCity      ","      ArrivalCity    ");
   
     for(BookedTickets bookedTickets:new DatabaseHandler().getBookedList(ExtraProcess.currentUserDetails.getId(),"no"))
     {  
        if(!distinctOrder.contains(bookedTickets.getBookingId()))
        {
          distinctOrder.add(bookedTickets.getBookingId());
          optionSelection+=1;
          List<String> deparrtimeclasslist=new DatabaseHandler().depArrivalFlightClass(bookedTickets.getFlightId());
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
     int optionSelected;
     while(true)
     {
       try{
        optionSelected=Integer.parseInt(Summaryscanner.nextLine());
         break;
       }
       catch(NumberFormatException e )
       {
         System.out.println("*Entered number is not valid please enter again*");
       }
     
     }

     
    
     optionSelection=0;
     listOrderId.setHeaders("Code","Username","UserAge","Usergender","FlightId ","TicketNumber","BookingId","AirLines "," DepartureCity "," ArrivalCity ","  DepartureTime ","  ArrivalTime "," FlightClass "," Amount");
    List<BookedTickets> bookedTicketsList=new DatabaseHandler().getBookedIdsBookingList( distinctOrder.get(optionSelected-1));
    for(BookedTickets bTickets:bookedTicketsList)
    { 
      optionSelection+=1;
      
      
      List<String> deparrtimeclasslist=new DatabaseHandler().depArrivalFlightClass(bTickets.getFlightId());
      
      Departurecity=deparrtimeclasslist.get(0);
      Arrivalcity=deparrtimeclasslist.get(1);
      flightClass=deparrtimeclasslist.get(2);
      listOrderId.addRow(String.valueOf(optionSelection),bTickets.getUserlist().getUname(),String.valueOf(bTickets.getUserlist().getUage()),ExtraProcess.genderCalculate(bTickets.getUserlist().getUgender()),bTickets.getFlightId(),bTickets.getTicketId(),bTickets.getBookingId(),bTickets.getFlight(),deparrtimeclasslist.get(0),deparrtimeclasslist.get(1),bTickets.getDepartureTime(),bTickets.getArrivalTime(),deparrtimeclasslist.get(2),"Rs."+String.valueOf(bTickets.getAmount()));
      
    }


    listOrderId.print();

    System.out.println("1.Do you want to Cancel all \n2.Do you want to Cancel specific:");
    int tempchoice;
    while(true)
    {
      try{
        tempchoice=Integer.parseInt(Summaryscanner.nextLine());
        break;
      }
      catch(NumberFormatException e )
      {
        System.out.println("*Entered number is not valid please enter again*");
      }
    
    }

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
      BookedTickets bTickets=bookedTicketsList.get(Integer.parseInt(tickets)-1);
      SeatsAllocate.seats.get(bookedTicketsList.get(0).getFlightId()).add(Integer.parseInt(bTickets.getTicketId().substring(1)));
      ticketid.add(bTickets.getTicketId());
       new DatabaseHandler().ticketCanceling(distinctOrder.get(optionSelected-1), bTickets.getTicketId());
    }
    //SeatsAllocate.seatStorer(SeatsAllocate.seats);
    //Collections.sort(SeatsAllocate.seats.get(bookedTicketsList.get(0).getFlightId()));
    noOfseats=options.size();






     
      
      TicketInfo ticketInfo=new TicketInfo(Departurecity, Arrivalcity, noOfseats, flightClass, ExtraProcess.currentUserDetails.getEmail(), bookedTicketsList.get(0).getDepartureTime(), bookedTicketsList.get(0).getArrivalTime() ,  bookedTicketsList.get(0).getFlight(), distinctOrder.get(optionSelected-1), ticketid, null);
    
      
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
