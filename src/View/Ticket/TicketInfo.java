package View.Ticket;

import java.util.*;

/**
 * This class is POJO class for ticket Information
 */
public class TicketInfo {
    private final String departureCity;
    private final String arrivalCity;
    private final int nOfSeats;
    private final String flightClass;
    private final String userEmail;
    private final String bookingId;
    private final List<String> seatNumbers;
    private final String depTime;
    private final String arrTime;
    private final String flightName;

    public TicketInfo(String departureCity, String arrivalCity, int nOfSeats, String flightClass, String userEmail,
            String depTime, String arrTime, String flightName, String bookingId, List<String> seatNumbers) {
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.nOfSeats = nOfSeats;
        this.flightClass = flightClass;
        this.userEmail = userEmail;
        this.bookingId = bookingId;
        this.seatNumbers = seatNumbers;
        this.depTime = depTime;
        this.arrTime = arrTime;
        this.flightName = flightName;
    }

    // Getter setter for Ticket Information
    public String getBookingId() {
        return bookingId;
    }

    public List<String> getSeatNumbers() {
        return seatNumbers;
    }

    public String getArrTime() {
        return arrTime;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public String getDepTime() {
        return depTime;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public String getFlightName() {
        return flightName;
    }

    public int getNofSeats() {
        return nOfSeats;
    }

    public String getUserEmail() {
        return userEmail;
    }

}
