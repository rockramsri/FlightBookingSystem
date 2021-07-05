package Database.DBTableClass;
import View.Ticket.*;
/* POJO Class for bookedtickets table in the MySqlDatabase */

public class BookedTickets {
    private String id;
    private UserDetails Userlist;
    private String flightId;
    private String ticketId;
    private String bookingId;
    private String bookedOn;
    private String cancelledOn;
    private String isCancelled;
    private float amount;
    private String flightClass;

    public BookedTickets(float amount, String bookedOn, String bookingId, String cancelledOn, String flightId,
            String id, String isCancelled, String ticketId, UserDetails userlist, String flightclass) {

        this.amount = amount;
        this.bookedOn = bookedOn;
        this.bookingId = bookingId;
        this.cancelledOn = cancelledOn;

        this.flightId = flightId;
        this.id = id;
        this.isCancelled = isCancelled;
        this.ticketId = ticketId;
        Userlist = userlist;
        flightClass = flightclass;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
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

    public String getFlightId() {
        return flightId;
    }

    public String getId() {
        return id;
    }

    public String getIsCancelled() {
        return isCancelled;
    }

    public String getTicketId() {
        return ticketId;
    }

    public UserDetails getUserlist() {
        return Userlist;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setBookedOn(String bookedOn) {
        this.bookedOn = bookedOn;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setCancelledOn(String cancelledOn) {
        this.cancelledOn = cancelledOn;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIsCancelled(String isCancelled) {
        this.isCancelled = isCancelled;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public void setUserlist(UserDetails userlist) {
        Userlist = userlist;
    }

}
