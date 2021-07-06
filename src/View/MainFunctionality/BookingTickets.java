package View.MainFunctionality;

import java.util.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    flightUtils.clearScreen();

    ArrayList<String> cList = Resource.citiesList();
    int citynumber = 0; // Getting DepartureCity
    Date current_Date = new Date();
    while (true) {

      System.out.println("Enter your Departure Date(DD/MM/YYY):");
      dateTicket = flightUtils.getStringInput();
      String pattern = "dd/MM/yyyy";
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

      try {
        Date ticket_Date = simpleDateFormat.parse(dateTicket);
        if (current_Date.compareTo(ticket_Date) != 1) {
          break;
        }
        System.out.println("The entered date is not Available");
      } catch (ParseException parseException) {
        System.out.println("--Please check your Entered date format--");
      }

    }
    CommandLineTable cityTable = new CommandLineTable();
    cityTable.setHeaders("CODE", "CITY");
    for (String city : cList) {
      citynumber = citynumber + 1;
      cityTable.addRow(String.valueOf(citynumber), city);

    }
    cityTable.print();
    while (true) {
      int depcitychoice;
      System.out.println("Enter the  city of Departure (Corresponding Number):");
      depcitychoice = flightUtils.getIntegerInput();

      if (depcitychoice >= 0 && depcitychoice <= cList.size()) {
        departureCity = cList.get(depcitychoice - 1);
        // cList.remove(depcitychoice-1);
        break;
      }
    }

    while (true) {
      System.out.println("Enter the  city for Arrival (Corresponding Number)::");
      int arrCityChoice = flightUtils.getIntegerInput();

      if (arrCityChoice >= 0 && arrCityChoice <= cList.size()) {
        arrivalCity = cList.get(arrCityChoice - 1);
        // cList.remove(arrCityChoice-1);
        break;
      }
    }

    // ExtraProcess.clearscreen();
    System.out.println("Enter Number of seats:");
    nofSeats = flightUtils.getIntegerInput();

    for (int i = 1; i <= nofSeats; i++) {
      System.out.println("Enter the Passenger:" + String.valueOf(i) + " Details");
      passengerList.add(new PassengerDetails());
    }

    // ExtraProcess.clearscreen();
    System.out.println("1.Economic:");
    System.out.println("2.Business:");
    int checker = flightUtils.getIntegerInput();
    System.out.println("\n");
    if (checker == 1) {
      flightClass = "Economic";
    } else {
      flightClass = "Business";
    }

  }

  // Listing availabale tickets based on condition
  public boolean listingTickets() {
    flightUtils.clearScreen();
    boolean available = false;

    int optionCount = 0;
    CommandLineTable tablebook = new CommandLineTable();
    List<Airlines> lAirlines = databaseHandler.bookingList(departureCity, arrivalCity, dateTicket, nofSeats,
        flightClass);
    tablebook.setHeaders("  Code  ", "   AirLines  ", "   DepartureCity  ", "       ArrivalCity      ",
        "      DepartureTime      ", "      ArrivalTime      ", "      FlightClass      ", "  Cost  ");

    for (Airlines aobject : lAirlines) {
      optionCount += 1;
      tablebook.addRow(String.valueOf(optionCount), aobject.getFlightName(), aobject.getDepartureCity(),
          aobject.getArrivalCity(), aobject.getDepartureTime(), aobject.getArrivalTime(), aobject.getFlightClass(),
          "Rs." + String.valueOf(aobject.getCostPerSeat()));
    }
    if (optionCount == 0) {
      flightUtils.clearScreen();
      System.out.println("********No Flights is available Now*******");
    } else {
      tablebook.print();
      available = true;
      System.out.println("Enter the Corresponding CODE for Booking:");
      int optionselection = flightUtils.getIntegerInput();

      ticketAvailable = true;
      selectedAirlines = lAirlines.get(optionselection - 1);
      seatPrice = selectedAirlines.getCostPerSeat();

      databaseHandler.airlinesUpdater(selectedAirlines.getFlightNumber(), nofSeats, "-");

    }

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
        nofSeats, selectedAirlines.getFlightClass(), currentUserDetails.getEmail(),
        selectedAirlines.getDepartureTime(), selectedAirlines.getArrivalTime(), selectedAirlines.getFlightName(),
        bookingId, ticketList);

    return bookTicketInfo;
  }

}
