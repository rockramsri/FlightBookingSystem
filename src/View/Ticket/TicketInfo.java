package View.Ticket;

import java.util.*;

/**
 * This class is POJO class for ticket Information
 */
public class TicketInfo {
    private String departureCity;
    private String arrivalCity;
    private int nOfSeats;
    private String flightClass;
    private String userEmail;
    private String bookingId;
    private List<String> seatNumbers;
    private String depTime;
    private String arrTime;
    private String flightName;

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
