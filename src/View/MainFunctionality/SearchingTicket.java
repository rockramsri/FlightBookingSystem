package View.MainFunctionality;

import Utils.*;
import Database.*;
import Database.DBTableClass.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class SearchingTicket {
  FlightUtils flightUtils;
  DatabaseHandler databaseHandler;

  public SearchingTicket() {
    flightUtils = FlightUtils.getInstance();
    databaseHandler = DatabaseHandler.getInstance();
  }

  // used to search ticket Availablity based on Every criteria given
  public void seachBySpecific() {

    String dateTicket = "";
    String departureCity = "";
    String arrivalCity = "";
    String flightClass = "";
    int nofSeatsAdult = 0;
    int nofSeatsInfant = 0;
    int nofSeatsChild = 0;

    flightUtils.clearScreen();

    ArrayList<String> cList = Resource.citiesList();
    int citynumber = 0; // Getting DepartureCity
    Date current_date = new Date();
    while (true) {

      System.out.println("Enter your Departure Date(DD/MM/YYY):");
      dateTicket = flightUtils.getStringInput();
      String pattern = "dd/MM/yyyy";
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

      try {
        Date search_date = simpleDateFormat.parse(dateTicket);
        if (current_date.compareTo(search_date) != 1) {
          break;
        }
        System.out.println("The entered date is Expired");
      } catch (ParseException parseException) {
        System.out.println("--Please check your Entered date format--");
      }

    }
    // here
    CommandLineTable cityTable = new CommandLineTable();
    cityTable.setHeaders("CODE", "CITY");
    for (String city : cList) {
      citynumber = citynumber + 1;
      cityTable.addRow(String.valueOf(citynumber), city);

    }
    cityTable.print();
    while (true) {
      System.out.println("Enter the  city of Departure (Corresponding Number):");
      int depcityChoice = flightUtils.getIntegerInput();

      if (depcityChoice >= 0 && depcityChoice <= cList.size()) {
        departureCity = cList.get(depcityChoice - 1);
        cList.remove(depcityChoice - 1);
        break;
      }
    }
    // ExtraProcess.clearscreen();

    while (true) {
      System.out.println("Enter the  city for Arrival (Corresponding Number)::");
      int arrCityChoice = flightUtils.getIntegerInput();

      if (arrCityChoice >= 0 && arrCityChoice <= cList.size()) {
        arrivalCity = cList.get(arrCityChoice - 1);
        cList.remove(arrCityChoice - 1);
        break;
      }
    }

    // ExtraProcess.clearscreen();
    System.out.println("Enter Number of seats for Adults(age above 15):");
    nofSeatsAdult = flightUtils.getIntegerInput();

    System.out.println("Enter Number of seats for child(age below 16):");
    nofSeatsChild = flightUtils.getIntegerInput();

    System.out.println("Enter Number of seats for infants(age above 3):");
    nofSeatsInfant = flightUtils.getIntegerInput();

    // ExtraProcess.clearscreen();
    System.out.println("1.Economic:");
    System.out.println("2.Business:");
    int checker = flightUtils.getIntegerInput();

    // System.out.println("\n");
    if (checker == 1) {
      flightClass = "Economic";
    } else {
      flightClass = "Business";
    }
    List<Airlines> lAirlines = databaseHandler.bookingList(departureCity, arrivalCity, dateTicket,
        nofSeatsAdult + nofSeatsChild + nofSeatsInfant, flightClass);
    int optionCount = 0;
    System.out.println("1.Round Trip\n2.Single Trip");

    int tripOption = flightUtils.getIntegerInput();

    List<Airlines> roundTripAirlines = databaseHandler.bookingList(arrivalCity, departureCity, "",
        nofSeatsAdult + nofSeatsChild + nofSeatsInfant, flightClass);
    String patternwithTime = "dd/MM/yyyy hh:mm";
    SimpleDateFormat roundTripDateFormat = new SimpleDateFormat(patternwithTime);

    CommandLineTable tablebook = new CommandLineTable();
    tablebook.setHeaders("  Code  ", "   AirLines  ", "   DepartureCity  ", "       ArrivalCity      ",
        "      DepartureTime      ", "      ArrivalTime      ", "      FlightClass      ", "  Cost  ");

    for (Airlines aobject : lAirlines) {

      if (tripOption == 1) {

        for (Airlines airline : roundTripAirlines) {
          try {
            Date roundtripDepDate = roundTripDateFormat.parse(airline.getDepartureTime());
            Date singletripArrDate = roundTripDateFormat.parse(aobject.getArrivalTime());
            if (singletripArrDate.compareTo(roundtripDepDate) != 1) {
              optionCount += 1;
              double totalCost = (nofSeatsAdult * airline.getCostPerSeat()
                  + (nofSeatsChild + nofSeatsInfant) * airline.getCostPerSeat() * 0.75)
                  + (nofSeatsAdult * aobject.getCostPerSeat()
                      + (nofSeatsChild + nofSeatsInfant) * aobject.getCostPerSeat() * 0.75);

              tablebook.addRow(String.valueOf(optionCount), aobject.getFlightName(), aobject.getDepartureCity(),
                  aobject.getArrivalCity(), aobject.getDepartureTime(), aobject.getArrivalTime(),
                  aobject.getFlightClass(), "Rs." + String.valueOf(totalCost));
              break;
            }
          } catch (ParseException parseException) {
            continue;
          }

        }

      } else {
        optionCount += 1;
        double totalCost = nofSeatsAdult * aobject.getCostPerSeat()
            + (nofSeatsChild + nofSeatsInfant) * aobject.getCostPerSeat() * 0.75;

        tablebook.addRow(String.valueOf(optionCount), aobject.getFlightName(), aobject.getDepartureCity(),
            aobject.getArrivalCity(), aobject.getDepartureTime(), aobject.getArrivalTime(), aobject.getFlightClass(),
            "Rs." + String.valueOf(totalCost));
      }

    }
    if (optionCount == 0) {
      flightUtils.clearScreen();
      System.out.println("********No Flights is available Now*******");
    } else {
      tablebook.print();
    }

  }

  // used to search ticket Availablity based on particular Airlines
  public void searchByFlight() {

    int options = 0;
    CommandLineTable flightTable = new CommandLineTable();
    flightTable.setHeaders("CODE", " FLIGHT ");
    for (String flight : Resource.flightList()) {
      options = options + 1;
      flightTable.addRow(String.valueOf(options), flight);

    }
    flightTable.print();
    System.out.println("Enter the Corresponding number of the flight to Search:");
    int optionSelected = flightUtils.getIntegerInput();

    CommandLineTable tablebook = new CommandLineTable();
    tablebook.setHeaders("  Code ", "      AirLines  ", " DepartureCity ", "  ArrivalCity  ", "   DepartureTime     ",
        "     ArrivalTime    ", "  FlightClass  ", " SeatsAvailabale  ", "     CostperSeat");
    options = 0;
    for (Airlines airlines : databaseHandler
        .searchAirlinesByFlightName(Resource.flightList().get(optionSelected - 1))) {
      options += 1;
      tablebook.addRow(String.valueOf(options), airlines.getFlightName(), airlines.getDepartureCity(),
          airlines.getArrivalCity(), airlines.getDepartureTime(), airlines.getArrivalTime(), airlines.getFlightClass(),
          String.valueOf(airlines.getCurrentSeatsAvailable()), "Rs." + String.valueOf(airlines.getCostPerSeat()));

    }
    tablebook.print();
    if (options == 0) {
      flightUtils.clearScreen();
      System.out.println("No Flights available");
    }

  }

  // used to search ticket Availablity based on particular Date
  public void searchByDate() {

    String dateTicket = "";
    Date current_date = new Date();
    while (true) {

      System.out.println("Enter the  Date(DD/MM/YYY):");
      dateTicket = flightUtils.getStringInput();
      String pattern = "dd/MM/yyyy";
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
      try {
        Date search_date = simpleDateFormat.parse(dateTicket);

        if (current_date.compareTo(search_date) != 1) {
          break;
        }
        System.out
            .println("The entered date is Not available\n1.Do you want to go back\n2.Do you want to Enter Date again:");
        int internalOption = flightUtils.getIntegerInput();
        if (internalOption == 1)
          return;
      } catch (ParseException parseException) {
        System.out.println("--Please check your Entered date format--");
      }

    }

    int options = 0;
    CommandLineTable tablebook = new CommandLineTable();
    tablebook.setHeaders("  Code ", "      AirLines  ", " DepartureCity ", "  ArrivalCity  ", "   DepartureTime     ",
        "     ArrivalTime    ", "  FlightClass  ", " SeatsAvailabale  ", "     CostperSeat");

    for (Airlines airlines : databaseHandler.searchAirlinesByDate(dateTicket)) {
      options += 1;
      tablebook.addRow(String.valueOf(options), airlines.getFlightName(), airlines.getDepartureCity(),
          airlines.getArrivalCity(), airlines.getDepartureTime(), airlines.getArrivalTime(), airlines.getFlightClass(),
          String.valueOf(airlines.getCurrentSeatsAvailable()), "Rs." + String.valueOf(airlines.getCostPerSeat()));

    }
    tablebook.print();
    if (options == 0) {
      flightUtils.clearScreen();
      System.out.println("No Flights available");
    }

  }

  // used to search ticket Availablity based on particular City
  public void searchByCity() {

    int options = 0;
    CommandLineTable cityTable = new CommandLineTable();
    cityTable.setHeaders("CODE", "CITY");
    for (String city : Resource.citiesList()) {
      options = options + 1;
      cityTable.addRow(String.valueOf(options), city);

    }
    cityTable.print();
    System.out.println("Enter the Corresponding CODE of the city to Search:");
    int optionSelected = flightUtils.getIntegerInput();

    CommandLineTable tablebook = new CommandLineTable();

    tablebook.setHeaders("  Code ", "      AirLines  ", " DepartureCity ", "  ArrivalCity  ", "   DepartureTime     ",
        "     ArrivalTime    ", "  FlightClass  ", " SeatsAvailabale  ", "     CostperSeat");
    options = 0;
    for (Airlines airlines : databaseHandler.searchAirlinesByCity(Resource.citiesList().get(optionSelected - 1))) {
      options += 1;
      tablebook.addRow(String.valueOf(options), airlines.getFlightName(), airlines.getDepartureCity(),
          airlines.getArrivalCity(), airlines.getDepartureTime(), airlines.getArrivalTime(), airlines.getFlightClass(),
          String.valueOf(airlines.getCurrentSeatsAvailable()), "Rs." + String.valueOf(airlines.getCostPerSeat()));

    }
    tablebook.print();
    if (options == 0) {
      flightUtils.clearScreen();
      System.out.println("No Flights available");
    }

  }

}
