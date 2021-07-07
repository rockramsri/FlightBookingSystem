package Utils;

import java.util.*;

/**
 * This class contains the Constant values for the application
 */
public class Resource {
    public static final String BOOKED_TICKET_TABLE_NAME = "BookedTickets";
    public static final String FLIGHT_TICKET_TABLE_NAME = "Flight";
    public static final String FLIGHT_BOOKING_AVAILABLITY_TICKET_TABLE_NAME = "FlightBookingAvailablity";
    public static final String USER_INFO_TABLE_NAME = "UserProfile";
    public static final String MYACCOUNT_ID = "internzh2021@gmail.com";
    public static final String MYACCOUNT_PASSWORD = "internzh";
    public static final String AUTO_COLUMN = "id_increment";
    public static final String ID_COLUMN = "user_id";
    public static final String NAME_COLUMN = "name";
    public static final String DOB_COLUMN = "dob";
    public static final String EMAIL_COLUMN = "email";
    public static final String PASSWORD_COLUMN = "password";
    public static final String PHONENUMBER_COLUMN = "phone_number";
    public static final String USERNAME_COLUMN = "passenger_name";
    public static final String USERAGE_COLUMN = "passenger_age";
    public static final String USERGENDER_COLUMN = "passenger_gender";
    public static final String FLIGHTID_COLUMN = "flight_number";
    public static final String TICKETID_COLUMN = "seat_number";
    public static final String BOOKINGID_COLUMN = "booking_id";
    public static final String FLIGHT_NAME_COLUMN = "flight_name";
    public static final String DEPARTURETIME_COLUMN = "departure_time";
    public static final String ARRIVALTIME_COLUMN = "arrival_time";
    public static final String BOOKEDON_COLUMN = "booked_on";
    public static final String CANCELLEDON_COLUMN = "cancelled_on";
    public static final String ISCANCELLED_COLUMN = "is_cancelled";
    public static final String FLIGHTCLASS_COLUMN = "flight_class";
    public static final String AMOUNT_COLUMN = "amount";
    public static final String FLIGHTNUMBER_COLUMN = "flight_number";
    public static final String DEPARTURECITY_COLUMN = "departure_city";
    public static final String ARRIVALCITY_COLUMN = "arrival_city";
    public static final String NOOFSEATS_COLUMN = "no_of_seats";
    public static final String CURRENTSEATSAVAILABLE_COLUMN = "current_seats_available";
    public static final String COSTPERSEAT_COLUMN = "cost_per_seat";

    public static final String ECONOMIC_FLIGHT_CLASS = "Economic";
    public static final String BUSINESS_FLIGHT_CLASS = "Business";

    public static final String CURRENCY_SIGN = "Rs.";
    public static final String BOOKING_ID_TAG = "ord";
    public static final String USER_ID_TAG = "Usr";

    public static final String CODE_HEADER = " Code ";
    public static final String FLIGHT_NAME_HEADER = "  Airlines  ";
    public static final String DEPARTURECITY_HEADER = " Departure City ";
    public static final String ARRIVALCITY_HEADER = " Arrival City ";
    public static final String DEPARTURETIME_HEADER = " Departure Time ";
    public static final String ARRIVALTIME_HEADER = " Arrival Time ";
    public static final String FLIGHTCLASS_HEADER = " Flight Class ";
    public static final String COST_HEADER = " Cost ";
    public static final String AMOUNT_HEADER = " Amount ";
    public static final String FLIGHT_NUMBER_HEADER = " Flight Number ";
    public static final String SEAT_NUMBER_HEADER = " Ticket Number ";
    public static final String BOOKING_ID_HEADER = " Booking Id ";

    public static final String COUNT_COLUMN = "count(*)";

    public static final String EMPTY_DATA = "  -  ";

    // Table creation Queries
    static public ArrayList<String> tableCreationList() {
        ArrayList<String> tableCreationList = new ArrayList<String>() {
            {
                add("create table IF NOT EXISTS " + USER_INFO_TABLE_NAME + "( " + AUTO_COLUMN
                        + " int NOT NULL AUTO_INCREMENT unique," + ID_COLUMN + " varchar(100) primary key default '',"
                        + NAME_COLUMN + " varchar(100)," + DOB_COLUMN + " varchar(100)," + EMAIL_COLUMN
                        + " varchar(100) unique," + PASSWORD_COLUMN + " varchar(100)," + PHONENUMBER_COLUMN
                        + " varchar(200) );");
                add("create table IF NOT EXISTS " + FLIGHT_TICKET_TABLE_NAME + "(" + FLIGHTNUMBER_COLUMN
                        + " varchar(200) primary key," + FLIGHT_NAME_COLUMN + " varchar(100)," + DEPARTURECITY_COLUMN
                        + " varchar(100)," + ARRIVALCITY_COLUMN + " varchar(100) );");
                add("create table IF NOT EXISTS " + FLIGHT_BOOKING_AVAILABLITY_TICKET_TABLE_NAME + "("
                        + FLIGHTNUMBER_COLUMN + " varchar(200)," + DEPARTURETIME_COLUMN + " varchar(100),"
                        + ARRIVALTIME_COLUMN + " varchar(100)," + FLIGHTCLASS_COLUMN + " varchar(100),"
                        + NOOFSEATS_COLUMN + " int," + CURRENTSEATSAVAILABLE_COLUMN + " int," + COSTPERSEAT_COLUMN
                        + " bigint ); ");
                add("create table IF NOT EXISTS " + BOOKED_TICKET_TABLE_NAME + "(" + ID_COLUMN + " varchar(100),"
                        + USERNAME_COLUMN + " varchar(100)," + USERAGE_COLUMN + " varchar(100)," + USERGENDER_COLUMN
                        + " varchar(100)," + FLIGHTID_COLUMN + " varchar(100)," + TICKETID_COLUMN + " varchar(100),"
                        + BOOKINGID_COLUMN + " varchar(100)," + BOOKEDON_COLUMN + " varchar(100)," + CANCELLEDON_COLUMN
                        + " varchar(100)," + ISCANCELLED_COLUMN + " varchar(100)," + FLIGHTCLASS_COLUMN
                        + " varchar(100)," + AMOUNT_COLUMN + " float);");
            }
        };
        return tableCreationList;
    }

    // returns Cities List available
    static public ArrayList<String> citiesList() {
        ArrayList<String> CityList = new ArrayList<String>() {
            {
                add("Raipur");
                add("Mysore");
                add("Tirupati");

                add("New Delhi");
                add("Mumbai");
                add("Bangalore");

                add("Chennai");
                add("Kolkata");
                add("Hyderabad");

                add("Cochin");
                add("Pune");
                add("Goa");
            }
        };
        return CityList;
    }

    // returns Flight names List available
    static public ArrayList<String> flightNameList() {

        ArrayList<String> Flightlist = new ArrayList<String>() {
            {
                add("SpieceJet");
                add("Panam");
                add("Lufthansa");
                add("indigo");
            }
        };
        return Flightlist;
    }

    // returns flightnumber with corresponding seats for seatallcation
    static public Map<String, Integer> flightNumbersMap() {
        Map<String, Integer> seatSizeMap = new HashMap<String, Integer>();
        for (int i = 1; i <= 15; i++) {
            seatSizeMap.put("Fl00" + String.valueOf(i) + "-Economic", 20);
            seatSizeMap.put("Fl00" + String.valueOf(i) + "-Business", 20);
        }

        return seatSizeMap;
    }

}
