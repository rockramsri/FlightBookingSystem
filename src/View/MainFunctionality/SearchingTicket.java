package View.MainFunctionality;

import Utils.*;
import Database.*;
import Database.DBTableClass.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class SearchingTicket {

  void print(List<Airlines> airlinesList) {
    if (airlinesList.size() == 0) {
      FlightUtils.getInstance().clearScreen();
      System.out.println("No Flights available");
      return;
    }
    CommandLineTable tablebook = new CommandLineTable();
    tablebook.setHeaders(Resource.CODE_HEADER, Resource.FLIGHT_NAME_HEADER, Resource.DEPARTURECITY_HEADER,
        Resource.ARRIVALCITY_HEADER, Resource.DEPARTURETIME_HEADER, Resource.ARRIVALTIME_HEADER,
        Resource.FLIGHTCLASS_HEADER, " SeatsAvailabale  ", "  CostperSeat ");
    int options = 0;
    for (Airlines airlines : airlinesList) {
      options += 1;
      tablebook.addRow(String.valueOf(options), airlines.getFlightName(), airlines.getDepartureCity(),
          airlines.getArrivalCity(), airlines.getDepartureTime(), airlines.getArrivalTime(), airlines.getFlightClass(),
          String.valueOf(airlines.getCurrentSeatsAvailable()),
          Resource.CURRENCY_SIGN + String.valueOf(airlines.getCostPerSeat()));

    }
    tablebook.print();

  }

  void roundTripSearch(String arrivalCity, String departureCity, int nofSeatsAdult, int nofSeatsChild,
      int nofSeatsInfant, String flightClass, String dateTicket) {
    int optionCount = 0;
    CommandLineTable tablebook = new CommandLineTable();
    tablebook.setHeaders(Resource.CODE_HEADER, Resource.FLIGHT_NAME_HEADER, Resource.DEPARTURECITY_HEADER,
        Resource.ARRIVALCITY_HEADER, Resource.DEPARTURETIME_HEADER, Resource.ARRIVALTIME_HEADER,
        Resource.FLIGHT_NAME_HEADER, Resource.CODE_HEADER);
    List<Airlines> listOfAirlines = DatabaseHandler.getInstance().bookingList(departureCity, arrivalCity, dateTicket,
        nofSeatsAdult + nofSeatsChild + nofSeatsInfant, flightClass);
    List<Airlines> roundTripAirlines = DatabaseHandler.getInstance().bookingList(arrivalCity, departureCity, "",
        nofSeatsAdult + nofSeatsChild + nofSeatsInfant, flightClass);
    String patternwithTime = "dd/MM/yyyy hh:mm";
    SimpleDateFormat roundTripDateFormat = new SimpleDateFormat(patternwithTime);

    for (Airlines airline : listOfAirlines) {
      for (Airlines roundTripAirline : roundTripAirlines) {
        try {
          Date roundtripDepDate = roundTripDateFormat.parse(roundTripAirline.getDepartureTime());
          Date singletripArrDate = roundTripDateFormat.parse(airline.getArrivalTime());
          if (singletripArrDate.compareTo(roundtripDepDate) != 1) {
            optionCount += 1;
            double totalCost = (nofSeatsAdult * roundTripAirline.getCostPerSeat()
                + (nofSeatsChild + nofSeatsInfant) * roundTripAirline.getCostPerSeat() * 0.75)
                + (nofSeatsAdult * airline.getCostPerSeat()
                    + (nofSeatsChild + nofSeatsInfant) * airline.getCostPerSeat() * 0.75);

            tablebook.addRow(String.valueOf(optionCount), airline.getFlightName(), airline.getDepartureCity(),
                airline.getArrivalCity(), airline.getDepartureTime(), airline.getArrivalTime(),
                airline.getFlightClass(), Resource.CURRENCY_SIGN + String.valueOf(totalCost));
            break;
          }
        } catch (ParseException parseException) {
          continue;
        }

      }
    }
    if (optionCount == 0) {
      FlightUtils.getInstance().clearScreen();
      System.out.println("********No Flights is available Now*******");
    } else {
      tablebook.print();
    }

  }

  void singleTripSearch(String arrivalCity, String departureCity, int nofSeatsAdult, int nofSeatsChild,
      int nofSeatsInfant, String flightClass, String dateTicket) {
    int optionCount = 0;
    List<Airlines> listOfAirlines = DatabaseHandler.getInstance().bookingList(departureCity, arrivalCity, dateTicket,
        nofSeatsAdult + nofSeatsChild + nofSeatsInfant, flightClass);
    if (listOfAirlines.size() == 0) {
      FlightUtils.getInstance().clearScreen();
      System.out.println("********No Flights is available Now*******");
      return;
    }
    CommandLineTable tablebook = new CommandLineTable();
    tablebook.setHeaders(Resource.CODE_HEADER, Resource.FLIGHT_NAME_HEADER, Resource.DEPARTURECITY_HEADER,
        Resource.ARRIVALCITY_HEADER, Resource.DEPARTURETIME_HEADER, Resource.ARRIVALTIME_HEADER,
        Resource.FLIGHT_NAME_HEADER, Resource.CODE_HEADER);
    for (Airlines aobject : listOfAirlines) {
      optionCount += 1;
      double totalCost = nofSeatsAdult * aobject.getCostPerSeat()
          + (nofSeatsChild + nofSeatsInfant) * aobject.getCostPerSeat() * 0.75;

      tablebook.addRow(String.valueOf(optionCount), aobject.getFlightName(), aobject.getDepartureCity(),
          aobject.getArrivalCity(), aobject.getDepartureTime(), aobject.getArrivalTime(), aobject.getFlightClass(),
          Resource.CURRENCY_SIGN + String.valueOf(totalCost));
    }
    tablebook.print();
  }

  // used to search ticket Availablity based on Every criteria given
  public void seachBySpecific() {

    final int ROUND_TRIP = 1;
    final int SINGLE_TRIP = 2;
    final int ECONOMIC = 1;
    final int BUSINESS = 2;

    String dateTicket = "";
    String departureCity = "";
    String arrivalCity = "";
    String flightClass = "";
    int nofSeatsAdult = 0;
    int nofSeatsInfant = 0;
    int nofSeatsChild = 0;

    dateTicket = FlightUtils.getInstance().getDepartureDate();

    HashMap<String, String> cityMap = FlightUtils.getInstance().getDepartureCityAndArrivalCity();
    departureCity = cityMap.get(Resource.DEPARTURECITY_COLUMN);
    arrivalCity = cityMap.get(Resource.ARRIVALCITY_COLUMN);

    System.out.println("Enter Number of seats for Adults(age above 15):");
    nofSeatsAdult = FlightUtils.getInstance().getIntegerInput();

    System.out.println("Enter Number of seats for child(age below 16):");
    nofSeatsChild = FlightUtils.getInstance().getIntegerInput();

    System.out.println("Enter Number of seats for infants(age above 3):");
    nofSeatsInfant = FlightUtils.getInstance().getIntegerInput();

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

    triploop: while (true) {
      System.out.println("1.Round Trip\n2.Single Trip");

      int tripOption = FlightUtils.getInstance().getIntegerInput();
      switch (tripOption) {
        case ROUND_TRIP:
          roundTripSearch(arrivalCity, departureCity, nofSeatsAdult, nofSeatsChild, nofSeatsInfant, flightClass,
              dateTicket);
          break triploop;
        case SINGLE_TRIP:
          singleTripSearch(arrivalCity, departureCity, nofSeatsAdult, nofSeatsChild, nofSeatsInfant, flightClass,
              dateTicket);
          break triploop;
        default:
          System.out.println("Entered option is wrong .Please Enter Again");

      }
    }

  }

  // used to search ticket Availablity based on particular Airlines
  public void searchByFlight() {

    int options = 0;
    CommandLineTable flightTable = new CommandLineTable();
    flightTable.setHeaders("CODE", " FLIGHT ");
    for (String flight : Resource.flightNameList()) {
      options = options + 1;
      flightTable.addRow(String.valueOf(options), flight);

    }
    flightTable.print();
    System.out.println("Enter the Corresponding number of the flight to Search:");
    int optionSelected = FlightUtils.getInstance().getIntegerInput();
    print(DatabaseHandler.getInstance().searchAirlinesByFlightName(Resource.flightNameList().get(optionSelected - 1)));

  }

  // used to search ticket Availablity based on particular Date
  public void searchByDate() {

    String dateTicket = "";
    Date current_date = new Date();
    while (true) {

      System.out.println("Enter the  Date(DD/MM/YYY):");
      dateTicket = FlightUtils.getInstance().getStringInput();
      String pattern = "dd/MM/yyyy";
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
      try {
        Date search_date = simpleDateFormat.parse(dateTicket);

        if (current_date.compareTo(search_date) != 1) {
          break;
        }
        System.out
            .println("The entered date is Not available\n1.Do you want to go back\n2.Do you want to Enter Date again:");
        int internalOption = FlightUtils.getInstance().getIntegerInput();
        if (internalOption == 1)
          return;
      } catch (ParseException parseException) {
        System.out.println("--Please check your Entered date format--");
      }

    }
    print(DatabaseHandler.getInstance().searchAirlinesByDate(dateTicket));

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
    int optionSelected = FlightUtils.getInstance().getIntegerInput();
    print(DatabaseHandler.getInstance().searchAirlinesByCity(Resource.citiesList().get(optionSelected - 1)));

  }

}
