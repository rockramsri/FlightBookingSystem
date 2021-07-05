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

    static public Functionality getInstance() {
        if (functionality == null)
            functionality = new Functionality();
        return functionality;
    }

    public void book_Tickets(Member member) {
        member.userBookingTickets.requestTickets();
        boolean available = member.userBookingTickets.listingTickets();
        if (available) {
            TicketInfo ticketInfo = member.userBookingTickets.bookingRegister();
            if (!flightUtils.internetConnectiviityCheck())
                System.out.println("--You are not connected to the Internet,Kindly connect to your Internet--");
            flightUtils.mailThreader(ticketInfo, "BC");
            // This static methond is to send mail for the Booking Confirmation
            CommandLineTable confirmationTable = new CommandLineTable();
            String finalPrint = "Your Booking Id is :" + ticketInfo.getOrderId();
            String finalTicketIds = "Your Tickets Are   :";
            for (String item : ticketInfo.getTicketIds()) {
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

    public void ticket_Cancellation(Member member) {
        TicketInfo ticketInfo = member.refundCancel.listOfBookings(); // booked ticketInformation is stored in this
                                                                      // ticketInfo Object
        if (ticketInfo != null) {
            if (!flightUtils.internetConnectiviityCheck())
                System.out.println("--You are not connected to the Internet,Kindly connec to your Internet--");
            flightUtils.mailThreader(ticketInfo, "CC");
            // MailSender.bookingRefundMail(ticketInfo,"CC");
            CommandLineTable confirmationTable = new CommandLineTable();
            String finalPrint = "Your Booking Id is         :" + ticketInfo.getOrderId();
            String finalTicketIds = "Your Cancelled Tickets Are   :";
            for (String item : ticketInfo.getTicketIds()) {
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

    public void my_Transaction(Member member) {
        inner: while (true) {
            System.out.println("1.Booked Ticket History ");
            System.out.println("2.Ticket Cancellation History ");

            System.out.println("3.Back");
            int searchOption = flightUtils.getIntegerInput();

            switch (searchOption) {
                case 1:
                    member.transactionHistory.bookedticketHistory();

                    break;
                case 2:
                    member.transactionHistory.ticketCancellingHistory();
                    break;
                case 3:
                    break inner;

            }
        }
    }

    public void change_password() {
        if (flightUtils.currentNoOfPasswordChanged < flightUtils.noOfPasswordChangeAllowed)
            new ProfileDetails().changeMypassword(false);
        else {
            System.out.println("Your Limit of Password Changing is Exceeded,Please try again Later");
        }
    }

}
