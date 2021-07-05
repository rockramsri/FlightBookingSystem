package View.Ticket;

import Utils.FlightUtils;
/**
 * This class contains the UserInformations of the people bookings
 */
public class UserDetails {
    private String Uname;
    private int Uage;
    private String Ugender;

    public UserDetails() {
        FlightUtils flightUtils = FlightUtils.getInstance();

        System.out.println("Enter the Name:");

        Uname = flightUtils.getStringInput();
        System.out.println("Enter the Age:");
        Uage = flightUtils.getIntegerInput();
        System.out.println("Select the Gender: \n 1.male \n 2.Female \n 3.others");

        int genderOption = flightUtils.getIntegerInput();
        Ugender = flightUtils.getGenederCal().get(genderOption);

    }

    public UserDetails(String uname, int uage, String ugender) {
        Ugender = ugender;
        Uage = uage;
        Uname = uname;
    }

    public int getUage() {
        return Uage;
    }

    public void setUage(int uage) {
        Uage = uage;
    }

    public void setUgender(String ugender) {
        Ugender = ugender;
    }

    public String getUgender() {
        return Ugender;
    }

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

}
