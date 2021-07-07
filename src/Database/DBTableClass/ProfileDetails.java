package Database.DBTableClass;

import Utils.*;
import Database.*;

public class ProfileDetails {
    private String id;
    private final String name;
    private final String dob;
    private final String email;
    private String password;
    private final String phonenumber;

    public ProfileDetails(String id, String name, String dob, String email, String password, String phonenumber) {
        this.dob = dob;
        this.email = email;
        this.name = name;
        this.id = id;
        this.password = password;
        this.phonenumber = phonenumber;

    }

    // For changing the user password
    public static boolean changeMyPassword(boolean forgotPassBool, ProfileDetails currentUserDetails) {

        String currentPassword = "";
        String newPassword;
        String confirmNewPassword;
        boolean success = false;

        if (!forgotPassBool) {
            currentPassword = new String(System.console().readPassword("Enter Your Current Password:"));
        }
        if (forgotPassBool || currentPassword.equals(currentUserDetails.getPassword())) {
            while (true) {
                while (true) {
                    newPassword = new String(System.console().readPassword("Enter your New password:"));
                    if (FlightUtils.getInstance().passwordValidate(newPassword)
                            && !newPassword.equals(currentUserDetails.getPassword()))
                        break;
                    else
                        System.out.println("****Entered password is too short or you already used this password****");

                }

                confirmNewPassword = new String(System.console().readPassword("Re-Enter Your New Password:"));
                if (confirmNewPassword.equals(newPassword)) {
                    success = true;
                    DatabaseHandler.getInstance().updatePassword(newPassword, currentUserDetails.getId());

                    currentUserDetails.setPassword(confirmNewPassword);
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
    public static void forgotPassword() {
        final int I_HAVE_RECOVERY_CODE = 1;
        final int I_DONT_HAVE_RECOVERY_CODE = 2;
        final int BACK = 3;

        System.out.print("Enter your Mail Id or User ID:");
        String registeredID = FlightUtils.getInstance().getStringInput();
        ProfileDetails profileDetails = DatabaseHandler.getInstance().getLoggedInUserInfo(registeredID, "");
        if (profileDetails != null) {
            int tryCount = 0;
            whileexit: while (tryCount <= 3) {

                System.out.println("Do You  Have an Recovery Code: \n1.Yes I have \n2.I Dont Have \n3.Back");
                int recoveryCodeAvailableOption = FlightUtils.getInstance().getIntegerInput();
                switch (recoveryCodeAvailableOption) {
                    case I_HAVE_RECOVERY_CODE:
                        recoveryCodeChecking(profileDetails);

                        break whileexit;
                    case I_DONT_HAVE_RECOVERY_CODE:
                        recoveryCodeGentrator(profileDetails);
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

    static int recoveryCodeGentrator(ProfileDetails currentProfileDetails) {
        int randomNumber = FlightUtils.getInstance().sizeRandomizer(1000, 1000000);
        FlightUtils.getInstance().randomCodeForPassword.replace(0,
                FlightUtils.getInstance().randomCodeForPassword.length(), String.valueOf(randomNumber));
        System.out.println("Recovery Code has been sent to Your Mail");
        if (!FlightUtils.getInstance().internetConnectiviityCheck())
            System.out.println("--You are not connected to the Internet,Kindly connect to your Internet--");
        FlightUtils.getInstance().mailThreader(null, "PP", currentProfileDetails.getEmail());
        return randomNumber;
    }

    static void recoveryCodeChecking(ProfileDetails profileDetails) {
        System.out.print("Enter the Recovery Code:");
        String code = FlightUtils.getInstance().getStringInput();
        if (FlightUtils.getInstance().randomCodeForPassword.length() != 0
                && code.equals(FlightUtils.getInstance().randomCodeForPassword.toString())) {
            changeMyPassword(true, profileDetails);

        }

    }

    public String getDob() {
        return dob;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
