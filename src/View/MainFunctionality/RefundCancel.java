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
  // private String flightId="";

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
  public TicketInfo listOfBookings() {

    int optionSelection = 0;

    List<String> distinctOrder = new ArrayList<String>();
    CommandLineTable bookingHistoryTable = new CommandLineTable();
    CommandLineTable listOrderId = new CommandLineTable();

    bookingHistoryTable.setHeaders("  CODE  ", "  FlightId  ", "   BookingId   ", "   AirLines   ",
        "   DepartureTime   ", "   ArrivalTime  ", "   DepartureCity      ", "      ArrivalCity    ", "  Class  ");

    for (BookedTickets bookedTickets : databaseHandler.getBookedList(Resource.currentUserDetails.getId(), "no")) {
      if (!distinctOrder.contains(bookedTickets.getBookingId())) {
        distinctOrder.add(bookedTickets.getBookingId());
        optionSelection += 1;
        HashMap<String, String> deparrlist = databaseHandler.depArrivalGetter(bookedTickets.getFlightId());
        bookingHistoryTable.addRow(String.valueOf(optionSelection), bookedTickets.getFlightId(),
            bookedTickets.getBookingId(), deparrlist.get("flightname"), deparrlist.get("departuretime"),
            deparrlist.get("arrivaltime"), deparrlist.get("departurecity"), deparrlist.get("arrivalcity"),
            bookedTickets.getFlightClass());
      }

    }
    if (optionSelection == 0) {
      // ExtraProcess.clearscreen();
      System.out.println("********Empty Booking history *******");
    } else {
      bookingHistoryTable.print();
      System.out.println("Enter the Corresponding CODE for Cancelation:");
      int optionSelected = flightUtils.getIntegerInput();

      optionSelection = 0;
      listOrderId.setHeaders("Code", "Username", "UserAge", "Usergender", "FlightId ", "TicketNumber", "BookingId",
          "AirLines ", " DepartureCity ", " ArrivalCity ", "  DepartureTime ", "  ArrivalTime ", " FlightClass ",
          " Amount");
      List<BookedTickets> bookedTicketsList = databaseHandler
          .getBookedIdsBookingList(distinctOrder.get(optionSelected - 1));
      for (BookedTickets bTickets : bookedTicketsList) {
        optionSelection += 1;

        HashMap<String, String> deparrtimeclasslist = databaseHandler.depArrivalGetter(bTickets.getFlightId());

        departureCity = deparrtimeclasslist.get("departurecity");
        arrivalCity = deparrtimeclasslist.get("arrivalcity");
        departureTime = deparrtimeclasslist.get("departuretime");
        arrivalTime = deparrtimeclasslist.get("arrivaltime");
        flightName = deparrtimeclasslist.get("flightname");
        flightClass = bTickets.getFlightClass();
        listOrderId.addRow(String.valueOf(optionSelection), bTickets.getUserlist().getUname(),
            String.valueOf(bTickets.getUserlist().getUage()), bTickets.getUserlist().getUgender(),
            bTickets.getFlightId(), bTickets.getTicketId(), bTickets.getBookingId(), flightName,
            deparrtimeclasslist.get("departurecity"), deparrtimeclasslist.get("arrivalcity"), departureTime,
            arrivalTime, bTickets.getFlightClass(), "Rs." + String.valueOf(bTickets.getAmount()));

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
        BookedTickets bTickets = bookedTicketsList.get(Integer.parseInt(tickets) - 1);
        if (databaseHandler.ticketCanceling(distinctOrder.get(optionSelected - 1), bTickets.getTicketId())) {
          SeatsAllocate.seats.get(bTickets.getFlightId() + '-' + bTickets.getFlightClass())
              .add(Integer.parseInt(bTickets.getTicketId().substring(1)));
          ticketid.add(bTickets.getTicketId());
        } else {
          System.out.println("sorry we could not cancel for Ticketnumber:" + bTickets.getTicketId());
        }
      }
      // SeatsAllocate.seatStorer(SeatsAllocate.seats);
      // Collections.sort(SeatsAllocate.seats.get(bookedTicketsList.get(0).getFlightId()));
      noOfseats = options.size();

      TicketInfo ticketInfo = new TicketInfo(departureCity, arrivalCity, noOfseats, flightClass,
          Resource.currentUserDetails.getEmail(), departureTime, arrivalTime, flightName,
          distinctOrder.get(optionSelected - 1), ticketid, null);

      int isrestored = updateAirlines(bookedTicketsList.get(0).getFlightId());
      if (isrestored == 0) {

        System.out.println("*********Your Cancelation coundnt get processed.Please try again later********");

      } else {
        System.out.println("*********Your Cancelation is Successfully processed********");
        // System.out.println("SENDING YOUR MAIL PLEASE WAIT..............");
        return ticketInfo;
      }

    }
    return null;

  }

}
