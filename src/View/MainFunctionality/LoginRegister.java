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
    public ProfileDetails login() {
        final int TRY_LOGIN_AGAIN = 1;
        final int FORGOT_PASSWORD = 2;
        final int BACK = 3;
        Console passwordConsole = System.console();
        int forgotPasswordOption;
        ProfileDetails profileDetails;

        while (true) {
            System.out.print("Enter your Mail Id or User ID:");
            String regID = flightUtils.getStringInput();
            String regpassword = new String(passwordConsole.readPassword("Enter your password:"));
            profileDetails=databaseHandler.loginCheck(regID, regpassword);
            if(profileDetails!=null)
                break;
            else {
                System.out.println("1.Do you want to try again \n2.Forgot password \n3.Back");
                forgotPasswordOption = flightUtils.getIntegerInput();
                switch (forgotPasswordOption) {
                    case TRY_LOGIN_AGAIN:
                        continue;

                    case FORGOT_PASSWORD:
                        if (flightUtils.currentNoOfPasswordChanged < flightUtils.noOfPasswordChangeAllowed) {
                            new ProfileDetails().forgotPassword();
                        }

                        else {
                            System.out.println("Your Limit of Password Changing is Exceeded,Please try again Later");
                        }
                        return null;
                    case BACK:
                        return null;
                    default:
                        System.out.println("Entered Wrong Option");

                }
            }
        }

        return profileDetails;
    }

    // Used to Add the data of User information as Registeration
    public ProfileDetails register() {
        Console passwordConsole = System.console();
        System.out.print("Enter your name:");
        String regName = flightUtils.getStringInput();
        System.out.print("Enter your DOB:");
        String regDate = flightUtils.getStringInput();
        String regMail = "";
        while (true) {
            System.out.print("Enter your Mail:");
            regMail = flightUtils.getStringInput();
            if (flightUtils.validateEmail(regMail))
                break;
            else
                System.out.println("**Entered Email is not vaild**");

        }
        StringBuffer regPassword = new StringBuffer("");
        while (true) {
            regPassword.replace(0, regPassword.length(),
                    new String(passwordConsole.readPassword("Enter your password:")));
            if (flightUtils.passwordValidate(regPassword.toString()))
                break;
            else
                System.out.println("****Entered password is too short****");

        }

        System.out.println("Enter your Contact Number:");
        String regPhonenumber = flightUtils.getStringInput();
         
        ProfileDetails profileDetails=new ProfileDetails();
        profileDetails.setDob(regDate);
        profileDetails.setEmail(regMail);
        profileDetails.setName(regName);
        profileDetails.setPassword(regPassword.toString());
        profileDetails.setPhonenumber(regPhonenumber);
       /* Resource.currentUserDetails.setDob(regDate);
        Resource.currentUserDetails.setEmail(regMail);
        Resource.currentUserDetails.setName(regName);
        Resource.currentUserDetails.setPassword();
        Resource.currentUserDetails.setPhonenumber(); */

        int userIdValue = databaseHandler.registerCheck(
                new ProfileDetails(null, regName, regDate, regMail, regPassword.toString(), regPhonenumber));

        if (userIdValue == 0)
            return null;
        System.out.println("SuccessFully Registered And your User ID is: Usr" + String.valueOf(userIdValue));

        profileDetails.setId("Usr" + String.valueOf(userIdValue));

        return profileDetails;

    }

}
