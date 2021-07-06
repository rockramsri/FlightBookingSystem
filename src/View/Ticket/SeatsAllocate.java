package View.Ticket;

import java.util.*;
import Database.DBTableClass.*;
import Database.*;
import Utils.*;

/**
 * This class is used to process and store the seats alloction for different
 * flights
 */
public class SeatsAllocate {

    public static Map<String, List<Integer>> seats = new HashMap<String, List<Integer>>();

    public static void seatUpdater() {
        if (SeatsAllocate.seats != null) {
            List<BookedTickets> bookedTicketsList = new DatabaseHandler().fullBookedTicketStable();
            for (BookedTickets bTickets : bookedTicketsList) {
                int element = Integer.parseInt(bTickets.getSeatNumber().substring(1));
                SeatsAllocate.seats.get(bTickets.getFlightNumber() + '-' + bTickets.getFlightClass())
                        .remove(SeatsAllocate.seats.get(bTickets.getFlightNumber() + '-' + bTickets.getFlightClass())
                                .indexOf(element));
                if (bTickets.getIsCancelled().equals("yes")) {

                    SeatsAllocate.seats.get(bTickets.getFlightNumber() + '-' + bTickets.getFlightClass()).add(element);

                }

            }

        }
    }

    public static void initiator() {
        Map<String, Integer> flightseats = Resource.flightNumbersMap();

        for (Map.Entry<String, Integer> entry : flightseats.entrySet()) {
            List<Integer> list = new ArrayList<Integer>() {
                {
                    for (int i = 1; i <= entry.getValue(); i++)
                        add(i);
                }
            };
            seats.put(entry.getKey(), list);
        }

    }

}
