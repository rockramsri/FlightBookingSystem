package View.Ticket;

import java.util.Properties;
import Utils.*;

import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//BC stands for booking confirmation message
//CC stands for Cancelation confirmation message

public class MailSender {
    static String htmlCodeBooking = "";
    static String htmlCodeCanceling = "";

    public static void mailSender(TicketInfo iTicketInfo, String contentCode)

    {

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true"); // For gmail Authentication is required
        properties.setProperty("mail.smtp.starttls.enable", "true"); // Enabling encrpytion
        properties.setProperty("mail.smtp.host", "smtp.gmail.com"); // server Host name which is gmail for this
                                                                    // application
        properties.setProperty("mail.smtp.port", "587"); // port Number for sending mail
        String myAccount = Resource.MYACCOUNT_ID;
        String myPassword = Resource.MYACCOUNT_PASSWORD;
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // TODO Auto-generated method stub
                return new PasswordAuthentication(myAccount, myPassword);
            }
        });
        try {
            Message message = prepareMessage(session, myAccount, iTicketInfo, contentCode);
            try {

                Transport.send(message);
            } catch (SendFailedException sendFailedException) {
                try {
                    Transport.send(message);
                } catch (SendFailedException resendFailedException) {
                    System.out.println("--Could not send the confirmation mail--");
                }
            }
        } catch (AuthenticationFailedException authenticationFailedException) {
            System.out.println("--Authecation failed,could not send mail--");
        } catch (MessagingException messagingException) {
            System.out.println("--Could not send Mail--");
        }

    }

    // Used for preparing Message for sending Email
    public static Message prepareMessage(Session session, String myEmail, TicketInfo messageTicketinfo, String code)
            throws MessagingException {
        FlightUtils flightUtils = FlightUtils.getInstance();
        htmlCodeBooking = "";
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myEmail));

        if (code.equals("BC")) // Mail properties for Booked tickets
        {
            // here
            String ticketString = "";
            for (int i = 0; i < messageTicketinfo.getSeatNumbers().size(); i++) {
                ticketString += messageTicketinfo.getSeatNumbers().get(i) + " ";
            }

            htmlCodeBooking = "<!DOCTYPE html>  <html lang=\"en\"> <head>   <style>.container{ text-align: center; }  </style>  </head><body><div class=\"container\"> <h1> "
                    + messageTicketinfo.getFlightName() + "</h1> <h2>successfully booked!!</h2> <h3>Order ID: "
                    + messageTicketinfo.getBookingId() + "</h3> <h3>Order ID:" + ticketString + " <h3>Date: "
                    + messageTicketinfo.getDepTime() + "</h3> <h3>Departure city: "
                    + messageTicketinfo.getDepartureCity() + " , Arrival city: " + messageTicketinfo.getArrivalCity()
                    + " </h3> <h3>Departure time:  " + messageTicketinfo.getDepTime() + " , Arrival time: "
                    + messageTicketinfo.getArrTime() + " </h3> <h3>Total no of seats: "
                    + messageTicketinfo.getNofSeats() + " , Class :" + messageTicketinfo.getFlightClass()
                    + " </h3> </div></body> </html>";

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(messageTicketinfo.getUserEmail()));
            message.setSubject("FLIGHT TICKET BOOKING CONFIRMATION");
            message.setContent(htmlCodeBooking, "text/html");
        } else if (code.equals("CC")) // Mail properties for Cancelled tickets
        {
            String ticketString = "";
            for (int i = 0; i < messageTicketinfo.getSeatNumbers().size(); i++) {
                ticketString += messageTicketinfo.getSeatNumbers().get(i) + " ";
            }

            htmlCodeCanceling = "<!DOCTYPE html>  <html lang=\"en\"> <head>   <style>.container{ text-align: center; }  </style>  </head><body><div class=\"container\"> <h1> "
                    + messageTicketinfo.getFlightName() + "</h1> <h2>Successfully cancelled!!</h2> <h3>Order ID: "
                    + messageTicketinfo.getBookingId() + "</h3> <h3>Order ID:" + ticketString + " <h3>Date: "
                    + messageTicketinfo.getDepTime() + "</h3> <h3>Departure city: "
                    + messageTicketinfo.getDepartureCity() + " , Arrival city: " + messageTicketinfo.getArrivalCity()
                    + " </h3> <h3>Departure time:  " + messageTicketinfo.getDepTime() + " , Arrival time: "
                    + messageTicketinfo.getArrTime() + " </h3> <h3>Total no of seats: "
                    + messageTicketinfo.getNofSeats() + " , Class :" + messageTicketinfo.getFlightClass()
                    + " </h3> </div></body> </html>";

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(messageTicketinfo.getUserEmail()));
            message.setSubject("FLIGHT TICKET CANCELLED CONFIRMATION");
            message.setContent(htmlCodeCanceling, "text/html");
        } else if (code.equals("PP")) // Mail properties for Recovery code for changing password
        {
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(Resource.currentUserDetails.getEmail()));
            message.setSubject("Recovery Password");
            message.setText("Recovery Code:" + flightUtils.randomCodeForPassword.toString());

        }

        return message;

    }

}
