package View.MainFunctionality;

import java.util.*;
import View.Ticket.*;
import Database.*;
import Utils.*;
import Database.DBTableClass.*;

public class RefundCancel {

  private FlightUtils flightUtils;
  private DatabaseHandler databaseHandler;

  private int noOfseats = 0;

  private String departureCity;
  private String arrivalCity;
  private String flightClass;
  private String departureTime;
  private String arrivalTime;
  private String flightName;

  private List<String> ticketid = new ArrayList<String>();

  public RefundCancel() {
    flightUtils = FlightUtils.getInstance();
    databaseHandler = DatabaseHandler.getInstance();
  }

  // Used to update the number of seats in the Airlines
  int updateAirlines(String flightId) {

    return databaseHandler.airlinesUpdater(flightId, noOfseats, "+");

  }

  // Used to list and cancel the booked tickets of the user
  public TicketInfo listOfBookings(ProfileDetails currentUserDetails) {

    int optionSelection = 0;

    List<String> distinctBookingId = new ArrayList<String>();
    CommandLineTable bookingHistoryTable = new CommandLineTable();
   

    bookingHistoryTable.setHeaders("  CODE  ", "  FlightId  ", "   BookingId   ", "   AirLines   ",
        "   DepartureTime   ", "   ArrivalTime  ", "   DepartureCity      ", "      ArrivalCity    ", "  Class  ");

    for (BookedTickets bookedTickets : databaseHandler.getBookedList(currentUserDetails.getId(), "no")) {
      if (!distinctBookingId.contains(bookedTickets.getBookingId())) {
        distinctBookingId.add(bookedTickets.getBookingId());
        optionSelection += 1;
        HashMap<String, String> deparrlist = databaseHandler
            .getDepartureArrivalFlightName(bookedTickets.getFlightNumber());
        bookingHistoryTable.addRow(String.valueOf(optionSelection), bookedTickets.getFlightNumber(),
            bookedTickets.getBookingId(), deparrlist.get(Resource.FLIGHT_NAME_COLUMN), deparrlist.get(Resource.DEPARTURETIME_COLUMN),
            deparrlist.get(Resource.ARRIVALTIME_COLUMN), deparrlist.get(Resource.DEPARTURECITY_COLUMN), deparrlist.get(Resource.ARRIVALCITY_COLUMN),
            bookedTickets.getFlightClass());
      }

    }
    if (optionSelection == 0) {
      System.out.println("********Empty Booking history *******");
    } else { bookingHistoryTable.print();
            return ticketCancelation(distinctBookingId, currentUserDetails);
      

    }
    return null;

  }
  TicketInfo ticketCancelation(List<String> distinctBookingId,ProfileDetails currentUserDetails)
  {
    CommandLineTable listOrderId = new CommandLineTable();
   
    System.out.println("Enter the Corresponding CODE for Cancelation:");
    int optionSelected = flightUtils.getIntegerInput();

    int optionSelection = 0;
     listOrderId.setHeaders("Code", "Username", "UserAge", "Usergender", "FlightId ", "TicketNumber", "BookingId",
          "AirLines ", " DepartureCity ", " ArrivalCity ", "  DepartureTime ", "  ArrivalTime ", " FlightClass ",
          " Amount");
    List<BookedTickets> bookedTicketsList = databaseHandler
         .getBookedIdsBookingList(distinctBookingId.get(optionSelected - 1));
    for (BookedTickets bTickets : bookedTicketsList) {
        optionSelection += 1;

        HashMap<String, String> deparrtimeclasslist = databaseHandler
            .getDepartureArrivalFlightName(bTickets.getFlightNumber());

        departureCity = deparrtimeclasslist.get(Resource.DEPARTURECITY_COLUMN);
        arrivalCity = deparrtimeclasslist.get(Resource.ARRIVALCITY_COLUMN);
        departureTime = deparrtimeclasslist.get(Resource.DEPARTURETIME_COLUMN);
        arrivalTime = deparrtimeclasslist.get(Resource.ARRIVALTIME_COLUMN);
        flightName = deparrtimeclasslist.get(Resource.FLIGHT_NAME_COLUMN);
        flightClass = bTickets.getFlightClass();
        listOrderId.addRow(String.valueOf(optionSelection), bTickets.getPassengerlist().getPassengerName(),
            String.valueOf(bTickets.getPassengerlist().getPassengerAge()),
            bTickets.getPassengerlist().getPassengerGender(), bTickets.getFlightNumber(), bTickets.getSeatNumber(),
            bTickets.getBookingId(), flightName, deparrtimeclasslist.get(Resource.DEPARTURECITY_COLUMN),
            deparrtimeclasslist.get(Resource.ARRIVALCITY_COLUMN), departureTime, arrivalTime, bTickets.getFlightClass(),
            "Rs." + String.valueOf(bTickets.getAmount()));

      }

    listOrderId.print();

    System.out.println("1.Do you want to Cancel all \n2.Do you want to Cancel specific:");
    int tempchoice = flightUtils.getIntegerInput();

    List<String> options = new ArrayList<String>();
    if (tempchoice == 1) {

      for (int i = 1; i <= optionSelection; i++)
        options.add(String.valueOf(i));

    } else {
      System.out.println("Enter the Corresponding CODE Separated by space for Cancelation:");
      options = Arrays.asList(flightUtils.getStringInput().split(" "));

    }

    for (String tickets : options) {
      BookedTickets ticketsBooked = bookedTicketsList.get(Integer.parseInt(tickets) - 1);
      if (databaseHandler.ticketCanceling(distinctBookingId.get(optionSelected - 1),ticketsBooked .getSeatNumber())) {
        SeatsAllocate.seats.get(ticketsBooked .getFlightNumber() + '-' + ticketsBooked .getFlightClass())
            .add(Integer.parseInt(ticketsBooked .getSeatNumber().substring(1)));
        ticketid.add(ticketsBooked .getSeatNumber());
      } else {
        System.out.println("sorry we could not cancel for Ticketnumber:" + ticketsBooked .getSeatNumber());
      }
    }
    noOfseats = options.size();

    TicketInfo ticketInfo = new TicketInfo(departureCity, arrivalCity, noOfseats, flightClass,
        currentUserDetails.getEmail(), departureTime, arrivalTime, flightName, distinctBookingId.get(optionSelected - 1),
        ticketid);

    int isrestored = updateAirlines(bookedTicketsList.get(0).getFlightNumber());
    if (isrestored == 0) {

      System.out.println("*********Your Cancelation coundnt get processed.Please try again later********");

    } else {
      System.out.println("*********Your Cancelation is Successfully processed********");
      return ticketInfo;
    }
    return null;

  }

}
