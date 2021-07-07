package View.Ticket;

import Utils.FlightUtils;

/**
 * This class contains the UserInformations of the people bookings
 */
public class PassengerDetails {
    private String passengerName;
    private int passengerAge;
    private String passengerGender;

    public PassengerDetails() {
        FlightUtils flightUtils = FlightUtils.getInstance();

        System.out.println("Enter the Name:");

        passengerName = flightUtils.getStringInput();
        System.out.println("Enter the Age:");
        passengerAge = flightUtils.getIntegerInput();
        System.out.println("Select the Gender: \n 1.male \n 2.Female \n 3.others");

        int genderOption = flightUtils.getIntegerInput();
        passengerGender = flightUtils.getGenderString().get(genderOption);

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
