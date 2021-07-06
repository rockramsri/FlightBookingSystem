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
    FlightUtils flightUtils;

    private static DatabaseHandler databaseHandler_instance = null;

    public static DatabaseHandler getInstance() {
        if (databaseHandler_instance == null)
            databaseHandler_instance = new DatabaseHandler();
        return databaseHandler_instance;
    }

    public DatabaseHandler() {
        dbStatement = DatabaseLoader.databaseLoadCaller();
        flightUtils = FlightUtils.getInstance();
    }

    public void databaseCloser() {
        try {
            if (!dbResult.isClosed())
                dbResult.close();
            if (!dbStatement.isClosed())
                dbStatement.close();
        } catch (NullPointerException nullPointerException) {
        } catch (SQLException sqlException) {
        }

    }

    public void createTable() {
        for (String createQuery : Resource.tableCreationList()) {
            try {
                dbStatement.executeUpdate(createQuery);
            } catch (SQLException e) {
                continue;
            }

        }
    }

    // Used to Update the password of the user
    public void updatePassword(String password) {
        try {
            dbStatement.executeUpdate(SqlQuery.passwordUpdater(password));
        } catch (SQLException sqlException) {
            System.out.println("--Could not update password Please try again later--");
        }

    }

    // Used to validate the login info
    public boolean loginCheck(String userId, String password) {
        String regUrID = "";
        try {
            if (userId.contains("@")) {
                dbResult = dbStatement
                        .executeQuery(SqlQuery.recordExistMailQuery(Resource.User_INFO_TABLE_NAME, userId, password));
            } else {
                dbResult = dbStatement.executeQuery(
                        SqlQuery.recordExistByUserinfoQuery(Resource.User_INFO_TABLE_NAME, userId, password));
                regUrID = userId;
            }
            dbResult.next();
            if (dbResult.getInt(1) == 1) {
                if (regUrID.length() == 0) {
                    dbResult = dbStatement
                            .executeQuery(SqlQuery.getIdByMailQuery(Resource.User_INFO_TABLE_NAME, userId));
                    dbResult.next();
                    regUrID = dbResult.getString(Resource.ID_COLUMN);

                }
                Resource.currentUserDetails.setId(regUrID);

                dbResult = dbStatement.executeQuery(SqlQuery.getUserInfo(Resource.User_INFO_TABLE_NAME, regUrID));
                dbResult.next();
                Resource.currentUserDetails.setId(regUrID);
                Resource.currentUserDetails.setDob(dbResult.getString(Resource.DOB_COLUMN));
                Resource.currentUserDetails.setEmail(dbResult.getString(Resource.EMAIL_COLUMN));
                Resource.currentUserDetails.setName(dbResult.getString(Resource.NAME_COLUMN));
                Resource.currentUserDetails.setPassword(dbResult.getString(Resource.PASSWORD_COLUMN));
                Resource.currentUserDetails.setPhonenumber(dbResult.getString(Resource.PHONENUMBER_COLUMN));

                return true;
            } else
                return false;
        } catch (SQLException sqlException) {
            System.out.println("--Login issue in database--");
            return false;
        }
    }

    // Used to register the and add the data in the database
    public int registerCheck(ProfileDetails profileRegister) {
        try {
            dbStatement.executeUpdate(SqlQuery.userRecordInsertQuery(Resource.User_INFO_TABLE_NAME,
                    profileRegister.getName(), profileRegister.getDob(), profileRegister.getEmail(),
                    profileRegister.getPassword(), profileRegister.getPhonenumber()));
            dbStatement.executeUpdate(SqlQuery.uniqueKeySetter());
            dbResult = dbStatement.executeQuery(SqlQuery.uniqueKeyGetter());
            dbResult.next();
            return dbResult.getInt(Resource.AUTO_ID_COLUMN);
        } catch (SQLException sqlException) {
            System.out.println("--User Registration Failed--");
            return 0;
        }

    }

    // Used to return the List of airlines based on condition from the Database
    public List<Airlines> bookingList(String departureCity, String arrivalCity, String dateTicket, int nofSeats,
            String flightClass) {
        List<Airlines> airlinesList = new ArrayList<Airlines>();

        try {
            // System.out.println(SqlQuery.availableFlightquery(Resource.AIRLINE_TICKET_TABLE_NAME,Resource.FLIGHT_TICKET_TABLE_NAME,
            // Departurecity, Arrivalcity, Dateticket, NofSeats, flightClass));
            dbResult = dbStatement.executeQuery(SqlQuery.availableFlightDetailsquery(Resource.FLIGHT_TICKET_TABLE_NAME,
                    Resource.FLIGHT_BOOKING_AVAILABLITY_TICKET_TABLE_NAME, departureCity, arrivalCity, dateTicket,
                    nofSeats, flightClass));
            while (dbResult.next()) {
                airlinesList.add(new Airlines(dbResult.getString(Resource.ARRIVALCITY_COLUMN),
                        dbResult.getString(Resource.ARRIVALTIME_COLUMN), dbResult.getInt(Resource.COSTPERSEAT_COLUMN),
                        dbResult.getInt(Resource.CURRENTSEATSAVAILABLE_COLUMN),
                        dbResult.getString(Resource.DEPARTURECITY_COLUMN),
                        dbResult.getString(Resource.DEPARTURETIME_COLUMN), dbResult.getString(Resource.FLIGHT_COLUMN),
                        flightClass, dbResult.getString(Resource.FLIGHTNUMBER_COLUMN),
                        dbResult.getInt(Resource.NOOFSEATS_COLUMN)));
            }
        } catch (SQLException sqlException) {
            System.out.println("--Couldn't fetch data Please try again later--");
        }
        return airlinesList;

    }

    // Used to update the tickets in the airlines
    public int airlinesUpdater(String flightId, int noOfSeats, String sign) {
        try {
            dbStatement.executeUpdate(SqlQuery.updateFlightCurrentSeatsquery(
                    Resource.FLIGHT_BOOKING_AVAILABLITY_TICKET_TABLE_NAME, noOfSeats, flightId, sign));
            return 1;
        } catch (SQLException sqlException) {
            System.out.println("--Couldn't Update Cancelled or booked tickets--");
            return 0;
        }
    }

    // used to register the booked ticket in the Bookedtickets table in the database
    public void bookingRegister(BookedTickets bookedTickets) {
        try {
            dbStatement.executeUpdate(SqlQuery.insertBookedTicketsQuery(Resource.BOOKED_TICKET_TABLE_NAME,
                    bookedTickets.getUserId(), bookedTickets.getPassengerlist().getPassengerName(),
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
    public List<BookedTickets> getBookedList(String UserId, String isCancelled) {
        List<BookedTickets> bookedTicketsList = new ArrayList<BookedTickets>();

        try {

            dbResult = dbStatement.executeQuery(
                    SqlQuery.availableSummaryQuery(Resource.BOOKED_TICKET_TABLE_NAME, UserId, isCancelled));
            while (dbResult.next()) {
                try {
                    PassengerDetails passengerDetails = new PassengerDetails(
                            dbResult.getString(Resource.USERNAME_COLUMN),
                            Integer.parseInt(dbResult.getString(Resource.USERAGE_COLUMN)),
                            dbResult.getString(Resource.USERGENDER_COLUMN));

                    bookedTicketsList.add(new BookedTickets(dbResult.getFloat(Resource.AMOUNT_COLUMN),
                            dbResult.getString(Resource.BOOKEDON_COLUMN), dbResult.getString(Resource.BOOKINGID_COLUMN),
                            dbResult.getString(Resource.BOOKEDON_COLUMN), dbResult.getString(Resource.FLIGHTID_COLUMN),
                            dbResult.getString(Resource.ID_COLUMN), dbResult.getString(Resource.ISCANCELLED_COLUMN),
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
    public HashMap<String, String> depArrivalFlightNameGetter(String flightId) {
        HashMap<String, String> journeyInfoList = new HashMap<String, String>();
        String emptyData = "No Info";
        try {
            dbResult = dbStatement
                    .executeQuery(SqlQuery.depArrivalCityFlightNamequery(Resource.FLIGHT_TICKET_TABLE_NAME, flightId));
            dbResult.next();
            try {
                journeyInfoList.put("departurecity", dbResult.getString(Resource.DEPARTURECITY_COLUMN));
            } catch (SQLDataException sqlDataException) {
                journeyInfoList.put("departurecity", emptyData);
            }
            try {
                journeyInfoList.put("arrivalcity", dbResult.getString(Resource.ARRIVALCITY_COLUMN));
            } catch (SQLDataException sqlDataException) {
                journeyInfoList.put("arrivalcity", emptyData);
            }

            try {
                journeyInfoList.put("flightname", dbResult.getString(Resource.FLIGHT_COLUMN));
            } catch (SQLDataException sqlDataException) {
                journeyInfoList.put("flightname", emptyData);
            }

            dbResult = dbStatement.executeQuery(
                    SqlQuery.depArrivalTimequery(Resource.FLIGHT_BOOKING_AVAILABLITY_TICKET_TABLE_NAME, flightId));
            dbResult.next();
            try {
                journeyInfoList.put("departuretime", dbResult.getString(Resource.DEPARTURETIME_COLUMN));
            } catch (SQLDataException sqlDataException) {
                journeyInfoList.put("departuretime", emptyData);
            }
            try {
                journeyInfoList.put("arrivaltime", dbResult.getString(Resource.ARRIVALTIME_COLUMN));
            } catch (SQLDataException sqlDataException) {
                journeyInfoList.put("arrivaltime", emptyData);
            }

        } catch (SQLException sqlException) {
            System.out.println("--Couldn't fetch departure arrival  Info--");

            journeyInfoList.put("departurecity", emptyData);
            journeyInfoList.put("arrivalcity", emptyData);
            journeyInfoList.put("flightname", emptyData);
            journeyInfoList.put("departuretime", emptyData);
            journeyInfoList.put("arrivaltime", emptyData);

        }

        return journeyInfoList;
    }

    // Used to get bookedTickets list
    public List<BookedTickets> getBookedIdsBookingList(String bookingId) {
        try {

            dbResult = dbStatement.executeQuery(SqlQuery.ticketlistquery(Resource.BOOKED_TICKET_TABLE_NAME, bookingId));
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
    public boolean ticketCanceling(String flightId, String ticketId) {
        try {
            dbStatement.executeUpdate(
                    SqlQuery.ticketCancelingQuery(Resource.BOOKED_TICKET_TABLE_NAME, flightId, ticketId.substring(1)));
            return true;
        } catch (SQLException sqlException) {
            return false;
        }

    }

    // Used search Airplanes based on flighId
    public List<Airlines> searchAirlinesByFlightName(String flightName) {
        List<Airlines> airlinesList = new ArrayList<Airlines>();

        try {

            dbResult = dbStatement.executeQuery(SqlQuery.searchByflight(Resource.FLIGHT_TICKET_TABLE_NAME,
                    Resource.FLIGHT_BOOKING_AVAILABLITY_TICKET_TABLE_NAME, flightName));
            while (dbResult.next()) {
                airlinesList.add(new Airlines(dbResult.getString(Resource.ARRIVALCITY_COLUMN),
                        dbResult.getString(Resource.ARRIVALTIME_COLUMN), dbResult.getInt(Resource.COSTPERSEAT_COLUMN),
                        dbResult.getInt(Resource.CURRENTSEATSAVAILABLE_COLUMN),
                        dbResult.getString(Resource.DEPARTURECITY_COLUMN),
                        dbResult.getString(Resource.DEPARTURETIME_COLUMN), dbResult.getString(Resource.FLIGHT_COLUMN),
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

            dbResult = dbStatement.executeQuery(SqlQuery.searchBycity(Resource.FLIGHT_TICKET_TABLE_NAME,
                    Resource.FLIGHT_BOOKING_AVAILABLITY_TICKET_TABLE_NAME, city));
            while (dbResult.next()) {
                airlinesList.add(new Airlines(dbResult.getString(Resource.ARRIVALCITY_COLUMN),
                        dbResult.getString(Resource.ARRIVALTIME_COLUMN), dbResult.getInt(Resource.COSTPERSEAT_COLUMN),
                        dbResult.getInt(Resource.CURRENTSEATSAVAILABLE_COLUMN),
                        dbResult.getString(Resource.DEPARTURECITY_COLUMN),
                        dbResult.getString(Resource.DEPARTURETIME_COLUMN), dbResult.getString(Resource.FLIGHT_COLUMN),
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

            dbResult = dbStatement.executeQuery(SqlQuery.searchByDate(Resource.FLIGHT_TICKET_TABLE_NAME,
                    Resource.FLIGHT_BOOKING_AVAILABLITY_TICKET_TABLE_NAME, date));
            while (dbResult.next()) {
                airlinesList.add(new Airlines(dbResult.getString(Resource.ARRIVALCITY_COLUMN),
                        dbResult.getString(Resource.ARRIVALTIME_COLUMN), dbResult.getInt(Resource.COSTPERSEAT_COLUMN),
                        dbResult.getInt(Resource.CURRENTSEATSAVAILABLE_COLUMN),
                        dbResult.getString(Resource.DEPARTURECITY_COLUMN),
                        dbResult.getString(Resource.DEPARTURETIME_COLUMN), dbResult.getString(Resource.FLIGHT_COLUMN),
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

            dbResult = dbStatement
                    .executeQuery(SqlQuery.noOfSeatsQuery(Resource.BOOKED_TICKET_TABLE_NAME, bookingId, isCancelled));
            dbResult.next();
            return dbResult.getString(Resource.COUNT_COLUMN);
        } catch (SQLException sqlException) {
            return "No Info";
        }
    }

    public List<BookedTickets> fullBookedTicketStable() {

        try {
            dbResult = dbStatement.executeQuery(SqlQuery.selectquery(Resource.BOOKED_TICKET_TABLE_NAME));
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

    public List<String> distinctColumns(String tablename, String columnName) {
        List<String> resullt = new ArrayList<String>();
        try {

            dbResult = dbStatement.executeQuery(SqlQuery.distinctColumn(tablename, columnName));

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
