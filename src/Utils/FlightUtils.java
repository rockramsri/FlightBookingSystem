package Utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.*;
import java.util.regex.Pattern;

import javax.mail.internet.InternetAddress;

import View.Ticket.*;

public class FlightUtils {

    private static FlightUtils flightUtils_instance = null;

    // Used for storing generatedCode for password Verification
    public StringBuffer randomCodeForPassword = new StringBuffer("");

    // To store Number of password Updated
    public int noOfPasswordChangeAllowed = 5;
    public int currentNoOfPasswordChanged = 0;

    public HashMap<Integer, String> getGenderString() {
        HashMap<Integer, String> genderMap = new HashMap<Integer, String>();

        /* Adding elements to HashMap */
        genderMap.put(1, "male");
        genderMap.put(2, "female");
        genderMap.put(3, "other");
        return genderMap;
    }

    // returns Single Instance of Flightutils class
    public static FlightUtils getInstance() {
        if (flightUtils_instance == null)
            flightUtils_instance = new FlightUtils();
        return flightUtils_instance;
    }

    public void utilCloser() {
        scanner.close();
    }

    // public static String randomCodeForPassword="";
    public static Scanner scanner = new Scanner(System.in);

    // Used to Clear the screen in the Terminal
    public void clearScreen() {
        System.out.print("\033[H\033[2J"); // ANSI Escape Code
        System.out.flush();
    }

    public boolean internetConnectiviityCheck() {
        try {
            URL url = new URL("http://www.google.com");
            URLConnection connection = url.openConnection();
            connection.connect();
            return true;
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public String getDepartureDate() {
        Date currentDate = new Date();
        StringBuffer departureDate = new StringBuffer("");
        while (true) {

            System.out.println("Enter your Departure Date(DD/MM/YYY):");
            departureDate.replace(0, departureDate.length(), getStringInput());
            String pattern = "dd/MM/yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            try {
                Date ticket_Date = simpleDateFormat.parse(departureDate.toString());
                if (currentDate.compareTo(ticket_Date) != 1) {
                    break;
                }
                System.out.println("The entered date is not Available");
            } catch (ParseException parseException) {
                System.out.println("--Please check your Entered date format--");
            }

        }
        return departureDate.toString();
    }

    public HashMap<String, String> getDepartureCityAndArrivalCity() {
        clearScreen();
        HashMap<String, String> departureArrivalCityMap = new HashMap<String, String>();
        departureArrivalCityMap.put(Resource.DEPARTURECITY_COLUMN, "");
        departureArrivalCityMap.put(Resource.ARRIVALCITY_COLUMN, "");

        ArrayList<String> cList = Resource.citiesList();
        int citynumber = 0; // for count of Cities available

        CommandLineTable cityTable = new CommandLineTable();
        cityTable.setHeaders("CODE", "CITY");
        for (String city : cList) {
            citynumber = citynumber + 1;
            cityTable.addRow(String.valueOf(citynumber), city);

        }
        cityTable.print();
        while (true) {
            int depcitychoice;
            System.out.println("Enter the  city of Departure (Corresponding Number):");
            depcitychoice = getIntegerInput();

            if (depcitychoice >= 0 && depcitychoice <= cList.size()) {
                departureArrivalCityMap.put(Resource.DEPARTURECITY_COLUMN, cList.get(depcitychoice - 1));
                break;
            }
        }

        while (true) {
            System.out.println("Enter the  city for Arrival (Corresponding Number)::");
            int arrCityChoice = getIntegerInput();

            if (arrCityChoice >= 0 && arrCityChoice <= cList.size()) {
                departureArrivalCityMap.put(Resource.ARRIVALCITY_COLUMN, cList.get(arrCityChoice - 1));

                break;
            }
        }
        return departureArrivalCityMap;
    }

    // Used to set the Mail sending process in separate Thread
    public void mailThreader(TicketInfo iTicketInfo, String ContentCode, String eMail) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 1, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (!internetConnectiviityCheck())
                    ;
                MailSender.mailSender(iTicketInfo, ContentCode, eMail);

            }
        });
        executor.shutdown();
    }

    // Used to return Radom number based on the min and max range
    public int sizeRandomizer(int min, int max) {

        int range = max - min + 1;

        return (int) (Math.random() * range) + min;

    }

    // for getting Standard Intger Input
    public int getIntegerInput() {
        int value = 0;

        while (true) {
            try {
                value = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entered Number is not valid.Please enter again");
                continue;
            }

        }
        return value;
    }

    // for getting Standard String Input
    public String getStringInput() {
        return scanner.nextLine();
    }

    // Used to return the current time
    public String dateTimeGetter() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formatDateTime = now.format(format);
        return formatDateTime;
    }

    // For User passwordValidation
    public boolean passwordValidate(String pword) {
        String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+.=])" + "(?=\\S+$).{8,20}$";
        if (Pattern.matches(regex, pword))
            return true;
        else
            return false;
    }

    // For User Email Vaildation
    public boolean validateEmail(String email) {
        boolean isValid = false;
        try {
            // Create InternetAddress object and validated the supplied
            // address which is this case is an email address.
            InternetAddress internetAddress = new InternetAddress(email, true); // strict
            internetAddress.validate();
            isValid = true;
        } catch (Exception e) {
            isValid = false;
        }
        return isValid;
    }

}
