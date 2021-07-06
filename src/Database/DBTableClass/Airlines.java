package Database.DBTableClass;
/* POJO Class for Airlines table in the MySqlDatabase */


public class Airlines {
    private String flightNumber;
    private String flightName;
    private String departureCity;
    private String arrivalCity;
    private String departureTime;
    private String arrivalTime;
    private String flightClass;
    private int currentSeatsAvailable;
    private int costPerSeat;

    public Airlines(String arrivalCity, String arrivalTime, int costPerSeat, int currentSeatsAvailable,
            String departureCity, String departureTime, String flightName, String flightClass, String flightNumber,
            int noOfSeats) {
        this.arrivalCity = arrivalCity;
        this.arrivalTime = arrivalTime;
        this.costPerSeat = costPerSeat;
        this.currentSeatsAvailable = currentSeatsAvailable;
        this.departureCity = departureCity;
        this.departureTime = departureTime;
        this.flightName = flightName;
        this.flightClass = flightClass;
        this.flightNumber = flightNumber;
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

    public String getFlightClass() {
        return flightClass;
    }

    public String getFlightName() {
        return flightName;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

}
