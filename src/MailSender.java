import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

 //BC stands for booking confirmation message
  //CC stands for Cancelation confirmation message
public class MailSender  {          
  static String htmlCodeBooking="";
   static String htmlCodeCanceling="";                                                           
    public static void bookingRefundMail(TicketInfo iTicketInfo,String ContentCode) throws Exception       

    {                                                                                   
        
        Properties properties=new Properties();
        properties.setProperty("mail.smtp.auth", "true");            //For gmail Authentication is required
        properties.setProperty("mail.smtp.starttls.enable", "true"); //Enabling encrpytion
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");  //server Host name  which is gmail for this application
        properties.setProperty("mail.smtp.port", "587");             // port Number for sending mail
        String myAccount=Resource.MYACCOUNT_ID;
        String myPassword=Resource.MYACCOUNT_PASSWORD;
        Session session=Session.getInstance(properties, new Authenticator(){
           @Override
           protected PasswordAuthentication getPasswordAuthentication() {
               // TODO Auto-generated method stub
               return new PasswordAuthentication(myAccount, myPassword);
           } 
        });
        Message message=prepareMessage(session,myAccount,iTicketInfo,ContentCode);
        Transport.send(message);
      
    }
    public static Message prepareMessage(Session session,String Myemail,TicketInfo messageTicketinfo,String code) throws AddressException, MessagingException, ClassNotFoundException, SQLException, IOException
    {
        htmlCodeBooking="";
        Message message=new MimeMessage(session);
        message.setFrom(new InternetAddress(Myemail));
       
        
       
        if(code.equals("BC"))   // Mail properties for Booked tickets
        {   
              //here
              String ticketString="";
              for(int i=0;i<messageTicketinfo.getTicketIds().size();i++)
              {
                  ticketString+=messageTicketinfo.getTicketIds().get(i)+" ";
              }
              message.setRecipient(Message.RecipientType.TO,new InternetAddress(messageTicketinfo.getUseremail()));
            htmlCodeBooking="<!DOCTYPE html>  <html lang=\"en\"> <head>   <style>.container{ text-align: center; }  </style>  </head><body><div class=\"container\"> <h1> "+messageTicketinfo.getFlightName()+"</h1> <h2>successfully booked!!</h2> <h3>Order ID: "+messageTicketinfo.getOrderId()+"</h3> <h3>Order ID:"+ticketString+" <h3>Date: "+messageTicketinfo.getDepTime()+"</h3> <h3>Departure city: "+messageTicketinfo.getDeparturecity()+" , Arrival city: "+messageTicketinfo.getArrivalcity()+" </h3> <h3>Departure time:  "+messageTicketinfo.getDepTime()+" , Arrival time: "+messageTicketinfo.getArrTime()+" </h3> <h3>Total no of seats: "+messageTicketinfo.getNofSeats()+" , Class :"+messageTicketinfo.getFlightClass()+" </h3> </div></body> </html>";
            
            message.setSubject("FLIGHT TICKET BOOKING CONFIRMATION");
            message.setContent(htmlCodeBooking, "text/html");
        }
        else if(code.equals("CC"))       // Mail properties for Cancelled tickets
        {  String ticketString="";
        for(int i=0;i<messageTicketinfo.getTicketIds().size();i++)
        {
            ticketString+=messageTicketinfo.getTicketIds().get(i)+" ";
        }
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(messageTicketinfo.getUseremail()));
            htmlCodeCanceling="<!DOCTYPE html>  <html lang=\"en\"> <head>   <style>.container{ text-align: center; }  </style>  </head><body><div class=\"container\"> <h1> "+messageTicketinfo.getFlightName()+"</h1> <h2>Successfully cancelled!!</h2> <h3>Order ID: "+messageTicketinfo.getOrderId()+"</h3> <h3>Order ID:"+ticketString+" <h3>Date: "+messageTicketinfo.getDepTime()+"</h3> <h3>Departure city: "+messageTicketinfo.getDeparturecity()+" , Arrival city: "+messageTicketinfo.getArrivalcity()+" </h3> <h3>Departure time:  "+messageTicketinfo.getDepTime()+" , Arrival time: "+messageTicketinfo.getArrTime()+" </h3> <h3>Total no of seats: "+messageTicketinfo.getNofSeats()+" , Class :"+messageTicketinfo.getFlightClass()+" </h3> </div></body> </html>";
            
            message.setSubject("FLIGHT TICKET CANCELLED CONFIRMATION");
            message.setContent(htmlCodeCanceling, "text/html");
        }
        else if(code.equals("PP"))         // Mail properties for Recovery code for changing password
        {
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(new DatabaseHandler().getMailbyId(ExtraProcess.userIdgetter())));
            message.setSubject("Recovery Password");
            message.setText("Recovery Code:"+ExtraProcess.randomCodeForPassword);

        }
        
        return message;

    }
    
}
