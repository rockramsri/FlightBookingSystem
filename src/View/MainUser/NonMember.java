package View.MainUser;

import Utils.FlightUtils;
import View.MainFunctionality.SearchingTicket;

public class NonMember {

    public SearchingTicket searchingTicket;

    NonMember() {
        searchingTicket = new SearchingTicket();
    }

    // For Searching available Flights
    public void search() {
        final int SEARCH_BY_CITY = 1;
        final int SEARCH_BY_FLIGHT = 2;
        final int SEARCH_BY_DATE = 3;
        final int SEARCH_SPECIFIC = 4;

        while (true) {

            int internalOption = 0;
            System.out.println("1.Search by City");
            System.out.println("2.Search by Flights");
            System.out.println("3.Search by Date");
            System.out.println("4.Search Specific");

            internalOption = FlightUtils.getInstance().getIntegerInput();

            switch (internalOption) {
                case SEARCH_BY_CITY:
                    searchingTicket.searchByCity();
                    break;
                case SEARCH_BY_FLIGHT:
                    searchingTicket.searchByFlight();
                    break;
                case SEARCH_BY_DATE:
                    searchingTicket.searchByDate();
                    break;
                case SEARCH_SPECIFIC:
                    searchingTicket.seachBySpecific();
                    break;
                default:
                    System.out.println("You have entered a wrong option");
            }

            System.out.println("1.Do you want to Search ");
            System.out.println("2.Back");
            int searchOption = FlightUtils.getInstance().getIntegerInput();

            if (searchOption == 2)
                break;
        }
    }

}