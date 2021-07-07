package View.MainUser;

import Database.DBTableClass.ProfileDetails;
import Utils.CommandLineTable;
import View.MainFunctionality.*;
import View.Ticket.TicketInfo;

/**
 * Non-Member class has only Search feature Memebr class has non-member class
 * feature and booking,Cancellation,Transaction Features
 */

public class Member extends NonMember {

    public LoginRegister loginRegister;
    public BookingTickets userBookingTickets;
    public RefundCancel refundCancel;
    public TransactionHistory transactionHistory;

    Member() {
        loginRegister = new LoginRegister();
        userBookingTickets = new BookingTickets();
        refundCancel = new RefundCancel();
        transactionHistory = new TransactionHistory();
    }

    // For changing Users password
    public void changePassword(ProfileDetails currentProfileDetails) {
        if (flightUtils.currentNoOfPasswordChanged < flightUtils.noOfPasswordChangeAllowed)
            new ProfileDetails().changeMyPassword(false, currentProfileDetails);
        else {
            System.out.println("Your Limit of Password Changing is Exceeded,Please try again Later");
        }
    }

    // For booking tickets
    public void bookTickets(ProfileDetails currentProfileDetails) {
        userBookingTickets.requestTickets();
        boolean available = userBookingTickets.listingTickets();
        if (available) {
            TicketInfo ticketInfo = userBookingTickets.bookingRegister(currentProfileDetails);
            if (!flightUtils.internetConnectiviityCheck())
                System.out.println("--You are not connected to the Internet,Kindly connect to your Internet--");
            flightUtils.mailThreader(ticketInfo, "BC", currentProfileDetails.getEmail());
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
    public void ticketCancellation(ProfileDetails currentProfileDetails) {
        TicketInfo ticketInfo = refundCancel.listOfBookings(currentProfileDetails); // booked ticketInformation
                                                                                    // is stored in this
        // ticketInfo Object
        if (ticketInfo != null) {
            if (!flightUtils.internetConnectiviityCheck())
                System.out.println("--You are not connected to the Internet,Kindly connec to your Internet--");
            flightUtils.mailThreader(ticketInfo, "CC", currentProfileDetails.getEmail());
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

    // For viewing history of both booking and Cancelation
    public void myTransaction(ProfileDetails currentUserDetails) {
        inner: while (true) {
            System.out.println("1.Booked Ticket History ");
            System.out.println("2.Ticket Cancellation History ");

            System.out.println("3.Back");
            int searchOption = flightUtils.getIntegerInput();

            switch (searchOption) {
                case 1:
                    transactionHistory.bookedTicketHistory(currentUserDetails);

                    break;
                case 2:
                    transactionHistory.ticketCancellingHistory(currentUserDetails);
                    break;
                case 3:
                    break inner;

            }
        }
    }

}
