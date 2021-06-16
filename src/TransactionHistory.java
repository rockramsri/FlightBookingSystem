import java.io.IOException;
import java.sql.*;
import java.util.*;

public class TransactionHistory {

    public void BookedticketHistory() throws ClassNotFoundException, SQLException, IOException
    {   
      
        List<BookedTickets> bookedTicketsList=new DatabaseHandler().getBookedList( ExtraProcess.Useridgetter(), "no");
       // ResultSet historyset=historyStatment.executeQuery(new SqlQuery().AvailableSummaryQuery(Resource.BOOKED_TICKET_TABLE_NAME, ExtraProcess.Useridgetter(),"no"));
     

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
    public void TicketCancellingHistory() throws Exception
    {

      List<BookedTickets> bookedTicketsList=new DatabaseHandler().getBookedList( ExtraProcess.Useridgetter(), "yes");
      // ResultSet historyset=historyStatment.executeQuery(new SqlQuery().AvailableSummaryQuery(Resource.BOOKED_TICKET_TABLE_NAME, ExtraProcess.Useridgetter(),"no"));
    

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
