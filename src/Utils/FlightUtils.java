package Utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.mail.internet.InternetAddress;

import View.Ticket.*;

public class FlightUtils {

    private static FlightUtils flightUtils_instance = null;

    public String randomCodeForPassword = "";

    public int noOfPasswordChangeAllowed = 5;
    public int currentNoOfPasswordChanged = 0;

    public HashMap<Integer, String> getGenederCal() {
        HashMap<Integer, String> genderMap = new HashMap<Integer, String>();

        /* Adding elements to HashMap */
        genderMap.put(1, "male");
        genderMap.put(2, "female");
        genderMap.put(3, "other");
        return genderMap;
    }

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

    public void mailThreader(TicketInfo iTicketInfo, String ContentCode) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 1, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (!internetConnectiviityCheck())
                    ;
                MailSender.bookingRefundMail(iTicketInfo, ContentCode);

            }
        });
        executor.shutdown();
    }

    // Used to return Radom number based on the min and max range
    public int sizeRandomizer(int min, int max) {

        int range = max - min + 1;

        return (int) (Math.random() * range) + min;

    }

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

    public boolean passwordValidate(String pword) {
        String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+.=])" + "(?=\\S+$).{8,20}$";
        if (Pattern.matches(regex, pword))
            return true;
        else
            return false;
    }

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
