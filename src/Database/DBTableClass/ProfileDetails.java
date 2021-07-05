package Database.DBTableClass;
import java.io.*;
import Utils.*;
import Database.*;

public class ProfileDetails {
    private String id;
    private String name;
    private String dob;
    private String email;
    private String password;
    private String phonenumber;
    Console passwordConsole;
    private FlightUtils flightUtils;
    private DatabaseHandler databaseHandler;

    public ProfileDetails(String id, String name, String dob, String email, String password, String phonenumber) {
        this.dob = dob;
        this.email = email;
        this.name = name;
        this.id = id;
        this.password = password;
        this.phonenumber = phonenumber;
        flightUtils = FlightUtils.getInstance();
        databaseHandler = DatabaseHandler.getInstance();
        passwordConsole = System.console();
    }

    public ProfileDetails() {
        flightUtils = FlightUtils.getInstance();
        passwordConsole = System.console();
        try {
            databaseHandler = DatabaseHandler.getInstance();
        } catch (Exception e) {

        }

    }

    // For changing the user password
    public boolean changeMypassword(boolean fobool) {
        String currentPassword = "";
        String newPassword = "";
        String confirmNewPassword = "";
        boolean success = false;

        if (!fobool) {
            System.out.println("Enter Your Current Password:");
            currentPassword = flightUtils.getStringInput();
        }
        if (fobool || currentPassword.equals(Resource.currentUserDetails.getPassword())) {
            while (true) {
                while (true) {
                    newPassword = new String(passwordConsole.readPassword("Enter your New password:"));
                    if (flightUtils.passwordValidate(newPassword)
                            && !newPassword.equals(Resource.currentUserDetails.getPassword()))
                        break;
                    else
                        System.out.println("****Entered password is too short or you already used this password****");

                }

                confirmNewPassword = newPassword = new String(
                        passwordConsole.readPassword("Re-Enter Your New Password:"));
                if (confirmNewPassword.equals(newPassword)) {
                    success = true;
                    databaseHandler.updatePassword(newPassword);

                    Resource.currentUserDetails.setPassword(confirmNewPassword);
                    System.out.println("*****Password has been changed successfully****");
                    break;
                } else {
                    System.out.println("*****Entered password Doesn't match****");
                }
            }

        } else {
            System.out.println("*****Entered password is wrong Please try again later****");

        }

        return success;

    }

    // Used to reset the forgotten password by send OTP to mail
    public void forgotPassword() {
        final int I_HAVE_RECOVERY_CODE = 1;
        final int I_DONT_HAVE_RECOVERY_CODE = 2;
        final int BACK = 3;

        System.out.print("Enter your Mail Id or User ID:");
        String RegID = flightUtils.getStringInput();
        if (databaseHandler.loginCheck(RegID, "")) {
            int tryCount = 0;
            whileexit: while (tryCount <= 3) {

                System.out.println("Do You  Have an Recovery Code: \n1.Yes I have \n2.I Dont Have \n3.Back");
                int recoveryCodeAvailableOption = flightUtils.getIntegerInput();
                switch (recoveryCodeAvailableOption) {
                    case I_HAVE_RECOVERY_CODE:
                        recoveryCodeChecking();

                        break whileexit;
                    case I_DONT_HAVE_RECOVERY_CODE:
                        recoveryCodegentrator();
                        tryCount = 2;
                        break;
                    case BACK:
                        break whileexit;

                    default:
                        System.out.println("YOU have entered Wrong Option");

                }
                tryCount += 1;
            }

        } else {
            System.out.println("Entered Email Doesnt Exist");
        }

    }

    int recoveryCodegentrator() {
        int randomNumber = flightUtils.sizeRandomizer(1000, 1000000);
        flightUtils.randomCodeForPassword = String.valueOf(randomNumber);
        System.out.println("Recovery Code has been sent to Your Mail");
        if (!flightUtils.internetConnectiviityCheck())
            System.out.println("--You are not connected to the Internet,Kindly connect to your Internet--");
        flightUtils.mailThreader(null, "PP");
        return randomNumber;
    }

    void recoveryCodeChecking() {
        System.out.print("Enter the Recovery Code:");
        String code = flightUtils.getStringInput();
        if (flightUtils.randomCodeForPassword.length() != 0 && code.equals(flightUtils.randomCodeForPassword)) {
            changeMypassword(true);

        }

    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

}
