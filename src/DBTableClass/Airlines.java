package DBTableClass;
/* POJO Class for Airlines table in the MySqlDatabase */


public class Airlines {
    private String flightId;
    private String flight;
    private String departureCity;
    private String arrivalCity;
    private String departureTime;
    private String arrivalTime;
    private String flightClass;
    private int noOfSeats;
    private int currentSeatsAvailable;
    private int costPerSeat;
    public Airlines(String arrivalCity,String arrivalTime,int costPerSeat,int currentSeatsAvailable,String departureCity,String departureTime,String flight,String flightClass,String flightId,int noOfSeats)
    {
        this.arrivalCity = arrivalCity;
        this.arrivalTime = arrivalTime;
        this.costPerSeat = costPerSeat;
        this.currentSeatsAvailable = currentSeatsAvailable;
        this.departureCity = departureCity;
        this.departureTime = departureTime;
        this.flight = flight;
        this.flightClass = flightClass;
        this.flightId = flightId;
        this.noOfSeats = noOfSeats;
    }
    public String getArrivalCity() {
        return arrivalCity;
    }
    public String getArrivalTime() {
        return arrivalTime;
    }
    public int getCostPerSeat() {
        return costPerSeat;
    }
    public int getCurrentSeatsAvailable() {
        return currentSeatsAvailable;
    }
    public String getDepartureCity() {
        return departureCity;
    }
    public String getDepartureTime() {
        return departureTime;
    }
    public String getFlight() {
        return flight;
    }
    public String getFlightClass() {
        return flightClass;
    }
    public String getFlightId() {
        return flightId;
    }
    public int getNoOfSeats() {
        return noOfSeats;
    }
    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public void setCostPerSeat(int costPerSeat) {
        this.costPerSeat = costPerSeat;
    }
    public void setCurrentSeatsAvailable(int currentSeatsAvailable) {
        this.currentSeatsAvailable = currentSeatsAvailable;
    }
    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
    public void setFlight(String flight) {
        this.flight = flight;
    }
    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }
    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }
    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }
    
}
