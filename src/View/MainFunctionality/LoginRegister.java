package View.MainFunctionality;
import java.io.*;
import Database.*;
import Utils.*;
import Database.DBTableClass.*;

public class LoginRegister {

    FlightUtils flightUtils;
    DatabaseHandler databaseHandler;

    public LoginRegister() {
        flightUtils = FlightUtils.getInstance();
        databaseHandler = DatabaseHandler.getInstance();
    }

    // used to authenticate the user based on information stored in userprofile
    // table
    public boolean login() {
        final int TRY_LOGIN_AGAIN = 1;
        final int FORGOT_PASSWORD = 2;
        final int BACK = 3;
        Console passwordConsole = System.console();
        int forgotPasswordOption;

        while (true) {
            System.out.print("Enter your Mail Id or User ID:");
            String RegID = flightUtils.getStringInput();
            String Regpassword = new String(passwordConsole.readPassword("Enter your password:"));
            if (databaseHandler.loginCheck(RegID, Regpassword))
                break;
            else {
                System.out.println("1.Do you want to try again \n2.Forgot password \n3.Back");
                forgotPasswordOption = flightUtils.getIntegerInput();
                switch (forgotPasswordOption) {
                    case TRY_LOGIN_AGAIN:
                        continue;

                    case FORGOT_PASSWORD:
                        if (flightUtils.currentNoOfPasswordChanged < flightUtils.noOfPasswordChangeAllowed) {
                            Resource.currentUserDetails.forgotPassword();
                        }

                        else {
                            System.out.println("Your Limit of Password Changing is Exceeded,Please try again Later");
                        }
                        return false;
                    case BACK:
                        return false;
                    default:
                        System.out.println("Entered Wrong Option");

                }
            }
        }

        return true;
    }

    // Used to Add the data of User information as Registeration
    public boolean register() {
        Console passwordConsole = System.console();
        System.out.print("Enter your name:");
        String RegName = flightUtils.getStringInput();
        System.out.print("Enter your DOB:");
        String RegDate = flightUtils.getStringInput();
        String RegMail = "";
        while (true) {
            System.out.print("Enter your Mail:");
            RegMail = flightUtils.getStringInput();
            if (flightUtils.validateEmail(RegMail))
                break;
            else
                System.out.println("**Entered Email is not vaild**");

        }
        String Regpassword = "";
        while (true) {
            Regpassword = new String(passwordConsole.readPassword("Enter your password:"));
            if (flightUtils.passwordValidate(Regpassword))
                break;
            else
                System.out.println("****Entered password is too short****");

        }

        System.out.println("Enter your Contact Number:");
        String RegPhonenumber = flightUtils.getStringInput();

        int tempcount = databaseHandler
                .registerCheck(new ProfileDetails(null, RegName, RegDate, RegMail, Regpassword, RegPhonenumber));

        if (tempcount == 0)
            return false;
        System.out.println("SuccessFully Registered And your User ID is: Usr" + String.valueOf(tempcount));

        Resource.currentUserDetails.setId("Usr" + String.valueOf(tempcount));
        Resource.currentUserDetails.setDob(RegDate);
        Resource.currentUserDetails.setEmail(RegMail);
        Resource.currentUserDetails.setName(RegName);
        Resource.currentUserDetails.setPassword(Regpassword);
        Resource.currentUserDetails.setPhonenumber(RegPhonenumber);
        return true;

    }

}
