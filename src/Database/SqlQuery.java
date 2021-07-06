package Database;

import Utils.*;

/**
 * This Classs is used to return the SqlQuery
 */
public class SqlQuery {

    private static FlightUtils flightUtils = FlightUtils.getInstance();

    public static String selectquery(String tablename) {
        return "select * from " + tablename;
    }

    public static String passwordUpdater(String password) {
        return "update " + Resource.USER_INFO_TABLE_NAME + " SET " + Resource.PASSWORD_COLUMN + "='" + password
                + "' where " + Resource.ID_COLUMN + "='" + Resource.currentUserDetails.getId() + "'";
    }

    public static String getIdByMailQuery(String mail) {
        return "select " + Resource.ID_COLUMN + " from " + Resource.USER_INFO_TABLE_NAME + " where "
                + Resource.EMAIL_COLUMN + "='" + mail + "'";
    }

    public static String userRecordInsertQuery(String name, String dob, String email, String password,
            String Phonenumber) {
        return "insert into " + Resource.USER_INFO_TABLE_NAME + " (" + Resource.NAME_COLUMN + "," + Resource.DOB_COLUMN
                + "," + Resource.EMAIL_COLUMN + "," + Resource.PASSWORD_COLUMN + "," + Resource.PHONENUMBER_COLUMN
                + ") values('" + name + "','" + dob + "','" + email + "','" + password + "','" + Phonenumber + "')";
    }

    public static String uniqueKeySetter() {
        return "UPDATE " + Resource.USER_INFO_TABLE_NAME + "  SET " + Resource.ID_COLUMN + "=concat('Usr',cast("
                + Resource.AUTO_COLUMN + " as char(200))) WHERE " + Resource.EMAIL_COLUMN + "='"
                + Resource.currentUserDetails.getEmail() + "';";
    }

    public static String uniqueKeyGetter() {
        return "select " + Resource.AUTO_COLUMN + " from " + Resource.USER_INFO_TABLE_NAME + " where "
                + Resource.EMAIL_COLUMN + "='" + Resource.currentUserDetails.getEmail() + "';";
    }

    public static String recordExistMailQuery(String email, String pass) {
        return "select exists(" + selectquery(Resource.USER_INFO_TABLE_NAME) + " where " + Resource.EMAIL_COLUMN + "='"
                + email + "'" + (pass.length() == 0 ? "" : " and " + Resource.PASSWORD_COLUMN + "='" + pass + "'")
                + ")";
    }

    public static String recordExistByUserinfoQuery(String userid, String pass) {
        return "select exists(" + selectquery(Resource.USER_INFO_TABLE_NAME) + " where " + Resource.ID_COLUMN + "='"
                + userid + "'" + (pass.length() == 0 ? "" : " and " + Resource.PASSWORD_COLUMN + "='" + pass + "'")
                + ")";
    }

    public static String availableFlightDetailsquery(String flight, String flightBookingAvailablity, String depcity,
            String arrcity, String depdate, int NofSeats, String flightClass) {
        String alternateDeptimeStr = " and " + Resource.DEPARTURETIME_COLUMN + " like '%" + depdate + "%'";

        return "select " + flight + "." + Resource.FLIGHTNUMBER_COLUMN + ", " + flight + "." + Resource.FLIGHT_COLUMN
                + ", " + flight + "." + Resource.DEPARTURECITY_COLUMN + "," + flight + "." + Resource.ARRIVALCITY_COLUMN
                + "," + flightBookingAvailablity + "." + Resource.DEPARTURETIME_COLUMN + "," + flightBookingAvailablity
                + "." + Resource.ARRIVALTIME_COLUMN + "," + flightBookingAvailablity + "." + Resource.FLIGHTCLASS_COLUMN
                + "," + flightBookingAvailablity + "." + Resource.NOOFSEATS_COLUMN + "," + flightBookingAvailablity
                + "." + Resource.CURRENTSEATSAVAILABLE_COLUMN + "," + flightBookingAvailablity + "."
                + Resource.COSTPERSEAT_COLUMN + " from " + flight + " INNER JOIN " + flightBookingAvailablity + " ON "
                + flight + "." + Resource.FLIGHTNUMBER_COLUMN + "=" + flightBookingAvailablity + "."
                + Resource.FLIGHTNUMBER_COLUMN + " where " + flight + "." + Resource.DEPARTURECITY_COLUMN + "='"
                + depcity + "' and " + flight + "." + Resource.ARRIVALCITY_COLUMN + "='" + arrcity + "' and "
                + flightBookingAvailablity + "." + Resource.CURRENTSEATSAVAILABLE_COLUMN + ">="
                + String.valueOf(NofSeats) + (depdate.length() == 0 ? "" : alternateDeptimeStr) + " and "
                + flightBookingAvailablity + "." + Resource.FLIGHTCLASS_COLUMN + "='" + flightClass + "'";
    }

    public static String updateFlightCurrentSeatsquery(int noOfSeats, String flightId, String sign) {
        return "Update " + Resource.FLIGHT_BOOKING_AVAILABLITY_TICKET_TABLE_NAME + " SET "
                + Resource.CURRENTSEATSAVAILABLE_COLUMN + "=" + Resource.CURRENTSEATSAVAILABLE_COLUMN + "" + sign
                + String.valueOf(noOfSeats) + " Where " + Resource.FLIGHTNUMBER_COLUMN + "='" + flightId + "'";
    }

    public static String insertBookedTicketsQuery(String userId, String passengerName, int passengerAge,
            String passengerGender, String flightId, String ticketId, String orderId, String bookedOn,
            String cancelledOn, String isCancelled, float amount, String flightClass) {
        return "insert into " + Resource.BOOKED_TICKET_TABLE_NAME + " values('" + userId + "','" + passengerName + "','"
                + String.valueOf(passengerAge) + "','" + passengerGender + "','" + flightId + "','" + ticketId + "','"
                + orderId + "','" + bookedOn + "','" + cancelledOn + "','" + isCancelled + "','" + flightClass + "',"
                + String.valueOf(amount) + ")";
    }

    public static String availableSummaryQuery(String userId, String status) {
        return "select * from " + Resource.BOOKED_TICKET_TABLE_NAME + " where " + Resource.ID_COLUMN + "='" + userId
                + "' and " + Resource.ISCANCELLED_COLUMN + "='" + status + "'";
    }

    public static String ticketlistquery(String bookingId) {
        return "select * from " + Resource.BOOKED_TICKET_TABLE_NAME + " where " + Resource.BOOKINGID_COLUMN + "='"
                + bookingId + "' and " + Resource.ISCANCELLED_COLUMN + "='no'";
    }

    public static String ticketCancelingQuery(String bookingId, String ticketId) {
        return "Update " + Resource.BOOKED_TICKET_TABLE_NAME + " SET " + Resource.CANCELLEDON_COLUMN + "='"
                + flightUtils.dateTimeGetter() + "'," + Resource.ISCANCELLED_COLUMN + "='yes' where "
                + Resource.BOOKINGID_COLUMN + "='" + bookingId + "' and " + Resource.TICKETID_COLUMN + " like '_"
                + ticketId + "'";
    }

    public static String depArrivalCityFlightNamequery(String flightNumber) {
        return "select " + Resource.DEPARTURECITY_COLUMN + "," + Resource.ARRIVALCITY_COLUMN + ","
                + Resource.FLIGHT_COLUMN + " from " + Resource.FLIGHT_TICKET_TABLE_NAME + " where "
                + Resource.FLIGHTNUMBER_COLUMN + "='" + flightNumber + "'";
    }

    public static String depArrivalTimequery(String flightNumber) {
        return "select " + Resource.DEPARTURETIME_COLUMN + "," + Resource.ARRIVALTIME_COLUMN + " from "
                + Resource.FLIGHT_BOOKING_AVAILABLITY_TICKET_TABLE_NAME + " where " + Resource.FLIGHTNUMBER_COLUMN
                + "='" + flightNumber + "'";
    }

    public static String noOfSeatsQuery(String bookingId, String cancel) {
        return "select count(*) from " + Resource.BOOKED_TICKET_TABLE_NAME + " where " + Resource.BOOKINGID_COLUMN
                + "='" + bookingId + "' and " + Resource.ISCANCELLED_COLUMN + "='" + cancel + "' group by "
                + Resource.BOOKINGID_COLUMN + "";
    }

    public static String getUserInfo(String userId) {
        return "select * from " + Resource.USER_INFO_TABLE_NAME + " where " + Resource.ID_COLUMN + "='" + userId + "'";
    }

    public static String distinctColumn(String tablename, String columnName) {
        return "select distinct " + columnName + " from " + tablename;
    }

    // search functions
    public static String search(String flight, String flightBookingAvailablity) {
        return "select " + flight + "." + Resource.FLIGHTNUMBER_COLUMN + ", " + flight + "." + Resource.FLIGHT_COLUMN
                + ", " + flight + "." + Resource.DEPARTURECITY_COLUMN + "," + flight + "." + Resource.ARRIVALCITY_COLUMN
                + "," + flightBookingAvailablity + "." + Resource.DEPARTURETIME_COLUMN + "," + flightBookingAvailablity
                + "." + Resource.ARRIVALTIME_COLUMN + "," + flightBookingAvailablity + "." + Resource.FLIGHTCLASS_COLUMN
                + "," + flightBookingAvailablity + "." + Resource.NOOFSEATS_COLUMN + "," + flightBookingAvailablity
                + "." + Resource.CURRENTSEATSAVAILABLE_COLUMN + "," + flightBookingAvailablity + "."
                + Resource.COSTPERSEAT_COLUMN + " from " + flight + " INNER JOIN " + flightBookingAvailablity + " ON "
                + flight + "." + Resource.FLIGHTNUMBER_COLUMN + "=" + flightBookingAvailablity + "."
                + Resource.FLIGHTNUMBER_COLUMN;
    }

    public static String searchByFlight(String flight, String flightBookingAvailablity, String flightName) {
        return search(flight, flightBookingAvailablity) + " where " + flight + "." + Resource.FLIGHT_COLUMN + "='"
                + flightName + "'";
    }

    public static String searchByCity(String flight, String flightBookingAvailablity, String cityName) {
        return search(flight, flightBookingAvailablity) + " where " + flight + "." + Resource.DEPARTURECITY_COLUMN
                + "='" + cityName + "' or " + flight + "." + Resource.ARRIVALCITY_COLUMN + "='" + cityName + "'";

    }

    public static String searchByDate(String flight, String flightBookingAvailablity, String date) {
        return search(flight, flightBookingAvailablity) + " where " + flightBookingAvailablity
                + ".Departuretime like '%" + date + "%' or " + flightBookingAvailablity + ".Arrivaltime='%" + date
                + "%'";
    }

}
