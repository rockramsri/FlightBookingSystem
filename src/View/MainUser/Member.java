package View.MainUser;
import java.io.IOException;
import java.sql.*;
import View.MainFunctionality.*;

/**Non-Member class has only Search feature
 * Memebr class has  non-member class feature and booking,Cancellation Features 
 */
class Non_Member{
    
    public SearchingTicket searchingTicket;
        Non_Member()
                {
            searchingTicket=new SearchingTicket();
                    }

}
public class Member extends Non_Member {

       public LoginRegister loginRegister;
       public  BookingTickets userBookingTickets;
        public RefundCancel refundCancel;

        Member() throws ClassNotFoundException, SQLException, IOException
        {   
             loginRegister=new LoginRegister();
            userBookingTickets=new BookingTickets();
            refundCancel=new RefundCancel();

        }
}
