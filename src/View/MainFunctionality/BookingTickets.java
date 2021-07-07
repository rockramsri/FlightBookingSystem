package View.MainFunctionality;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Utils.*;
import Database.DBTableClass.*;
import Database.*;
import View.Ticket.*;

public class BookingTickets {

  private String dateTicket = "";

  private String departureCity = "";
  private String arrivalCity = "";
  private int nofSeats = 0;
  private String flightClass = "";
  private int seatPrice = 0;
  private List<PassengerDetails> passengerList;
  private List<String> ticketList;
  private boolean ticketAvailable = false;

  Airlines selectedAirlines;
  FlightUtils flightUtils;
  DatabaseHandler databaseHandler;

  // Getter and setters for data members

  public BookingTickets() // intializing values
  {
    flightUtils = FlightUtils.getInstance();
    passengerList = new ArrayList<PassengerDetails>();
    ticketList = new ArrayList<String>();
    databaseHandler = DatabaseHandler.getInstance();
  }

  public void requestTickets() // Getting required input to book ticket
  {
    final int ECONOMIC = 1;
    final int BUSINESS = 2;
    dateTicket = flightUtils.getDepartureDate();

    HashMap<String, String> cityMap = flightUtils.getDepartureCityAndArrivalCity();
    departureCity = cityMap.get(Resource.DEPARTURECITY_COLUMN);
    arrivalCity = cityMap.get(Resource.ARRIVALCITY_COLUMN);

    System.out.println("Enter Number of seats:");
    nofSeats = flightUtils.getIntegerInput();

    for (int i = 1; i <= nofSeats; i++) {
      System.out.println("Enter the Passenger:" + String.valueOf(i) + " Details");
      passengerList.add(new PassengerDetails());
    }

    classwhile: while (true) {
      System.out.println("1.Economic:");
      System.out.println("2.Business:");
      switch (flightUtils.getIntegerInput()) {
        case ECONOMIC:
          flightClass = Resource.ECONOMIC_FLIGHT_CLASS;

          break classwhile;
        case BUSINESS:
          flightClass = Resource.BUSINESS_FLIGHT_CLASS;
          break classwhile;

        default:
          System.out.println("Entered an Invaild Number.Please Enter Again");

      }
    }

  }

  // Listing availabale tickets based on condition
  public boolean listingTickets() {
    flightUtils.clearScreen();
    boolean available = false;

    int optionCount = 0;

    List<Airlines> lAirlines = databaseHandler.bookingList(departureCity, arrivalCity, dateTicket, nofSeats,
        flightClass);
    if (lAirlines.size() == 0) {
      flightUtils.clearScreen();
      System.out.println("********No Flights is available Now*******");
      return available;
    }
    CommandLineTable tablebook = new CommandLineTable();
    tablebook.setHeaders(Resource.CODE_HEADER, Resource.FLIGHT_NAME_HEADER, Resource.DEPARTURECITY_HEADER,
        Resource.ARRIVALCITY_HEADER, Resource.DEPARTURETIME_HEADER, Resource.ARRIVALTIME_COLUMN,
        Resource.FLIGHTCLASS_HEADER, Resource.COST_HEADER);

    for (Airlines aobject : lAirlines) {
      optionCount += 1;
      tablebook.addRow(String.valueOf(optionCount), aobject.getFlightName(), aobject.getDepartureCity(),
          aobject.getArrivalCity(), aobject.getDepartureTime(), aobject.getArrivalTime(), aobject.getFlightClass(),
          Resource.CURRENCY_SIGN + String.valueOf(aobject.getCostPerSeat()));
    }

    tablebook.print();
    available = true;
    System.out.println("Enter the Corresponding CODE for Booking:");
    int optionselection = flightUtils.getIntegerInput();

    ticketAvailable = true;
    selectedAirlines = lAirlines.get(optionselection - 1);
    seatPrice = selectedAirlines.getCostPerSeat();

    databaseHandler.airlinesUpdater(selectedAirlines.getFlightNumber(), nofSeats, "-");

    return available;

  }

  // Used to register the data in the database of the ticket booked
  public TicketInfo bookingRegister(ProfileDetails currentUserDetails) {
    if (!ticketAvailable)
      return null;

    int i = 0;
    String bookingId = "";
    while (true) {
      bookingId = "ord" + String.valueOf(flightUtils.sizeRandomizer(1000, 9999));
      List<String> allBookingIds = databaseHandler.distinctColumns(Resource.BOOKED_TICKET_TABLE_NAME,
          Resource.BOOKINGID_COLUMN);
      if (!allBookingIds.contains(bookingId))
        break;
    }
    for (i = 1; i <= nofSeats; i++) {

      float cost = (float) (passengerList.get(i - 1).getPassengerAge() > 12 ? seatPrice : seatPrice * (0.75));

      String cancelledOn = "null";
      String bookedOn = flightUtils.dateTimeGetter();
      String isCancelled = "no";
      BookedTickets bookedTickets = new BookedTickets(cost, bookedOn, bookingId, cancelledOn,
          selectedAirlines.getFlightNumber(), currentUserDetails.getId(), isCancelled,
          "P" + String.valueOf(SeatsAllocate.seats
              .get(selectedAirlines.getFlightNumber() + '-' + selectedAirlines.getFlightClass()).get(0)),
          passengerList.get(i - 1), selectedAirlines.getFlightClass());
      databaseHandler.bookingRegister(bookedTickets);

      ticketList.add("P" + String.valueOf(SeatsAllocate.seats
          .get(selectedAirlines.getFlightNumber() + '-' + selectedAirlines.getFlightClass()).get(0)));
      SeatsAllocate.seats.get(selectedAirlines.getFlightNumber() + '-' + selectedAirlines.getFlightClass()).remove(0);
    }

    TicketInfo bookTicketInfo = new TicketInfo(selectedAirlines.getDepartureCity(), selectedAirlines.getArrivalCity(),
        nofSeats, selectedAirlines.getFlightClass(), currentUserDetails.getEmail(), selectedAirlines.getDepartureTime(),
        selectedAirlines.getArrivalTime(), selectedAirlines.getFlightName(), bookingId, ticketList);

    return bookTicketInfo;
  }

}
