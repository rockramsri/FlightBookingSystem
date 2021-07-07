package View.Ticket;

import Utils.FlightUtils;

/**
 * This class contains the UserInformations of the people bookings
 */
public class PassengerDetails {
    private final String passengerName;
    private final int passengerAge;
    private final String passengerGender;

    public PassengerDetails() {

        System.out.println("Enter the Name:");

        passengerName = FlightUtils.getInstance().getStringInput();
        System.out.println("Enter the Age:");
        passengerAge = FlightUtils.getInstance().getIntegerInput();
        System.out.println("Select the Gender: \n 1.Male \n 2.Female \n 3.Others");

        int genderOption = FlightUtils.getInstance().getIntegerInput();
        passengerGender = FlightUtils.getInstance().getGenderString().get(genderOption);

    }

    public PassengerDetails(String passengerName, int passengerAge, String passengerGender) {
        this.passengerGender = passengerGender;
        this.passengerAge = passengerAge;
        this.passengerName = passengerName;
    }

    public int getPassengerAge() {
        return passengerAge;
    }

    public String getPassengerGender() {
        return passengerGender;
    }

    public String getPassengerName() {
        return passengerName;
    }

}
