package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Database.DBTableClass.*;
import Utils.*;
import View.Ticket.*;

/** For Handling MysqlDatabase to store and retrieve Information */
public class DatabaseHandler {
    ResultSet dbResult;
    Statement dbStatement;

    private static DatabaseHandler databaseHandlerInstance = null;

    public static DatabaseHandler getInstance() {
        if (databaseHandlerInstance == null)
            databaseHandlerInstance = new DatabaseHandler();
        return databaseHandlerInstance;
    }

    public DatabaseHandler() {
        dbStatement = DatabaseLoader.getDatabaseStatement();
    }

    public void databaseCloser() {
        try {
            if (!dbResult.isClosed())
                dbResult.close();
            if (!dbStatement.isClosed())
                dbStatement.close();
        } catch (NullPointerException nullPointerException) { // If this Exception occurs Then No need to close The
                                                              // Resultset and statement
        } catch (SQLException sqlException) {
            System.out.println("--Closing Database Issue--");
        }

    }

    public void createTable() {
        createUserTable();
        createFlightTable();
        createFlightBookingAvailablityTable();
        createBookedTicketTable();

    }

    public void createUserTable() {
        try {
            dbStatement.executeUpdate(SqlQuery.getUserTableCreationQuery());
        } catch (SQLException e) {
            System.out.println("Failed for creation of user table");
        }
    }

    public void createFlightTable() {
        try {
            dbStatement.executeUpdate(SqlQuery.getFlightTableCreationQuery());
        } catch (SQLException e) {
            System.out.println("Failed for creation of Flight table");
        }
    }

    public void createFlightBookingAvailablityTable() {
        try {
            dbStatement.executeUpdate(SqlQuery.getFlightBookingAvailablityTableCreationQuery());
        } catch (SQLException e) {
            System.out.println("Failed for creation of FlightBookingAvailablity table");
        }
    }

    public void createBookedTicketTable() {

        try {
            dbStatement.executeUpdate(SqlQuery.getBookedTicketTableCreationQuery());
        } catch (SQLException e) {
            System.out.println("Failed for creation of BookedTicket table");
        }
    }

    // Used to Update the password of the user
    public void updatePassword(String password, String userId) {
        try {
            dbStatement.executeUpdate(SqlQuery.passwordUpdater(password, userId));
        } catch (SQLException sqlException) {
            System.out.println("--Could not update password Please try again later--");
        }

    }

    // Used to validate the login info
    public ProfileDetails getLoggedInUserInfo(String registrationUserIDorMailId, String password) {
        ProfileDetails currentUserDetails = null;
        String registeredUserID = getLoggedInUserIdIfExist(registrationUserIDorMailId, password);
        try {
            if (registeredUserID != null && registeredUserID.length() != 0) {
                dbResult = dbStatement.executeQuery(SqlQuery.getUserInfo(registeredUserID));
                dbResult.next();
                currentUserDetails = new ProfileDetails(registeredUserID, dbResult.getString(Resource.NAME_COLUMN),
                        dbResult.getString(Resource.DOB_COLUMN), dbResult.getString(Resource.EMAIL_COLUMN),
                        dbResult.getString(Resource.PASSWORD_COLUMN), dbResult.getString(Resource.PHONENUMBER_COLUMN));
            }

        } catch (SQLException sqlException) {
            System.out.println("--Login issue in database--" + sqlException.toString() + "1");

        }
        return currentUserDetails;
    }

    // Used to get the LoggedIn User Id if Exist
    private String getLoggedInUserIdIfExist(String registrationUserIDorMailId, String password) {
        String registeredUserID = null;
        try {

            if (registrationUserIDorMailId.contains("@")) {
                dbResult = dbStatement
                        .executeQuery(SqlQuery.recordExistMailQuery(registrationUserIDorMailId, password));
            } else {
                dbResult = dbStatement
                        .executeQuery(SqlQuery.recordExistByUserinfoQuery(registrationUserIDorMailId, password));
                registeredUserID = registrationUserIDorMailId;
            }
            dbResult.next();
            if (dbResult.getInt(1) == 1) {
                if (registeredUserID == null) {
                    dbResult = dbStatement.executeQuery(SqlQuery.getIdByMailQuery(registrationUserIDorMailId));
                    dbResult.next();
                    registeredUserID = dbResult.getString(Resource.ID_COLUMN);

                }

            } else {
                registeredUserID = null;
            }
        } catch (SQLException sqlException) {
            System.out.println("--Login issue in database--" + sqlException.toString());
        }

        return registeredUserID;
    }

    // Used to register the and add the data in the database
    public int getRegisteredUserId(ProfileDetails profileDetails) {
        try {
            if (insertUserInfo(profileDetails)) {
                dbResult = dbStatement.executeQuery(SqlQuery.uniqueKeyGetter(profileDetails.getEmail()));
                dbResult.next();
                return dbResult.getInt(Resource.AUTO_COLUMN);
            }

        } catch (SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
            System.out.println("--Your Mail is Already been used--");

        } catch (SQLException sqlException) {
            System.out.println("--User Registration Failed--" + sqlException.toString());

        }
        return 0;

    }

    private boolean insertUserInfo(ProfileDetails profileDetails) {
        try {
            dbStatement.executeUpdate(SqlQuery.userRecordInsertQuery(profileDetails.getName(), profileDetails.getDob(),
                    profileDetails.getEmail(), profileDetails.getPassword(), profileDetails.getPhonenumber()));

            dbStatement.executeUpdate(SqlQuery.uniqueKeySetter(profileDetails.getEmail()));
            return true;
        } catch (SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
            System.out.println("--Your Mail is Already been used--");

        } catch (SQLException sqlException) {
            System.out.println("--User Registration Failed--" + sqlException.toString());

        }
        return false;

    }

    // Used to return the List of airlines based on condition from the Database
    public List<Airlines> bookingList(String departureCity, String arrivalCity, String dateTicket, int nofSeats,
            String flightClass) {
        List<Airlines> airlinesList = new ArrayList<Airlines>();

        try {
            dbResult = dbStatement.executeQuery(SqlQuery.availableFlightDetailsQuery(departureCity, arrivalCity,
                    dateTicket, nofSeats, flightClass));
            while (dbResult.next()) {
                airlinesList.add(new Airlines(dbResult.getString(Resource.ARRIVALCITY_COLUMN),
                        dbResult.getString(Resource.ARRIVALTIME_COLUMN), dbResult.getInt(Resource.COSTPERSEAT_COLUMN),
                        dbResult.getInt(Resource.CURRENTSEATSAVAILABLE_COLUMN),
                        dbResult.getString(Resource.DEPARTURECITY_COLUMN),
                        dbResult.getString(Resource.DEPARTURETIME_COLUMN),
                        dbResult.getString(Resource.FLIGHT_NAME_COLUMN), flightClass,
                        dbResult.getString(Resource.FLIGHTNUMBER_COLUMN), dbResult.getInt(Resource.NOOFSEATS_COLUMN)));
            }
        } catch (SQLException sqlException) {
            System.out.println("--Couldn't fetch data Please try again later--");
        }
        return airlinesList;

    }

    // Used to update the tickets in the airlines
    public int updateAirlines(String flightNumber, int noOfSeats, String sign) {
        try {
            dbStatement.executeUpdate(SqlQuery.updateFlightCurrentSeatsQuery(noOfSeats, flightNumber, sign));
            return 1;
        } catch (SQLException sqlException) {
            System.out.println("--Couldn't Update Cancelled or booked tickets--");
            return 0;
        }
    }

    // used to register the booked ticket in the Bookedtickets table in the database
    public void insertBookedTickets(BookedTickets bookedTickets) {
        try {
            dbStatement.executeUpdate(SqlQuery.insertBookedTicketsQuery(bookedTickets.getUserId(),
                    bookedTickets.getPassengerlist().getPassengerName(),
                    bookedTickets.getPassengerlist().getPassengerAge(),
                    bookedTickets.getPassengerlist().getPassengerGender(), bookedTickets.getFlightNumber(),
                    bookedTickets.getSeatNumber(), bookedTickets.getBookingId(), bookedTickets.getBookedOn(),
                    bookedTickets.getCancelledOn(), bookedTickets.getIsCancelled(), bookedTickets.getAmount(),
                    bookedTickets.getFlightClass()));
        } catch (SQLException sqlException) {
            System.out.println("--Couldn't Register booked Tickets--");
        }
    }

    // used to get Bookedtickets form the databse based on condition
    public List<BookedTickets> getBookedList(String userId, String isCancelled) {
        List<BookedTickets> bookedTicketsList = new ArrayList<BookedTickets>();

        try {

            dbResult = dbStatement.executeQuery(SqlQuery.availableSummaryQuery(userId, isCancelled));
            while (dbResult.next()) {
                try {
                    PassengerDetails passengerDetails = new PassengerDetails(
                            dbResult.getString(Resource.USERNAME_COLUMN),
                            Integer.parseInt(dbResult.getString(Resource.USERAGE_COLUMN)),
                            dbResult.getString(Resource.USERGENDER_COLUMN));

                    bookedTicketsList.add(new BookedTickets(dbResult.getFloat(Resource.AMOUNT_COLUMN),
                            dbResult.getString(Resource.BOOKEDON_COLUMN), dbResult.getString(Resource.BOOKINGID_COLUMN),
                            dbResult.getString(Resource.CANCELLEDON_COLUMN),
                            dbResult.getString(Resource.FLIGHTID_COLUMN), dbResult.getString(Resource.ID_COLUMN),
                            dbResult.getString(Resource.ISCANCELLED_COLUMN),
                            dbResult.getString(Resource.TICKETID_COLUMN), passengerDetails,
                            dbResult.getString(Resource.FLIGHTCLASS_COLUMN)));
                } catch (SQLException sqlException) {
                    continue;
                }

            }

            return bookedTicketsList;
        } catch (SQLException sqlException) {
            System.out.println("--Couldn't fetch History--");
            return null;
        }

    }

    // Used to get the departurecity,arrivalcity and flightclass form the Airlines
    // based in the flightId
    public HashMap<String, String> getDepartureArrivalFlightName(String flightNumber) {
        HashMap<String, String> journeyInfoList = new HashMap<String, String>();

        try {
            dbResult = dbStatement.executeQuery(SqlQuery.departureArrivalCityFlightNameQuery(flightNumber));
            dbResult.next();
            try {
                journeyInfoList.put(Resource.DEPARTURECITY_COLUMN, dbResult.getString(Resource.DEPARTURECITY_COLUMN));
            } catch (SQLDataException sqlDataException) {
                journeyInfoList.put(Resource.DEPARTURECITY_COLUMN, Resource.EMPTY_DATA);
            }
            try {
                journeyInfoList.put(Resource.ARRIVALCITY_COLUMN, dbResult.getString(Resource.ARRIVALCITY_COLUMN));
            } catch (SQLDataException sqlDataException) {
                journeyInfoList.put(Resource.ARRIVALCITY_COLUMN, Resource.EMPTY_DATA);
            }

            try {
                journeyInfoList.put(Resource.FLIGHT_NAME_COLUMN, dbResult.getString(Resource.FLIGHT_NAME_COLUMN));
            } catch (SQLDataException sqlDataException) {
                journeyInfoList.put(Resource.FLIGHT_NAME_COLUMN, Resource.EMPTY_DATA);
            }

            dbResult = dbStatement.executeQuery(SqlQuery.departureArrivalTimeQuery(flightNumber));
            dbResult.next();
            try {
                journeyInfoList.put(Resource.DEPARTURETIME_COLUMN, dbResult.getString(Resource.DEPARTURETIME_COLUMN));
            } catch (SQLDataException sqlDataException) {
                journeyInfoList.put(Resource.DEPARTURETIME_COLUMN, Resource.EMPTY_DATA);
            }
            try {
                journeyInfoList.put(Resource.ARRIVALTIME_COLUMN, dbResult.getString(Resource.ARRIVALTIME_COLUMN));
            } catch (SQLDataException sqlDataException) {
                journeyInfoList.put(Resource.ARRIVALTIME_COLUMN, Resource.EMPTY_DATA);
            }

        } catch (SQLException sqlException) {
            System.out.println("--Couldn't fetch departure arrival  Info--");

            journeyInfoList = getEmptyJourneyInfoList();

        }

        return journeyInfoList;
    }

    private HashMap<String, String> getEmptyJourneyInfoList() {
        HashMap<String, String> journeyInfoList = new HashMap<String, String>();
        journeyInfoList.put(Resource.DEPARTURECITY_COLUMN, Resource.EMPTY_DATA);
        journeyInfoList.put(Resource.ARRIVALCITY_COLUMN, Resource.EMPTY_DATA);
        journeyInfoList.put(Resource.FLIGHT_NAME_COLUMN, Resource.EMPTY_DATA);
        journeyInfoList.put(Resource.DEPARTURETIME_COLUMN, Resource.EMPTY_DATA);
        journeyInfoList.put(Resource.ARRIVALTIME_COLUMN, Resource.EMPTY_DATA);
        return journeyInfoList;
    }

    // Used to get bookedTickets list
    public List<BookedTickets> getBookedIdsBookingList(String bookingId) {
        try {

            dbResult = dbStatement.executeQuery(SqlQuery.ticketListQuery(bookingId));
            List<BookedTickets> bookedTicketsList = new ArrayList<BookedTickets>();
            while (dbResult.next()) {
                try {

                    PassengerDetails passengerDetails = new PassengerDetails(
                            dbResult.getString(Resource.USERNAME_COLUMN),
                            Integer.parseInt(dbResult.getString(Resource.USERAGE_COLUMN)),
                            dbResult.getString(Resource.USERGENDER_COLUMN));

                    bookedTicketsList.add(new BookedTickets(dbResult.getFloat(Resource.AMOUNT_COLUMN),
                            dbResult.getString(Resource.BOOKEDON_COLUMN), dbResult.getString(Resource.BOOKINGID_COLUMN),
                            dbResult.getString(Resource.CANCELLEDON_COLUMN),
                            dbResult.getString(Resource.FLIGHTID_COLUMN), dbResult.getString(Resource.ID_COLUMN),
                            dbResult.getString(Resource.ISCANCELLED_COLUMN),
                            dbResult.getString(Resource.TICKETID_COLUMN), passengerDetails,
                            dbResult.getString(Resource.FLIGHTCLASS_COLUMN)));
                } catch (SQLException sqlException) {
                    continue;
                }
            }
            return bookedTicketsList;
        } catch (SQLException sqlException) {
            System.out.println("--Couldn't fetch History--");
            return null;
        }
    }

    // Used to update the the Iscancelled column in booked list
    public boolean ticketCanceling(String bookingId, String seatNumber) {
        try {
            dbStatement.executeUpdate(SqlQuery.ticketCancelingQuery(bookingId, seatNumber.substring(1)));
            return true;
        } catch (SQLException sqlException) {
            return false;
        }

    }

    // Used search Airplanes based on flighId
    public List<Airlines> searchAirlinesByFlightName(String flightName) {
        List<Airlines> airlinesList = new ArrayList<Airlines>();

        try {

            dbResult = dbStatement.executeQuery(SqlQuery.searchByFlight(flightName));
            while (dbResult.next()) {
                airlinesList.add(new Airlines(dbResult.getString(Resource.ARRIVALCITY_COLUMN),
                        dbResult.getString(Resource.ARRIVALTIME_COLUMN), dbResult.getInt(Resource.COSTPERSEAT_COLUMN),
                        dbResult.getInt(Resource.CURRENTSEATSAVAILABLE_COLUMN),
                        dbResult.getString(Resource.DEPARTURECITY_COLUMN),
                        dbResult.getString(Resource.DEPARTURETIME_COLUMN),
                        dbResult.getString(Resource.FLIGHT_NAME_COLUMN),
                        dbResult.getString(Resource.FLIGHTCLASS_COLUMN),
                        dbResult.getString(Resource.FLIGHTNUMBER_COLUMN), dbResult.getInt(Resource.NOOFSEATS_COLUMN)));
            }
        } catch (SQLException sqlException) {
            System.out.println("--Couldn't fetch data Please try again later--");
        }
        return airlinesList;

    }

    // Used to return airlines object list based on city
    public List<Airlines> searchAirlinesByCity(String city) {
        List<Airlines> airlinesList = new ArrayList<Airlines>();
        try {

            dbResult = dbStatement.executeQuery(SqlQuery.searchByCity(city));
            while (dbResult.next()) {
                airlinesList.add(new Airlines(dbResult.getString(Resource.ARRIVALCITY_COLUMN),
                        dbResult.getString(Resource.ARRIVALTIME_COLUMN), dbResult.getInt(Resource.COSTPERSEAT_COLUMN),
                        dbResult.getInt(Resource.CURRENTSEATSAVAILABLE_COLUMN),
                        dbResult.getString(Resource.DEPARTURECITY_COLUMN),
                        dbResult.getString(Resource.DEPARTURETIME_COLUMN),
                        dbResult.getString(Resource.FLIGHT_NAME_COLUMN),
                        dbResult.getString(Resource.FLIGHTCLASS_COLUMN),
                        dbResult.getString(Resource.FLIGHTNUMBER_COLUMN), dbResult.getInt(Resource.NOOFSEATS_COLUMN)));
            }
        } catch (SQLException sqlException) {
            System.out.println("--Couldn't fetch data Please try again later--");
        }
        return airlinesList;

    }

    // Used to return airlines object list based on DepartureDate
    public List<Airlines> searchAirlinesByDate(String date) {
        List<Airlines> airlinesList = new ArrayList<Airlines>();
        try {
            System.out.println(SqlQuery.searchByDate(date));
            dbResult = dbStatement.executeQuery(SqlQuery.searchByDate(date));
            while (dbResult.next()) {
                airlinesList.add(new Airlines(dbResult.getString(Resource.ARRIVALCITY_COLUMN),
                        dbResult.getString(Resource.ARRIVALTIME_COLUMN), dbResult.getInt(Resource.COSTPERSEAT_COLUMN),
                        dbResult.getInt(Resource.CURRENTSEATSAVAILABLE_COLUMN),
                        dbResult.getString(Resource.DEPARTURECITY_COLUMN),
                        dbResult.getString(Resource.DEPARTURETIME_COLUMN),
                        dbResult.getString(Resource.FLIGHT_NAME_COLUMN),
                        dbResult.getString(Resource.FLIGHTCLASS_COLUMN),
                        dbResult.getString(Resource.FLIGHTNUMBER_COLUMN), dbResult.getInt(Resource.NOOFSEATS_COLUMN)));
            }
        } catch (SQLException sqlException) {
            System.out.println("--Couldn't fetch data Please try again later--");
        }
        return airlinesList;

    }

    // Used to get the nOofSeats in which is cancelled and not cancelled
    public String noOfSeats(String bookingId, String isCancelled) {
        try {

            dbResult = dbStatement.executeQuery(SqlQuery.noOfSeatsQuery(bookingId, isCancelled));
            dbResult.next();
            return dbResult.getString(Resource.COUNT_COLUMN);
        } catch (SQLException sqlException) {
            return Resource.EMPTY_DATA;
        }
    }

    public List<BookedTickets> bookedTicketTable() {

        try {
            dbResult = dbStatement.executeQuery(SqlQuery.selectQuery(Resource.BOOKED_TICKET_TABLE_NAME));
            List<BookedTickets> bookedTicketsList = new ArrayList<BookedTickets>();
            while (dbResult.next()) {
                try {
                    PassengerDetails passengerDetails = new PassengerDetails(
                            dbResult.getString(Resource.USERNAME_COLUMN),
                            Integer.parseInt(dbResult.getString(Resource.USERAGE_COLUMN)),
                            dbResult.getString(Resource.USERGENDER_COLUMN));

                    bookedTicketsList.add(new BookedTickets(dbResult.getFloat(Resource.AMOUNT_COLUMN),
                            dbResult.getString(Resource.BOOKEDON_COLUMN), dbResult.getString(Resource.BOOKINGID_COLUMN),
                            dbResult.getString(Resource.CANCELLEDON_COLUMN),
                            dbResult.getString(Resource.FLIGHTID_COLUMN), dbResult.getString(Resource.ID_COLUMN),
                            dbResult.getString(Resource.ISCANCELLED_COLUMN),
                            dbResult.getString(Resource.TICKETID_COLUMN), passengerDetails,
                            dbResult.getString(Resource.FLIGHTCLASS_COLUMN)));
                } catch (SQLException sqlException) {
                    continue;
                }

            }
            return bookedTicketsList;
        } catch (SQLException sqlException) {
            System.out.println("--Couldn't fetch Booking List--");
            return null;
        }
    }

    public List<String> distinctColumns(String tableName, String columnName) {
        List<String> resullt = new ArrayList<String>();
        try {

            dbResult = dbStatement.executeQuery(SqlQuery.distinctColumn(tableName, columnName));

            while (dbResult.next()) {
                try {
                    resullt.add(dbResult.getString(columnName));
                } catch (SQLException sqlException) {
                    continue;
                }

            }
        } catch (SQLException sqlException) {
            System.err.println(" Couldn't find DistinctColumn");
            resullt.add("");
        }
        return resullt;

    }

}
