package MainFunctionality;
import java.util.Scanner;
import java.io.*;
import Database.*;
import ExtraResources.*;
import DBTableClass.*;

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
        String RegMail="";
        while(true)
        {
            System.out.print("Enter your Mail:");
             RegMail=sc.nextLine();
             if(ExtraProcess.validateEmail(RegMail))
             break;
             else
             System.out.println("**Entered Email is not vaild**");

        }
        String Regpassword="";
       while(true)
       {
       Regpassword=new String(passwordConsole.readPassword("Enter your password:"));
       if(ExtraProcess.passwordValidate(Regpassword))
       break;
       else
       System.out.println("****Entered password is too short****");

       }
       
        System.out.println("Enter your Contact Number:");
        String RegPhonenumber=sc.nextLine();
       

      
        int tempcount=new DatabaseHandler().registerCheck(new ProfileDetails(null, RegName, RegDate, RegMail, Regpassword, RegPhonenumber));

        System.out.println("SuccessFully Registered And your User ID is: Usr"+String.valueOf(tempcount));
        
        ExtraProcess.currentUserDetails.setId("Usr"+String.valueOf(tempcount));
        ExtraProcess.currentUserDetails.setDob(RegDate);
        ExtraProcess.currentUserDetails.setEmail(RegMail);
        ExtraProcess.currentUserDetails.setName(RegName);
        ExtraProcess.currentUserDetails.setPassword(Regpassword);
        ExtraProcess.currentUserDetails.setPhonenumber(RegPhonenumber);




    }
    
}
