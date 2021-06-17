
import java.util.*;
/* This is the starting of the program
  This is is an application used for for FlightBooking
  SeatAllocate.txt is used for storing Seat numbers
  Userlog.txt is used for storing current user id
*/

public class App {
    static Scanner inputScanner=new Scanner(System.in);
    
    public static void main(String[] args) throws Exception {
       
        TicketInfo ticketInfo;
        SeatsAllocate.initiator();
        SeatsAllocate.seatUpdater(); //  To check whether the text file for storing seats Seatallocation.txt contains the seatallocation object "
       
     
        exitprogram:
    while(true)
    {   
        
        System.out.println("***************FLIGHT TICKET BOOKING PORTAL********************");
        System.out.println("1.New User?");
        System.out.println("2.Already have an account?");
        System.out.println("3.Search");
        System.out.println("4.Exit");
        int choice=Integer.parseInt( inputScanner.nextLine());
        if(choice==1)
        {
            Member member=new Member();
            member.loginRegister.register(); //navigate to the register methond of loginRegister class in Member class
            outer:
           while(true)
           {   
              System.out.println("1.Book Tickets");
              System.out.println("2.Ticket Cancellation");
              System.out.println("3.Search");
              System.out.println("4.My Transaction");
              System.out.println("5.Change My Password");
              System.out.println("6.Logout");
              System.out.println("7.Exit");
        
        
              int option=Integer.parseInt( inputScanner.nextLine());
              switch(option)
                 {  


                     case 1:member.userBookingTickets.requestTickets();
                           boolean available=member.userBookingTickets.listingTickets();
                          if(available)
                             {
                                ticketInfo=member.userBookingTickets.bookingRegister();
                   
                                  MailSender.bookingRefundMail(ticketInfo,"BC");        //This static methond is to send mail for the Booking Confirmation
                                  CommandLineTable confirmationTable=new CommandLineTable();
                                  confirmationTable.setShowVerticalLines(true);
                                  String finalPrint="Your Booking Id is :"+ticketInfo.getOrderId();
                                  String finalTicketIds="Your Tickets Are   :";
                                  for(String item:ticketInfo.getTicketIds())
                                     {
                                        finalTicketIds+=item+" ";
                                      }
                                confirmationTable.addRow("------------------------------");
                            confirmationTable.addRow(finalPrint);
                            confirmationTable.addRow(finalTicketIds);
                            confirmationTable.addRow("------------------------------");
                            confirmationTable.print();
                            System.out.println("****Your Confirmation Mail has been sent*****");
                            }
                  
                 
                         break;
                    case 2: ticketInfo=member.refundCancel.listOfBookings();     // booked ticketInformation is stored in this ticketInfo Object
                        if(ticketInfo!=null)
                        {
                            MailSender.bookingRefundMail(ticketInfo,"CC");
                            CommandLineTable confirmationTable=new CommandLineTable();
                            confirmationTable.setShowVerticalLines(true);
                            String finalPrint="Your Booking Id is         :"+ticketInfo.getOrderId();
                            String finalTicketIds="Your Cancelled Tickets Are   :";
                            for(String item:ticketInfo.getTicketIds())
                                {
                                    finalTicketIds+=item+" ";
                                }
                            confirmationTable.addRow("------------------------------");
                            confirmationTable.addRow(finalPrint);
                            confirmationTable.addRow(finalTicketIds);
                            confirmationTable.addRow("------------------------------");
                            confirmationTable.print();
                            System.out.println("Your Cancellation Mail has been sent");
                        } 
         
                    
                            break;

            case 3:
                    while(true)
                        {
         
                          int internalOption=0;
                          System.out.println("1.Search by City");
                          System.out.println("2.Search by Flighhts");
                          System.out.println("3.Search by Date");
                          System.out.println("3.Search Specific");

                          internalOption=Integer.parseInt( inputScanner.nextLine());
                          switch(internalOption)
                          {
                                case 1:member.searchingTicket.searchByCity();
                                    break;
                                case 2:member.searchingTicket.searchByFlight();
                                    break;
                                case 3:member.searchingTicket.searchByDate();
                                    break;
                                case 4:member.searchingTicket.seachBySpecific();
                                    break;
                                default:System.out.println("You have entered a wrong option");
                          }
  
                          System.out.println("1.Do you want to Search ");
                          System.out.println("2.Back");
                           int searchOption=Integer.parseInt( inputScanner.nextLine());
                           if(searchOption==2)
                           break;
                        }
                        break;
            case 4:inner:
                while(true)
                    {
                        System.out.println("1.Booked Ticket History ");
                        System.out.println("2.Ticket Cancellation History ");

                        System.out.println("3.Exit");
                        int searchOption=Integer.parseInt( inputScanner.nextLine());
                        switch(searchOption)
                            {
                            case 1:new TransactionHistory().bookedticketHistory();
  
                                    break;
                            case 2: new TransactionHistory().ticketCancellingHistory();
                                    break;
                            case 3:break inner;
                     
                  
                            }
                    }
                    break;
            case 5:new ProfileDetails().changeMypassword(false);
                    break;
            case 6:
                    break outer;
            case 7:break exitprogram;
            
        
         
                    
            default:System.out.println("Wrong Choice Please enter Agian");
                    break;
                   
           
        }
        
    } 

            
        }
        else if(choice==2)
          {
            Member member=new Member();
            if(member.loginRegister.login())
            {
                outer:
                while(true)
                {   
                    System.out.println("1.Book Tickets");
                    System.out.println("2.Ticket Cancellation");
                    System.out.println("3.Search");
                    System.out.println("4.My Transaction");
                    System.out.println("5.Change My password");

                    System.out.println("6.Logout");
                    System.out.println("7.Exit");
                   
                    int option=Integer.parseInt( inputScanner.nextLine());
                    switch(option)
                    {  
            
            
                        case 1:member.userBookingTickets.requestTickets();
                              boolean available=member.userBookingTickets.listingTickets();
                             if(available)
                                 {
                                  ticketInfo=member.userBookingTickets.bookingRegister();
                   
                                   MailSender.bookingRefundMail(ticketInfo,"BC");
                                   CommandLineTable confirmationTable=new CommandLineTable();
                                   confirmationTable.setShowVerticalLines(true);
                                   String finalPrint="Your Booking Id is :"+ticketInfo.getOrderId();
                                   String finalTicketIds="Your Tickets Are   :";
                                   for(String item:ticketInfo.getTicketIds())
                                        {
                                            finalTicketIds+=item+" ";
                                        }
                                    confirmationTable.addRow("------------------------------");
                                    confirmationTable.addRow(finalPrint);
                                    confirmationTable.addRow(finalTicketIds);
                                    confirmationTable.addRow("------------------------------");
                                    confirmationTable.print();
                                    System.out.println("Your Confirmation Mail has been sent");
                                  }
               
                 
                                    break;
            case 2: ticketInfo=member.refundCancel.listOfBookings();
                    if(ticketInfo!=null)
                        {
                            MailSender.bookingRefundMail(ticketInfo,"CC");
                            CommandLineTable confirmationTable=new CommandLineTable();
                            confirmationTable.setShowVerticalLines(true);
                            String finalPrint="Your Booking Id is          :"+ticketInfo.getOrderId();
                            String finalTicketIds="Your Cancelled Tickets Are   :";
                            for(String item:ticketInfo.getTicketIds())
                                {
                                    finalTicketIds+=item+" ";
                                }
                            confirmationTable.addRow("------------------------------");
                            confirmationTable.addRow(finalPrint);
                            confirmationTable.addRow(finalTicketIds);
                            confirmationTable.addRow("------------------------------");
                            confirmationTable.print();
                            System.out.println("Your Cancellation Mail has been sent");
                        } 
         
                    
                    break;
            
            case 3: 
                        while(true)
                        {
                            int internalOption=0;
                            System.out.println("1.Search by City");
                            System.out.println("2.Search by Flighhts");
                            System.out.println("3.Search by Date");
                            System.out.println("4.Search Specific");
                                      
                            internalOption=Integer.parseInt( inputScanner.nextLine());
                            switch(internalOption)
                                {
                                    case 1:member.searchingTicket.searchByCity();
                                            break;
                                    case 2:member.searchingTicket.searchByFlight();
                                            break;
                                    case 3:member.searchingTicket.searchByDate();
                                            break;
                                    case 4:member.searchingTicket.seachBySpecific();
                                            break;
                                    default:System.out.println("You have entered a wrong option");
                                }
              
                          
                            System.out.println("1.Do you want to Search again");
                            System.out.println("2.Back");
                            int searchOption=Integer.parseInt(inputScanner.nextLine());
                            if(searchOption==2)
                            break;
                        }
                                break;
                case 4:inner:
                                while(true)
                                    {
                                        System.out.println("1.Booked Ticket History ");
                                        System.out.println("2.Ticket Cancellation History ");
                    
                                        System.out.println("3.Back");
                                        int searchOption=Integer.parseInt( inputScanner.nextLine());
                                        switch(searchOption)
                                            {
                                                case 1:new TransactionHistory().bookedticketHistory();
                                                        break;
                                                case 2: new TransactionHistory().ticketCancellingHistory();
                                                        break;
                                                case 3:break inner;
                                            }
                                    }
                                    break;
            case 5:new ProfileDetails().changeMypassword(false);
                    break;
            case 6:break outer;
            case 7:break exitprogram;
                                
            default:System.out.println("Wrong Choice Please enter Agian");
                                break;
                               
                       
                    }
                    
                } 
               
            }
            else{
                System.out.println("***Email or password entered is wrong****");
                System.out.println("1.Forgot password");
                System.out.println("2.Wrong Email Entered");
                int foption=Integer.parseInt( inputScanner.nextLine());
                if(foption==1)
                {
                    new ProfileDetails().forgotPassword();
                }

            }
        }
        else if(choice==3)
        {
          Non_Member non_Member=new Non_Member();
          while(true)
          {           
            
           
                        int internalOption=0;
                        System.out.println("1.Search by City");
                        System.out.println("2.Search by Flights");
                        System.out.println("3.Search by Date");
                        System.out.println("4.Search Specific");
                        
                        internalOption = Integer.parseInt( inputScanner.nextLine());
                        
                        switch(internalOption)
                        {
                            case 1:non_Member.searchingTicket.searchByCity();
                                    break;
                            case 2:non_Member.searchingTicket.searchByFlight();
                                    break;
                            case 3:non_Member.searchingTicket.searchByDate();
                                   break;
                            case 4:non_Member.searchingTicket.seachBySpecific();
                                   break;
                            default:System.out.println("You have entered a wrong option");
                        }

                        
                        int searchoption=2;

            System.out.println("1.Do you want to Search ");
            System.out.println("2.Back:");
            searchoption=Integer.parseInt( inputScanner.nextLine());
            if(searchoption==2)
            break;
                   
                
           
          }


        }
        else{
             break;
        }
    }
  
   
            
        
    DatabaseLoader.connectionCloser();  //For closing database connection
   
    }
}
