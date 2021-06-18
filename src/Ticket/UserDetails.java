package Ticket;
import java.util.Scanner;
/**
 * This class contains the UserInformations of the people bookings
 */
public class UserDetails {
   private String Uname;
    private int Uage;
  private  int Ugender;
  private Scanner Userscanner;

    public UserDetails()
    {
      Userscanner=new Scanner(System.in);
      System.out.println("Enter the Name:");
      if(Userscanner.hasNextLine())
      Uname=Userscanner.nextLine();
      System.out.println("Enter the Age:");
     
      while(true)
      {
        try{
            Uage=Integer.parseInt(Userscanner.nextLine());
          break;
        }
        catch(NumberFormatException e )
        {
          System.out.println("*Entered number is not valid please enter again*");
        }
      
      }
      System.out.println("Select the Gender: \n 1.male \n 2.Female \n 3.others");
     
      while(true)
      {
        try{
            Ugender=Integer.parseInt(Userscanner.nextLine());
          break;
        }
        catch(NumberFormatException e )
        {
          System.out.println("*Entered number is not valid please enter again*");
        }
      
      }
   
      
    }
    public UserDetails(String uname,int uage,int ugender)
    {
        Ugender = ugender;
        Uage = uage;
        Uname = uname;
    }
    public void finalize()
    {
        Userscanner.close();

    }
    public int getUage() {
        return Uage;
    }
   public void setUage(int uage) {
       Uage = uage;
   }
 public void setUgender(int ugender) {
     Ugender = ugender;
 }
 public int getUgender() {
     return Ugender;
 }
    public String getUname() {
        return Uname;
    }  
    public void setUname(String uname) {
        Uname = uname;
    }


}
