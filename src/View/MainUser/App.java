package View.MainUser;

import View.Ticket.*;
import Utils.*;
import Database.*;
import Database.DBTableClass.ProfileDetails;

/* This is the starting of the program
  This is is an application used for for FlightBooking
  SeatAllocate.txt is used for storing Seat numbers
  Userlog.txt is used for storing current user id
*/

public class App {
  // static Scanner inputScanner=new Scanner(System.in);
  static FlightUtils flightUtils = FlightUtils.getInstance();
  static Functionality functionality = Functionality.getInstance();
  static final int NEW_USER = 1;
  static final int LOGIN = 2;
  static final int SEARCH = 3;
  static final int EXIT = 4;
  static final int BOOK_TICKETS = 1;
  static final int TICKET_CANCELLATION = 2;
  static final int MY_TRANSACTION = 4;
  static final int CHANGE_MY_PASSWORD = 5;
  static final int LOGOUT = 6;
  static final int FUNCTIONALITY_EXIT = 7;

  public static void main(String[] args) {

    DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
    databaseHandler.createTable();
    SeatsAllocate.initiator();
    SeatsAllocate.seatUpdater();

    exitprogram: while (true) {

      System.out.println("***************FLIGHT TICKET BOOKING PORTAL********************");
      System.out.println("1.New User?");
      System.out.println("2.Already have an account?");
      System.out.println("3.Search");
      System.out.println("4.Exit");
      int choice = flightUtils.getIntegerInput();

      switch (choice) {
        case NEW_USER:
          if (new_User())
            break exitprogram;
          break;
        case LOGIN:
          if (login())
            break exitprogram;
          break;
        case SEARCH:
          search();
          break;
        case EXIT:
          break exitprogram;

        default:
          System.out.println("Entered Option is incorrect Please Enter");
      }
    }
    databaseHandler.databaseCloser();

    DatabaseLoader.connectionCloser(); // For closing database connection

    flightUtils.utilCloser();

  }

  // Registration or Sign up
  public static boolean new_User() {
    Member member = new Member();
    ProfileDetails profileDetails = member.loginRegister.register(); // navigate to the register methond of
                                                                     // loginRegister class in Member class
    if (profileDetails != null)
      return functionality_Methond(member, profileDetails);
    else
      return false;

  }

  // This method is which returns signed in success or not
  public static boolean login() {
    Member member = new Member();
    ProfileDetails profileDetails = member.loginRegister.login();
    if (profileDetails != null) {
      return functionality_Methond(member, profileDetails);
    }
    return false;
  }

  // For searching Available Flights
  public static void search() {
    Non_Member non_Member = new Non_Member();
    functionality.search(non_Member);

  }

  public static boolean functionality_Methond(Member member, ProfileDetails currentUserDetails) {
    while (true) {
      System.out.println("1.Book Tickets");
      System.out.println("2.Ticket Cancellation");
      System.out.println("3.Search");
      System.out.println("4.My Transaction");
      System.out.println("5.Change My Password");
      System.out.println("6.Logout");
      System.out.println("7.Exit");
      int functionality_option = flightUtils.getIntegerInput();
      switch (functionality_option) {
        case BOOK_TICKETS:
          functionality.book_Tickets(member, currentUserDetails);
          break;
        case TICKET_CANCELLATION:
          functionality.ticket_Cancellation(member, currentUserDetails);
          break;
        case SEARCH:
          functionality.search(member);
          break;
        case MY_TRANSACTION:
          functionality.my_Transaction(member, currentUserDetails);
          break;
        case CHANGE_MY_PASSWORD:
          functionality.change_password(currentUserDetails);
          break;
        case LOGOUT:
          return false;
        case FUNCTIONALITY_EXIT:
          return true;
        default:
          System.out.println("Entered Option is incorrect Please Enter");

      }
    }
  }

}
