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

  // Getter and setters for data members

  public BookingTickets() // intializing values
  {
    passengerList = new ArrayList<PassengerDetails>();
    ticketList = new ArrayList<String>();
  }

  public void getInfoForBookingTickets() // Getting required input to book ticket
  {
    final int ECONOMIC = 1;
    final int BUSINESS = 2;
    dateTicket = FlightUtils.getInstance().getDepartureDate();

    HashMap<String, String> cityMap = FlightUtils.getInstance().getDepartureCityAndArrivalCity();
    departureCity = cityMap.get(Resource.DEPARTURECITY_COLUMN);
    arrivalCity = cityMap.get(Resource.ARRIVALCITY_COLUMN);

    System.out.println("Enter Number of seats:");
    nofSeats = FlightUtils.getInstance().getIntegerInput();

    for (int i = 1; i <= nofSeats; i++) {
      System.out.println("Enter the Passenger:" + String.valueOf(i) + " Details");
      passengerList.add(new PassengerDetails());
    }

    classwhile: while (true) {
      System.out.println("1.Economic:");
      System.out.println("2.Business:");
      switch (FlightUtils.getInstance().getIntegerInput()) {
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
  public boolean listAirlines() {
    FlightUtils.getInstance().clearScreen();
    boolean available = false;

    int optionCount = 0;

    List<Airlines> airlinesList = DatabaseHandler.getInstance().bookingList(departureCity, arrivalCity, dateTicket,
        nofSeats, flightClass);
    if (airlinesList.size() == 0) {
      FlightUtils.getInstance().clearScreen();
      System.out.println("********No Flights is available Now*******");
      return available;
    }
    CommandLineTable tablebook = new CommandLineTable();
    tablebook.setHeaders(Resource.CODE_HEADER, Resource.FLIGHT_NAME_HEADER, Resource.DEPARTURECITY_HEADER,
        Resource.ARRIVALCITY_HEADER, Resource.DEPARTURETIME_HEADER, Resource.ARRIVALTIME_COLUMN,
        Resource.FLIGHTCLASS_HEADER, Resource.COST_HEADER);

    for (Airlines airlines : airlinesList) {
      optionCount += 1;
      tablebook.addRow(String.valueOf(optionCount), airlines.getFlightName(), airlines.getDepartureCity(),
          airlines.getArrivalCity(), airlines.getDepartureTime(), airlines.getArrivalTime(), airlines.getFlightClass(),
          Resource.CURRENCY_SIGN + String.valueOf(airlines.getCostPerSeat()));
    }

    tablebook.print();
    available = true;
    System.out.println("Enter the Corresponding CODE for Booking:");
    int optionselection = FlightUtils.getInstance().getIntegerInput();

    ticketAvailable = true;
    selectedAirlines = airlinesList.get(optionselection - 1);
    seatPrice = selectedAirlines.getCostPerSeat();

    DatabaseHandler.getInstance().updateAirlines(selectedAirlines.getFlightNumber(), nofSeats, "-");

    return available;

  }

  // Used to register the data in the database of the ticket booked
  public TicketInfo bookTickets(ProfileDetails currentUserDetails) {
    if (!ticketAvailable)
      return null;

    String bookingId = getBookingId();
    addBookedTickets(currentUserDetails, bookingId);

    TicketInfo bookTicketInfo = new TicketInfo(selectedAirlines.getDepartureCity(), selectedAirlines.getArrivalCity(),
        nofSeats, selectedAirlines.getFlightClass(), currentUserDetails.getEmail(), selectedAirlines.getDepartureTime(),
        selectedAirlines.getArrivalTime(), selectedAirlines.getFlightName(), bookingId, ticketList);

    return bookTicketInfo;
  }

  private void addBookedTickets(ProfileDetails currentUserDetails, String bookingId) {

    for (int i = 1; i <= nofSeats; i++) {

      float cost = (float) (passengerList.get(i - 1).getPassengerAge() > 12 ? seatPrice : seatPrice * (0.75));

      String cancelledOn = "null";
      String bookedOn = FlightUtils.getInstance().dateTimeGetter();
      String isCancelled = "no";
      BookedTickets bookedTickets = new BookedTickets(cost, bookedOn, bookingId, cancelledOn,
          selectedAirlines.getFlightNumber(), currentUserDetails.getId(), isCancelled,
          "P" + String.valueOf(SeatsAllocate.seats
              .get(selectedAirlines.getFlightNumber() + '-' + selectedAirlines.getFlightClass()).get(0)),
          passengerList.get(i - 1), selectedAirlines.getFlightClass());
      DatabaseHandler.getInstance().insertBookedTickets(bookedTickets);

      ticketList.add("P" + String.valueOf(SeatsAllocate.seats
          .get(selectedAirlines.getFlightNumber() + '-' + selectedAirlines.getFlightClass()).get(0)));
      SeatsAllocate.seats.get(selectedAirlines.getFlightNumber() + '-' + selectedAirlines.getFlightClass()).remove(0);
    }
  }

  // used to generate Unique Booking Id
  private String getBookingId() {
    String bookingId = "";
    while (true) {
      bookingId = "ord" + String.valueOf(FlightUtils.getInstance().sizeRandomizer(1000, 9999));
      List<String> allBookingIds = DatabaseHandler.getInstance().distinctColumns(Resource.BOOKED_TICKET_TABLE_NAME,
          Resource.BOOKINGID_COLUMN);
      if (!allBookingIds.contains(bookingId))
        break;
    }
    return bookingId;
  }

}
