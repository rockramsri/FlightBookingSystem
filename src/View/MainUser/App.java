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
  static final int NEWUSER = 1;
  static final int LOGIN = 2;
  static final int SEARCH = 3;
  static final int EXIT = 4;
  static final int BOOKTICKETS = 1;
  static final int TICKETCANCELLATION = 2;
  static final int MYTRANSACTION = 4;
  static final int CHANGEMYPASSWORD = 5;
  static final int LOGOUT = 6;
  static final int FUNCTIONALITYEXIT = 7;

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
        case NEWUSER:
          if (newUser())
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
  public static boolean newUser() {
    Member member = new Member();
    ProfileDetails profileDetails = member.loginRegister.register(); // navigate to the register methond of
                                                                     // loginRegister class in Member class
    if (profileDetails != null)
      return performMemberFunction(member, profileDetails);
    else
      return false;

  }

  // This method is which returns signed in success or not
  public static boolean login() {
    Member member = new Member();
    ProfileDetails profileDetails = member.loginRegister.login();
    if (profileDetails != null) {
      return performMemberFunction(member, profileDetails);
    }
    return false;
  }

  // For searching Available Flights
  public static void search() {
    NonMember nonMember = new NonMember();
    nonMember.search();

  }

  public static boolean performMemberFunction(Member member, ProfileDetails currentUserDetails) {
    while (true) {
      System.out.println("1.Book Tickets");
      System.out.println("2.Ticket Cancellation");
      System.out.println("3.Search");
      System.out.println("4.My Transaction");
      System.out.println("5.Change My Password");
      System.out.println("6.Logout");
      System.out.println("7.Exit");
      int functionalityOption = flightUtils.getIntegerInput();
      switch (functionalityOption) {
        case BOOKTICKETS:
          member.bookTickets(currentUserDetails);
          break;
        case TICKETCANCELLATION:
          member.ticketCancellation(currentUserDetails);
          break;
        case SEARCH:
          member.search();
          break;
        case MYTRANSACTION:
          member.myTransaction(currentUserDetails);
          break;
        case CHANGEMYPASSWORD:
          member.changePassword(currentUserDetails);
          break;
        case LOGOUT:
          return false;
        case FUNCTIONALITYEXIT:
          return true;
        default:
          System.out.println("Entered Option is incorrect Please Enter");

      }
    }
  }

}
