import java.util.Scanner;

public class ProfileDetails {
    private String id;
    private String name;
    private String dob;
    private String email;
    private String password;
    private String phonenumber;
    private  Scanner UserScanner;

    public ProfileDetails(String id,String name,String dob,String email,String password,String phonenumber)
    {
        this.dob = dob;
        this.email = email;
        this.name = name;
        this.id = id;
        this.password = password;
        this.phonenumber = phonenumber;
        UserScanner=new Scanner(System.in);
    }
    public ProfileDetails()
    {
        UserScanner=new Scanner(System.in);
    }
    public void finalize()
    {
        UserScanner.close();
    }
    
    public boolean changeMypassword(boolean fobool) throws Exception
    {   
          String currentPassword="";
          String newPassword="";
          String confirmNewPassword="";
          boolean success=false;
          while(true)
          {
              if(!fobool)
              {
                System.out.println("Enter Your Current Password:");
                currentPassword=UserScanner.nextLine();
              }
              if(fobool || currentPassword.equals(ExtraProcess.passwordHolder))
                {
                    while(true)
                    {
                       System.out.println("Enter Your New Password:");
                       newPassword=UserScanner.nextLine();
                       System.out.println("Re-Enter Your New Password:");
                       confirmNewPassword=UserScanner.nextLine();
                       if(confirmNewPassword.equals(newPassword))
                       {
                          success=true;
                          new DatabaseHandler().updatePassword(newPassword);
                         
                          ExtraProcess.passwordHolder=confirmNewPassword;
                          break;
                       }
                       else
                       {
                        System.out.println("*****Entered password Doesn't match****");
                       } 
                     }
                     break;
                }
            else{
                  System.out.println("*****Entered password is wrong Please try again later****");
                  break;
                  
                }
                 

            }
      
        return success;
          
    }


    
    public void forgotPassword() throws Exception
    { 
      

       
        System.out.print("Enter your Mail Id or User ID:");
        String RegID=UserScanner.nextLine();
        if(new DatabaseHandler().loginCheck(RegID,""))
        {
            int randomNumber=ExtraProcess.sizeRandomizer(1000, 1000000);
            ExtraProcess.randomCodeForPassword=String.valueOf(randomNumber);
            System.out.println("Sending the recovery code to Your Mail........");
            MailSender.Bookingrefundmail(null, "PP");
           
            System.out.print("Enter the Recovery Code:");
            String code=UserScanner.nextLine();
            if(code.equals(String.valueOf(randomNumber)))
            {
               if(changeMypassword(true))
               {
                System.out.println("***Password changed Successfully***");
               }
            }
        }
        else
        {
            System.out.println("Entered Email Doesnt Exist");
        }

        
       
    }
    
    public String getDob() {
        return dob;
    }
    public void setDob(String dob) {
        this.dob = dob;
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
    public void setEmail(String email) {
        this.email = email;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
  public void setPhonenumber(String phonenumber) {
      this.phonenumber = phonenumber;
  }
    
}
