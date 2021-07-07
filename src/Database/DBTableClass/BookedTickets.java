package Database.DBTableClass;

import View.Ticket.*;
/* POJO Class for bookedtickets table in the MySqlDatabase */

public class BookedTickets {
    private final String userId;
    private final String flightNumber;
    private final String seatNumber;
    private final String bookingId;
    private final String bookedOn;
    private final String cancelledOn;
    private final String isCancelled;
    private final float amount;
    private final String flightClass;

    private final PassengerDetails passengerList;

    public BookedTickets(float amount, String bookedOn, String bookingId, String cancelledOn, String flightId,
            String userId, String isCancelled, String seatNumber, PassengerDetails passengerlist, String flightclass) {

        this.amount = amount;
        this.bookedOn = bookedOn;
        this.bookingId = bookingId;
        this.cancelledOn = cancelledOn;

        this.flightNumber = flightId;
        this.userId = userId;
        this.isCancelled = isCancelled;
        this.seatNumber = seatNumber;
        passengerList = passengerlist;
        flightClass = flightclass;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public float getAmount() {
        return amount;
    }

    public String getBookedOn() {
        return bookedOn;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getCancelledOn() {
        return cancelledOn;
    }

    public String getIsCancelled() {
        return isCancelled;
    }

    public PassengerDetails getPassengerlist() {
        return passengerList;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getUserId() {
        return userId;
    }

}
