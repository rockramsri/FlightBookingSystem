package View.MainUser;

import Database.DBTableClass.ProfileDetails;
import Utils.CommandLineTable;
import Utils.FlightUtils;
import View.Ticket.TicketInfo;

public class Functionality {

    FlightUtils flightUtils;
    private static Functionality functionality = null;

    public Functionality() {
        flightUtils = FlightUtils.getInstance();
    }

    // For Single Instance
    static public Functionality getInstance() {
        if (functionality == null)
            functionality = new Functionality();
        return functionality;
    }

    // For booking tickets
    public void book_Tickets(Member member,ProfileDetails currentProfileDetails) {
        member.userBookingTickets.requestTickets();
        boolean available = member.userBookingTickets.listingTickets();
        if (available) {
            TicketInfo ticketInfo = member.userBookingTickets.bookingRegister(currentProfileDetails);
            if (!flightUtils.internetConnectiviityCheck())
                System.out.println("--You are not connected to the Internet,Kindly connect to your Internet--");
            flightUtils.mailThreader(ticketInfo, "BC",currentProfileDetails.getEmail());
            // This static methond is to send mail for the Booking Confirmation
            CommandLineTable confirmationTable = new CommandLineTable();
            String finalPrint = "Your Booking Id is :" + ticketInfo.getBookingId();
            String finalTicketIds = "Your Tickets Are   :";
            for (String item : ticketInfo.getSeatNumbers()) {
                finalTicketIds += item + " ";
            }
            confirmationTable.addRow("------------------------------");
            confirmationTable.addRow(finalPrint);
            confirmationTable.addRow(finalTicketIds);
            confirmationTable.addRow("------------------------------");
            confirmationTable.print();
            System.out.println("****Your Confirmation Mail has been sent*****");
        }

    }

    // For Ticket Cancellation
    public void ticket_Cancellation(Member member,ProfileDetails currentProfileDetails) {
        TicketInfo ticketInfo = member.refundCancel.listOfBookings(currentProfileDetails); // booked ticketInformation is stored in this
                                                                      // ticketInfo Object
        if (ticketInfo != null) {
            if (!flightUtils.internetConnectiviityCheck())
                System.out.println("--You are not connected to the Internet,Kindly connec to your Internet--");
            flightUtils.mailThreader(ticketInfo, "CC",currentProfileDetails.getEmail());
            // MailSender.bookingRefundMail(ticketInfo,"CC");
            CommandLineTable confirmationTable = new CommandLineTable();
            String finalPrint = "Your Booking Id is         :" + ticketInfo.getBookingId();
            String finalTicketIds = "Your Cancelled Tickets Are   :";
            for (String item : ticketInfo.getSeatNumbers()) {
                finalTicketIds += item + " ";
            }
            confirmationTable.addRow("------------------------------");
            confirmationTable.addRow(finalPrint);
            confirmationTable.addRow(finalTicketIds);
            confirmationTable.addRow("------------------------------");
            confirmationTable.print();
            System.out.println("Your Cancellation Mail has been sent");
        }

    }

    // For Searching available Flights
    public void search(Non_Member non_member) {
        final int SEARCH_BY_CITY = 1;
        final int SEARCH_BY_FLIGHT = 2;
        final int SEARCH_BY_DATE = 3;
        final int SEARCH_SPECIFIC = 4;

        while (true) {

            int internalOption = 0;
            System.out.println("1.Search by City");
            System.out.println("2.Search by Flights");
            System.out.println("3.Search by Date");
            System.out.println("4.Search Specific");

            internalOption = flightUtils.getIntegerInput();

            switch (internalOption) {
                case SEARCH_BY_CITY:
                    non_member.searchingTicket.searchByCity();
                    break;
                case SEARCH_BY_FLIGHT:
                    non_member.searchingTicket.searchByFlight();
                    break;
                case SEARCH_BY_DATE:
                    non_member.searchingTicket.searchByDate();
                    break;
                case SEARCH_SPECIFIC:
                    non_member.searchingTicket.seachBySpecific();
                    break;
                default:
                    System.out.println("You have entered a wrong option");
            }

            System.out.println("1.Do you want to Search ");
            System.out.println("2.Back");
            int searchOption = flightUtils.getIntegerInput();

            if (searchOption == 2)
                break;
        }
    }

    // For viewing history of both booking and Cancelation
    public void my_Transaction(Member member,ProfileDetails currentUserDetails) {
        inner: while (true) {
            System.out.println("1.Booked Ticket History ");
            System.out.println("2.Ticket Cancellation History ");

            System.out.println("3.Back");
            int searchOption = flightUtils.getIntegerInput();

            switch (searchOption) {
                case 1:
                    member.transactionHistory.bookedticketHistory(currentUserDetails);

                    break;
                case 2:
                    member.transactionHistory.ticketCancellingHistory(currentUserDetails);
                    break;
                case 3:
                    break inner;

            }
        }
    }

    // For changing Users password
    public void change_password(ProfileDetails currentProfileDetails) {
        if (flightUtils.currentNoOfPasswordChanged < flightUtils.noOfPasswordChangeAllowed)
            new ProfileDetails().changeMyPassword(false,currentProfileDetails);
        else {
            System.out.println("Your Limit of Password Changing is Exceeded,Please try again Later");
        }
    }

}
