package View.MainFunctionality;

import java.io.*;
import Database.*;
import Utils.*;
import Database.DBTableClass.*;

public class LoginRegister {

    // used to authenticate the user based on information stored in userprofile
    // table
    public ProfileDetails login() {
        final int TRY_LOGIN_AGAIN = 1;
        final int FORGOT_PASSWORD = 2;
        final int BACK = 3;
        int forgotPasswordOption;
        ProfileDetails profileDetails = null;
        whilebreak: while (true) {
            System.out.print("Enter your Mail Id or User ID:");
            String registerID = FlightUtils.getInstance().getStringInput();
            String registerPassword = new String(System.console().readPassword("Enter your password:"));
            profileDetails = DatabaseHandler.getInstance().getLoggedInUserInfo(registerID, registerPassword);
            if (profileDetails != null)
                break;
            else {
                System.out.println("Entered Email id or password is wrong");
                System.out.println("1.Do you want to try again \n2.Forgot password \n3.Back");
                forgotPasswordOption = FlightUtils.getInstance().getIntegerInput();
                switch (forgotPasswordOption) {
                    case TRY_LOGIN_AGAIN:
                        continue;

                    case FORGOT_PASSWORD:
                        if (FlightUtils.getInstance().currentNoOfPasswordChanged < FlightUtils
                                .getInstance().noOfPasswordChangeAllowed) {
                            ProfileDetails.forgotPassword();
                        }

                        else {
                            System.out.println("Your Limit of Password Changing is Exceeded,Please try again Later");
                        }
                        break whilebreak;
                    case BACK:
                        break whilebreak;
                    default:
                        System.out.println("Entered Wrong Option");

                }
            }
        }

        return profileDetails;
    }

    // Used to Add the data of User information as Registeration
    public ProfileDetails registerUser() {
        Console passwordConsole = System.console();
        System.out.print("Enter your name:");
        String regName = FlightUtils.getInstance().getStringInput();
        System.out.print("Enter your DOB:");
        String regDate = FlightUtils.getInstance().getStringInput();
        String regMail = "";
        while (true) {
            System.out.print("Enter your Mail:");
            regMail = FlightUtils.getInstance().getStringInput();
            if (FlightUtils.getInstance().validateEmail(regMail))
                break;
            else
                System.out.println("**Entered Email is not vaild**");

        }
        StringBuffer regPassword = new StringBuffer("");
        while (true) {
            regPassword.replace(0, regPassword.length(),
                    new String(passwordConsole.readPassword("Enter your password:")));
            if (FlightUtils.getInstance().passwordValidate(regPassword.toString()))
                break;
            else
                System.out.println("****Entered password is too short****");

        }

        System.out.println("Enter your Contact Number:");
        String regPhonenumber = FlightUtils.getInstance().getStringInput();

        ProfileDetails profileDetails = new ProfileDetails(null, regName, regDate, regMail, regPassword.toString(),
                regPhonenumber);

        int userIdValue = DatabaseHandler.getInstance().getRegisteredUserId(profileDetails);

        if (userIdValue == 0)
            return null;
        System.out.println(
                "SuccessFully Registered And your User ID is: " + Resource.USER_ID_TAG + String.valueOf(userIdValue));

        profileDetails.setId(Resource.USER_ID_TAG + String.valueOf(userIdValue));

        return profileDetails;

    }

}
