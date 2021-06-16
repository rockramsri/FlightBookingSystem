import java.util.Scanner;
import java.io.*;

public class LoginRegister {

    
   
     Scanner sc;

    public LoginRegister() throws  IOException
    {
     sc=new Scanner(System.in); 
    }
   
    protected void finalize() 
    {
       sc.close();
    }

    //used to authenticate the user based on information stored in userprofile table
    public boolean login() throws Exception
    {  
        Console passwordConsole  = System.console();
      
        System.out.print("Enter your Mail Id or User ID:");
        String RegID=sc.nextLine();
        String Regpassword=new String(passwordConsole.readPassword("Enter your password:"));
       return new DatabaseHandler().loginCheck(RegID, Regpassword);
    }

    //Used to Add the data of User information as Registeration
    public void register() throws Exception
    {   
        Console passwordConsole  = System.console();
        System.out.print("Enter your name:");
        String RegName=sc.nextLine();
        System.out.print("Enter your DOB:");
        String RegDate=sc.nextLine();
        
        System.out.print("Enter your Mail:");
        String RegMail=sc.nextLine();
       
        String Regpassword=new String(passwordConsole.readPassword("Enter your password:"));
        System.out.println("Enter your Contact Number:");
        String RegPhonenumber=sc.nextLine();
        ExtraProcess.passwordHolder=Regpassword;

      
        int tempcount=new DatabaseHandler().registerCheck(new ProfileDetails(null, RegName, RegDate, RegMail, Regpassword, RegPhonenumber));

        System.out.println("SuccessFully Registered And your User ID is: Usr"+String.valueOf(tempcount));
        
        



    }
    
}
