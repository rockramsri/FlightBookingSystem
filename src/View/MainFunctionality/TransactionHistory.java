package View.MainFunctionality;

import java.util.*;
import Database.*;
import Database.DBTableClass.*;
import Utils.*;

public class TransactionHistory {
  FlightUtils flightUtils;
  DatabaseHandler databaseHandler;

  public TransactionHistory() {
    flightUtils = FlightUtils.getInstance();
    databaseHandler = DatabaseHandler.getInstance();
  }

  // Used to print booked ticket history
  public void bookedticketHistory() {

    List<BookedTickets> bookedTicketsList = databaseHandler.getBookedList(Resource.currentUserDetails.getId(), "no");
    CommandLineTable bookTicketTable = new CommandLineTable();
    int optionSelection = 0;
    List<String> distinctOrder = new ArrayList<String>();
    bookTicketTable.setHeaders("  CODE  ", "  FlightId  ", "   BookingId   ", "   AirLines   ", "   DepartureTime   ",
        "   ArrivalTime  ", "   DepartureCity      ", "      ArrivalCity    ", "  BookedOn  ", " FlightClass ",
        "  Seats Booked ");

    for (BookedTickets bTickets : bookedTicketsList) {
      if (!distinctOrder.contains(bTickets.getBookingId())) {
        distinctOrder.add(bTickets.getBookingId());
        optionSelection += 1;
        HashMap<String, String> depArrFlightList = databaseHandler
            .depArrivalFlightNameGetter(bTickets.getFlightNumber());
        String noofseats = databaseHandler.noOfSeats(bTickets.getBookingId(), "no");
        bookTicketTable.addRow(String.valueOf(optionSelection), bTickets.getFlightNumber(), bTickets.getBookingId(),
            depArrFlightList.get("flightname"), depArrFlightList.get("departuretime"),
            depArrFlightList.get("arrivaltime"), depArrFlightList.get("departurecity"),
            depArrFlightList.get("arrivalcity"), bTickets.getBookedOn(), bTickets.getFlightClass(), noofseats);
      }

    }
    if (optionSelection == 0) {
      System.out.println("********Empty Booking history *******");
    } else {
      bookTicketTable.print();
    }

  }

  // Used to print Cancelled ticket history
  public void ticketCancellingHistory() {

    List<BookedTickets> bookedTicketsList = databaseHandler.getBookedList(Resource.currentUserDetails.getId(), "yes");

    CommandLineTable bookTicketTable = new CommandLineTable();

    int optionSelection = 0;

    List<String> distinctBookings = new ArrayList<String>();

    bookTicketTable.setHeaders("  CODE  ", "  FlightId  ", "   BookingId   ", "   AirLines   ", "   DepartureTime   ",
        "   ArrivalTime  ", "   DepartureCity      ", "      ArrivalCity    ", " BookedOn ", "  CancelledOn  ",
        " FlightClass ", "  Seats Cancelled ");
    System.out.println(bookedTicketsList.size());
    for (BookedTickets bTickets : bookedTicketsList) {
      if (!distinctBookings.contains(bTickets.getBookingId())) {
        distinctBookings.add(bTickets.getBookingId());
        optionSelection += 1;
        HashMap<String, String> depArrFlightList = databaseHandler
            .depArrivalFlightNameGetter(bTickets.getFlightNumber());
        String noofseats = databaseHandler.noOfSeats(bTickets.getBookingId(), "yes");
        bookTicketTable.addRow(String.valueOf(optionSelection), bTickets.getFlightNumber(), bTickets.getBookingId(),
            depArrFlightList.get("flightname"), depArrFlightList.get("departuretime"),
            depArrFlightList.get("arrivaltime"), depArrFlightList.get("departurecity"),
            depArrFlightList.get("arrivalcity"), bTickets.getBookedOn(), bTickets.getCancelledOn(),
            bTickets.getFlightClass(), noofseats);
      }

    }
    if (optionSelection == 0) {
      System.out.println("********Empty Cancelation history *******");
    } else {
      bookTicketTable.print();
    }

  }

}
