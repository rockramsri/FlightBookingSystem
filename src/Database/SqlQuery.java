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
        return "update " + Resource.User_INFO_TABLE_NAME + " SET " + Resource.PASSWORD_COLUMN + "='" + password
                + "' where " + Resource.ID_COLUMN + "='" + Resource.currentUserDetails.getId() + "'";
    }

    public static String getIdByMailQuery(String tablename, String mail) {
        return "select " + Resource.ID_COLUMN + " from " + tablename + " where " + Resource.EMAIL_COLUMN + "='" + mail
                + "'";
    }

    public static String userRecordInsertQuery(String tablename, String name, String dob, String email, String password,
            String Phonenumber) {
        return "insert into " + tablename + " (" + Resource.NAME_COLUMN + "," + Resource.DOB_COLUMN + ","
                + Resource.EMAIL_COLUMN + "," + Resource.PASSWORD_COLUMN + "," + Resource.PHONENUMBER_COLUMN
                + ") values('" + name + "','" + dob + "','" + email + "','" + password + "','" + Phonenumber + "')";
    }

    public static String uniqueKeySetter() {
        return "UPDATE userprofile  SET " + Resource.ID_COLUMN + "=concat('Usr',cast(" + Resource.AUTO_COLUMN
                + " as char(200))) WHERE " + Resource.AUTO_COLUMN + "=last_insert_id();";
    }

    public static String uniqueKeyGetter() {
        return "select last_insert_id();";
    }

    public static String recordExistMailQuery(String tablename, String email, String pass) {
        return "select exists(" + selectquery(tablename) + " where " + Resource.EMAIL_COLUMN + "='" + email + "'"
                + (pass.length() == 0 ? "" : " and " + Resource.PASSWORD_COLUMN + "='" + pass + "'") + ")";
    }

    public static String recordExistByUserinfoQuery(String tablename, String userid, String pass) {
        return "select exists(" + selectquery(tablename) + " where " + Resource.ID_COLUMN + "='" + userid + "'"
                + (pass.length() == 0 ? "" : " and " + Resource.PASSWORD_COLUMN + "='" + pass + "'") + ")";
    }

    public static String availableFlightDetailsquery(String flight, String flightdetails, String depcity,
            String arrcity, String depdate, int NofSeats, String flightClass) {
        String alternateDeptimeStr = " and " + Resource.DEPARTURETIME_COLUMN + " like '%" + depdate + "%'";

        return "select " + flight + "." + Resource.FLIGHTNUMBER_COLUMN + ", " + flight + "." + Resource.FLIGHT_COLUMN
                + ", " + flight + "." + Resource.DEPARTURECITY_COLUMN + "," + flight + "." + Resource.ARRIVALCITY_COLUMN
                + "," + flightdetails + "." + Resource.DEPARTURETIME_COLUMN + "," + flightdetails + "."
                + Resource.ARRIVALTIME_COLUMN + "," + flightdetails + "." + Resource.FLIGHTCLASS_COLUMN + ","
                + flightdetails + "." + Resource.NOOFSEATS_COLUMN + "," + flightdetails + "."
                + Resource.CURRENTSEATSAVAILABLE_COLUMN + "," + flightdetails + "." + Resource.COSTPERSEAT_COLUMN
                + " from " + flight + " INNER JOIN " + flightdetails + " ON " + flight + "."
                + Resource.FLIGHTNUMBER_COLUMN + "=" + flightdetails + "." + Resource.FLIGHTNUMBER_COLUMN + " where "
                + flight + "." + Resource.DEPARTURECITY_COLUMN + "='" + depcity + "' and " + flight + "."
                + Resource.ARRIVALCITY_COLUMN + "='" + arrcity + "' and " + flightdetails + "."
                + Resource.CURRENTSEATSAVAILABLE_COLUMN + ">=" + String.valueOf(NofSeats)
                + (depdate.length() == 0 ? "" : alternateDeptimeStr) + " and " + flightdetails + "."
                + Resource.FLIGHTCLASS_COLUMN + "='" + flightClass + "'";
    }

    public static String updateFlightCurrentSeatsquery(String tablename, int noofseats, String flightId, String sign) {
        return "Update " + tablename + " SET " + Resource.CURRENTSEATSAVAILABLE_COLUMN + "="
                + Resource.CURRENTSEATSAVAILABLE_COLUMN + "" + sign + String.valueOf(noofseats) + " Where "
                + Resource.FLIGHTNUMBER_COLUMN + "='" + flightId + "'";
    }

    public static String insertBookedTicketsQuery(String tablename, String userId, String username, int userage,
            String usergender, String flightId, String ticketId, String orderId, String bookedOn, String cancelledOn,
            String isCancelled, float amount, String flightClass) {
        return "insert into " + tablename + " values('" + userId + "','" + username + "','" + String.valueOf(userage)
                + "','" + usergender + "','" + flightId + "','" + ticketId + "','" + orderId + "','" + bookedOn + "','"
                + cancelledOn + "','" + isCancelled + "','" + flightClass + "'," + String.valueOf(amount) + ")";
    }

    public static String availableSummaryQuery(String tablename, String userId, String status) {
        return "select * from " + tablename + " where " + Resource.ID_COLUMN + "='" + userId + "' and "
                + Resource.ISCANCELLED_COLUMN + "='" + status + "'";
    }

    public static String ticketlistquery(String tablename, String bookingId) {
        return "select * from " + tablename + " where " + Resource.BOOKINGID_COLUMN + "='" + bookingId + "' and "
                + Resource.ISCANCELLED_COLUMN + "='no'";
    }

    public static String ticketCancelingQuery(String tablename, String BookingId, String ticketId) {
        return "Update " + tablename + " SET " + Resource.CANCELLEDON_COLUMN + "='" + flightUtils.dateTimeGetter()
                + "'," + Resource.ISCANCELLED_COLUMN + "='yes' where " + Resource.BOOKINGID_COLUMN + "='" + BookingId
                + "' and " + Resource.TICKETID_COLUMN + " like '_" + ticketId + "'";
    }

    public static String depArrivalCityFlightNamequery(String tablename, String flightId) {
        return "select " + Resource.DEPARTURECITY_COLUMN + "," + Resource.ARRIVALCITY_COLUMN + ","
                + Resource.FLIGHT_COLUMN + " from " + tablename + " where " + Resource.FLIGHTNUMBER_COLUMN + "='"
                + flightId + "'";
    }

    public static String depArrivalTimequery(String tablename, String flightId) {
        return "select " + Resource.DEPARTURETIME_COLUMN + "," + Resource.ARRIVALTIME_COLUMN + " from " + tablename
                + " where " + Resource.FLIGHTNUMBER_COLUMN + "='" + flightId + "'";
    }

    public static String noOfSeatsQuery(String tablename, String bookingId, String cancel) {
        return "select count(*) from " + tablename + " where " + Resource.BOOKINGID_COLUMN + "='" + bookingId + "' and "
                + Resource.ISCANCELLED_COLUMN + "='" + cancel + "' group by " + Resource.BOOKINGID_COLUMN + "";
    }

    public static String getUserInfo(String tablename, String id) {
        return "select * from " + tablename + " where " + Resource.ID_COLUMN + "='" + id + "'";
    }

    public static String distinctColumn(String tablename, String columnName) {
        return "select distinct " + columnName + " from " + tablename;
    }

    // search functions
    public static String search(String flight, String flightDetails) {
        return "select " + flight + "." + Resource.FLIGHTNUMBER_COLUMN + ", " + flight + "." + Resource.FLIGHT_COLUMN
                + ", " + flight + "." + Resource.DEPARTURECITY_COLUMN + "," + flight + "." + Resource.ARRIVALCITY_COLUMN
                + "," + flightDetails + "." + Resource.DEPARTURETIME_COLUMN + "," + flightDetails + "."
                + Resource.ARRIVALTIME_COLUMN + "," + flightDetails + "." + Resource.FLIGHTCLASS_COLUMN + ","
                + flightDetails + "." + Resource.NOOFSEATS_COLUMN + "," + flightDetails + "."
                + Resource.CURRENTSEATSAVAILABLE_COLUMN + "," + flightDetails + "." + Resource.COSTPERSEAT_COLUMN
                + " from " + flight + " INNER JOIN " + flightDetails + " ON " + flight + "."
                + Resource.FLIGHTNUMBER_COLUMN + "=" + flightDetails + "." + Resource.FLIGHTNUMBER_COLUMN;
    }

    public static String searchByflight(String flight, String flightDetails, String flightname) {
        return search(flight, flightDetails) + " where " + flight + "." + Resource.FLIGHT_COLUMN + "='" + flightname
                + "'";
    }

    public static String searchBycity(String flight, String flightDetails, String cityname) {
        return search(flight, flightDetails) + " where " + flight + "." + Resource.DEPARTURECITY_COLUMN + "='"
                + cityname + "' or " + flight + "." + Resource.ARRIVALCITY_COLUMN + "='" + cityname + "'";

    }

    public static String searchByDate(String flight, String flightDetails, String date) {
        return search(flight, flightDetails) + " where " + flightDetails + ".Departuretime like '%" + date + "%' or "
                + flightDetails + ".Arrivaltime='%" + date + "%'";
    }

}
