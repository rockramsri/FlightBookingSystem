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
  public void bookedTicketHistory(ProfileDetails currentUserDetails) {

    List<BookedTickets> bookedTicketsList = databaseHandler.getBookedList(currentUserDetails.getId(), "no");
    CommandLineTable bookTicketTable = new CommandLineTable();
    int optionSelection = 0;
    List<String> distinctOrder = new ArrayList<String>();
    bookTicketTable.setHeaders(Resource.CODE_HEADER, Resource.FLIGHT_NUMBER_HEADER, Resource.BOOKING_ID_HEADER,
        Resource.FLIGHT_NAME_HEADER, Resource.DEPARTURETIME_HEADER, Resource.ARRIVALTIME_HEADER,
        Resource.DEPARTURECITY_HEADER, Resource.ARRIVALCITY_HEADER, "  BookedOn  ", Resource.FLIGHTCLASS_HEADER,
        "  Seats Booked ");

    for (BookedTickets bTickets : bookedTicketsList) {
      if (!distinctOrder.contains(bTickets.getBookingId())) {
        distinctOrder.add(bTickets.getBookingId());
        optionSelection += 1;
        HashMap<String, String> depArrFlightList = databaseHandler
            .getDepartureArrivalFlightName(bTickets.getFlightNumber());
        String noofseats = databaseHandler.noOfSeats(bTickets.getBookingId(), "no");
        bookTicketTable.addRow(String.valueOf(optionSelection), bTickets.getFlightNumber(), bTickets.getBookingId(),
            depArrFlightList.get(Resource.FLIGHT_NAME_COLUMN), depArrFlightList.get(Resource.DEPARTURETIME_COLUMN),
            depArrFlightList.get(Resource.ARRIVALTIME_COLUMN), depArrFlightList.get(Resource.DEPARTURECITY_COLUMN),
            depArrFlightList.get(Resource.ARRIVALCITY_COLUMN), bTickets.getBookedOn(), bTickets.getFlightClass(),
            noofseats);
      }

    }
    if (optionSelection == 0) {
      System.out.println("********Empty Booking history *******");
    } else {
      bookTicketTable.print();
    }

  }

  // Used to print Cancelled ticket history
  public void ticketCancellingHistory(ProfileDetails currentUserDetails) {

    List<BookedTickets> bookedTicketsList = databaseHandler.getBookedList(currentUserDetails.getId(), "yes");

    CommandLineTable bookTicketTable = new CommandLineTable();

    int optionSelection = 0;

    List<String> distinctBookings = new ArrayList<String>();
    bookTicketTable.setHeaders(Resource.CODE_HEADER, Resource.FLIGHT_NUMBER_HEADER, Resource.BOOKING_ID_HEADER,
        Resource.FLIGHT_NAME_HEADER, Resource.DEPARTURETIME_HEADER, Resource.ARRIVALTIME_HEADER,
        Resource.DEPARTURECITY_HEADER, Resource.ARRIVALCITY_HEADER, "  BookedOn  ", "  CancelledOn ",
        Resource.FLIGHTCLASS_HEADER, "  Seats Booked ");
    System.out.println(bookedTicketsList.size());
    for (BookedTickets bookedTicket : bookedTicketsList) {
      if (!distinctBookings.contains(bookedTicket.getBookingId())) {
        distinctBookings.add(bookedTicket.getBookingId());
        optionSelection += 1;
        HashMap<String, String> departureArrivalFlightList = databaseHandler
            .getDepartureArrivalFlightName(bookedTicket.getFlightNumber());
        String noofseats = databaseHandler.noOfSeats(bookedTicket.getBookingId(), "yes");
        bookTicketTable.addRow(String.valueOf(optionSelection), bookedTicket.getFlightNumber(),
            bookedTicket.getBookingId(), departureArrivalFlightList.get(Resource.FLIGHT_NAME_COLUMN),
            departureArrivalFlightList.get(Resource.DEPARTURETIME_COLUMN),
            departureArrivalFlightList.get(Resource.ARRIVALTIME_COLUMN),
            departureArrivalFlightList.get(Resource.DEPARTURECITY_COLUMN),
            departureArrivalFlightList.get(Resource.ARRIVALCITY_COLUMN), bookedTicket.getBookedOn(),
            bookedTicket.getCancelledOn(), bookedTicket.getFlightClass(), noofseats);
      }

    }
    if (optionSelection == 0) {
      System.out.println("********Empty Cancelation history *******");
    } else {
      bookTicketTable.print();
    }

  }

}
