package MainFunctionality;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import Database.*;
import DBTableClass.*;
import ExtraResources.*;


public class TransactionHistory {
   //Used to print booked ticket history
    public void bookedticketHistory() throws ClassNotFoundException, SQLException, IOException
    {   
      
        List<BookedTickets> bookedTicketsList=new DatabaseHandler().getBookedList( ExtraProcess.currentUserDetails.getId(), "no");
          CommandLineTable bookTickeTable =new CommandLineTable();
          int optionSelection=0;        
          List<String> distinctOrder=new ArrayList<String>();        
          bookTickeTable.setShowVerticalLines(true);
          bookTickeTable.setHeaders("  CODE  ","  FlightId  ","   BookingId   ","   AirLines   ","   DepartureTime   ","   ArrivalTime  ","   DepartureCity      ","      ArrivalCity    ","  BookedOn  ","  Seats Booked ");
        
          for(BookedTickets bTickets:bookedTicketsList)
          {  
             if(!distinctOrder.contains(bTickets.getBookingId()))
             {
               distinctOrder.add(bTickets.getBookingId());
               optionSelection+=1;
               List<String> depArrTimeClass=new DatabaseHandler().depArrivalFlightClass(bTickets.getFlightId());
               String noofseats=new DatabaseHandler().noOfSeats(bTickets.getBookingId(),"no");
               bookTickeTable.addRow(String.valueOf(optionSelection),bTickets.getFlightId(),bTickets.getBookingId(),bTickets.getFlight(),bTickets.getDepartureTime(),bTickets.getArrivalTime(),depArrTimeClass.get(0),depArrTimeClass.get(1),bTickets.getBookedOn(),noofseats);
             }
              
          }
          if(optionSelection==0)
          {
            //ExtraProcess.clearscreen();
           System.out.println("********Empty Booking history *******");
          }
          else
          {
              bookTickeTable.print();
          }
  
  
    }

  //Used to print Cancelled ticket history
    public void ticketCancellingHistory() throws Exception
    {

      List<BookedTickets> bookedTicketsList=new DatabaseHandler().getBookedList( ExtraProcess.currentUserDetails.getId(), "yes");

         CommandLineTable bookTickeTable =new CommandLineTable();


         int optionSelection=0;
  
       
         List<String> distinctOrder=new ArrayList<String>();
        
         bookTickeTable.setShowVerticalLines(true);
         
    
         bookTickeTable.setHeaders("  CODE  ","  FlightId  ","   BookingId   ","   AirLines   ","   DepartureTime   ","   ArrivalTime  ","   DepartureCity      ","      ArrivalCity    "," BookedOn ","  CancelledOn  ","  Seats Cancelled ");
       
         for(BookedTickets bTickets:bookedTicketsList)
         {  
            if(!distinctOrder.contains(bTickets.getBookingId()))
            {
              distinctOrder.add(bTickets.getBookingId());
              optionSelection+=1;
              List<String> depArrTimeClass=new DatabaseHandler().depArrivalFlightClass(bTickets.getFlightId());
              String noofseats=new DatabaseHandler().noOfSeats(bTickets.getBookingId(),"yes");
              bookTickeTable.addRow(String.valueOf(optionSelection),bTickets.getFlightId(),bTickets.getBookingId(),bTickets.getFlight(),bTickets.getDepartureTime(),bTickets.getArrivalTime(),depArrTimeClass.get(0),depArrTimeClass.get(1),bTickets.getBookedOn(),bTickets.getCancelledOn(),noofseats);
            }
             
         }
         if(optionSelection==0)
         {
           //ExtraProcess.clearscreen();
          System.out.println("********Empty Cancelation history *******");
         }
         else
         {
             bookTickeTable.print();
         }
 
        }
    
}
