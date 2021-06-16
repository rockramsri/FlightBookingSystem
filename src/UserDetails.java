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
      Uage=Integer.parseInt(Userscanner.nextLine());
      System.out.println("Select the Gender: \n 1.male \n 2.Female \n 3.others");
      Ugender=Integer.parseInt(Userscanner.nextLine());
   
      
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
