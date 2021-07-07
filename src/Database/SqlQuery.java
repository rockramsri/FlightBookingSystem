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

    public static String passwordUpdater(String password, String userId) {
        return "update " + Resource.USER_INFO_TABLE_NAME + " SET " + Resource.PASSWORD_COLUMN + "='" + password
                + "' where " + Resource.ID_COLUMN + "='" + userId + "'";
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

    public static String uniqueKeySetter(String eMail) {
        return "UPDATE " + Resource.USER_INFO_TABLE_NAME + "  SET " + Resource.ID_COLUMN + "=concat('"
                + Resource.USER_ID_TAG + "',cast(" + Resource.AUTO_COLUMN + " as char(200))) WHERE "
                + Resource.EMAIL_COLUMN + "='" + eMail + "';";
    }

    public static String uniqueKeyGetter(String eMail) {
        return "select " + Resource.AUTO_COLUMN + " from " + Resource.USER_INFO_TABLE_NAME + " where "
                + Resource.EMAIL_COLUMN + "='" + eMail + "';";
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

    public static String availableFlightDetailsquery(String depcity, String arrcity, String depdate, int NofSeats,
            String flightClass) {
        String alternateDeptimeStr = " and " + Resource.DEPARTURETIME_COLUMN + " like '%" + depdate + "%'";

        return "select " + Resource.FLIGHT_TICKET_TABLE_NAME + "." + Resource.FLIGHTNUMBER_COLUMN + ", "
                + Resource.FLIGHT_NAME_COLUMN + ", " + Resource.DEPARTURECITY_COLUMN + "," + Resource.ARRIVALCITY_COLUMN
                + "," + Resource.DEPARTURETIME_COLUMN + "," + Resource.ARRIVALTIME_COLUMN + ","
                + Resource.FLIGHTCLASS_COLUMN + "," + Resource.NOOFSEATS_COLUMN + ","
                + Resource.CURRENTSEATSAVAILABLE_COLUMN + "," + Resource.COSTPERSEAT_COLUMN + " from "
                + Resource.FLIGHT_TICKET_TABLE_NAME + " INNER JOIN "
                + Resource.FLIGHT_BOOKING_AVAILABLITY_TICKET_TABLE_NAME + " ON " + Resource.FLIGHT_TICKET_TABLE_NAME
                + "." + Resource.FLIGHTNUMBER_COLUMN + "=" + Resource.FLIGHT_BOOKING_AVAILABLITY_TICKET_TABLE_NAME + "."
                + Resource.FLIGHTNUMBER_COLUMN + " where " + Resource.FLIGHT_TICKET_TABLE_NAME + "."
                + Resource.DEPARTURECITY_COLUMN + "='" + depcity + "' and " + Resource.FLIGHT_TICKET_TABLE_NAME + "."
                + Resource.ARRIVALCITY_COLUMN + "='" + arrcity + "' and "
                + Resource.FLIGHT_BOOKING_AVAILABLITY_TICKET_TABLE_NAME + "." + Resource.CURRENTSEATSAVAILABLE_COLUMN
                + ">=" + String.valueOf(NofSeats) + (depdate.length() == 0 ? "" : alternateDeptimeStr) + " and "
                + Resource.FLIGHT_BOOKING_AVAILABLITY_TICKET_TABLE_NAME + "." + Resource.FLIGHTCLASS_COLUMN + "='"
                + flightClass + "'";
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
                + Resource.FLIGHT_NAME_COLUMN + " from " + Resource.FLIGHT_TICKET_TABLE_NAME + " where "
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
    public static String search() {
        return "select " + Resource.FLIGHT_TICKET_TABLE_NAME + "." + Resource.FLIGHTNUMBER_COLUMN + ", "
                + Resource.FLIGHT_NAME_COLUMN + ", " + Resource.DEPARTURECITY_COLUMN + "," + Resource.ARRIVALCITY_COLUMN
                + "," + Resource.DEPARTURETIME_COLUMN + "," + Resource.ARRIVALTIME_COLUMN + ","
                + Resource.FLIGHTCLASS_COLUMN + "," + Resource.NOOFSEATS_COLUMN + ","
                + Resource.CURRENTSEATSAVAILABLE_COLUMN + "," + Resource.COSTPERSEAT_COLUMN + " from "
                + Resource.FLIGHT_TICKET_TABLE_NAME + " INNER JOIN "
                + Resource.FLIGHT_BOOKING_AVAILABLITY_TICKET_TABLE_NAME + " ON " + Resource.FLIGHT_TICKET_TABLE_NAME
                + "." + Resource.FLIGHTNUMBER_COLUMN + "=" + Resource.FLIGHT_BOOKING_AVAILABLITY_TICKET_TABLE_NAME + "."
                + Resource.FLIGHTNUMBER_COLUMN;
    }

    public static String searchByFlight(String flightName) {
        return search() + " where " + Resource.FLIGHT_TICKET_TABLE_NAME + "." + Resource.FLIGHT_NAME_COLUMN + "='"
                + flightName + "'";
    }

    public static String searchByCity(String cityName) {
        return search() + " where " + Resource.FLIGHT_TICKET_TABLE_NAME + "." + Resource.DEPARTURECITY_COLUMN + "='"
                + cityName + "' or " + Resource.FLIGHT_TICKET_TABLE_NAME + "." + Resource.ARRIVALCITY_COLUMN + "='"
                + cityName + "'";

    }

    public static String searchByDate(String date) {
        return search() + " where " + Resource.FLIGHT_BOOKING_AVAILABLITY_TICKET_TABLE_NAME + "."
                + Resource.DEPARTURETIME_COLUMN + " like '%" + date + "%' or "
                + Resource.FLIGHT_BOOKING_AVAILABLITY_TICKET_TABLE_NAME + "." + Resource.ARRIVALTIME_COLUMN + "='%"
                + date + "%'";
    }

}
