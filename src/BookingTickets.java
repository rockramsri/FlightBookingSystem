import java.util.*;
import java.util.Date;
import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BookingTickets {
  private String UserId;
  private  String Dateticket;
   private Scanner ticketInfoScanner;
   private String Departurecity;
   private String Arrivalcity;
   private int NofSeats;
    private String flightClass;
    private int SeatPrice;
    private List<UserDetails> userList;
    private List<String> ticketList;
    private boolean ticketAvailable=false;
    Airlines selectedAirlines;

    //Getter and setters for data members
    public String getDate() {
        return Dateticket;
    }
    public void setDate(String date) {
        Dateticket = date;
    }
    public String getDeparturecity() {
        return Departurecity;
    }
    public void setDeparturecity(String departurecity) {
        Departurecity = departurecity;
    }
    public String getArrivalcity() {
        return Arrivalcity;
    }
    public void setArrivalcity(String arrivalcity) {
        Arrivalcity = arrivalcity;
    }
    public int getNofSeats() {
        return NofSeats;
    }
    public void setNofSeats(int nofSeats) {
        NofSeats = nofSeats;
    }
    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }
    public String getFlightClass()
    {
      return this.flightClass;
    }
    
    public BookingTickets()  //intializing values
    {    
      
     
   
    SeatPrice=0;
   
      UserId="";
      Dateticket="";
      Departurecity="";
      Arrivalcity="";
      NofSeats=0;
      flightClass="Business";
        ticketInfoScanner=new Scanner(System.in);
        userList=new ArrayList<UserDetails>();
        ticketList= new ArrayList<String>();
    }
    public void finalize() throws SQLException  //To close the Scanner
    {
      ticketInfoScanner.close();
      
    }
    public void requestTickets() throws ParseException   //Getting required input to book ticket
    {   
      ExtraProcess.clearScreen();
      
        ArrayList<String> cList=Resource.citiesList();
        int citynumber=0;         //Getting DepartureCity
        Date date1 = new Date();  
        while(true)
        {

       
        System.out.println("Enter your Departure Date(DD/MM/YYY):");
      Dateticket=ticketInfoScanner.nextLine();
      String pattern = "dd/MM/yyyy";
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
      
      Date date = simpleDateFormat.parse(Dateticket);
               if(date1.compareTo(date)!=1)
                 {
                  break;
                 }
                 System.out.println("The entered date is not Available");
                 
         }
        CommandLineTable cityTable=new CommandLineTable();
        cityTable.setShowVerticalLines(true);
        cityTable.setHeaders("CODE","CITY");
        for(String city:cList)
        {   citynumber=citynumber+1;
          cityTable.addRow(String.valueOf(citynumber),city);
           
        }
        cityTable.print();
        while(true)
        {
        System.out.println("Enter the  city of Departure (Corresponding Number):");
        int depcitychoice=Integer.parseInt(ticketInfoScanner.nextLine());
          if(depcitychoice>=0 && depcitychoice<=cList.size())
          {
            Departurecity=cList.get(depcitychoice-1);
            cList.remove(depcitychoice-1); 
            break;
          }
        }
       // ExtraProcess.clearscreen();
       
        while(true)
        {
        System.out.println("Enter the  city for Arrival (Corresponding Number)::");
        int arrCityChoice=Integer.parseInt(ticketInfoScanner.nextLine());
        
        
          if(arrCityChoice>=0 && arrCityChoice<=cList.size())
          {
            Arrivalcity=cList.get(arrCityChoice-1);
            cList.remove(arrCityChoice-1); 
            break;
          }
        }
        
        
     //   ExtraProcess.clearscreen();
        System.out.println("Enter Number of seats:");
        NofSeats=Integer.parseInt(ticketInfoScanner.nextLine());
        

        for(int i=1;i<=NofSeats;i++)
        {
          System.out.println("Enter the User"+String.valueOf(i)+" Details");
          userList.add(new UserDetails());
        }

        
        
      //  ExtraProcess.clearscreen();
        System.out.println("1.Economic:");
        System.out.println("2.Business:");
        int checker=Integer.parseInt(ticketInfoScanner.nextLine());
        System.out.println("\n");
        if(checker==1)
        {
          flightClass="Economic";
        }
        else
        {
          flightClass="Business";
        }

    }
     
    //Listing availabale tickets based on condition
   public boolean listingTickets() throws ClassNotFoundException, SQLException   
   {
      ExtraProcess.clearScreen();
      boolean available=false;
    
     int optionCount=0;
     CommandLineTable tablebook=new CommandLineTable();
     tablebook.setShowVerticalLines(true);
     List<Airlines> lAirlines=new DatabaseHandler().bookingList(Departurecity, Arrivalcity, Dateticket, NofSeats, flightClass);
     tablebook.setHeaders("  Code  ","   AirLines  ","   DepartureCity  ","       ArrivalCity      ","      DepartureTime      ","      ArrivalTime      ","      FlightClass      ","  Cost  ");
     
     for(Airlines aobject:lAirlines)
     {
       optionCount+=1;
       tablebook.addRow(String.valueOf(optionCount),aobject.getFlight(),aobject.getDepartureCity(),aobject.getArrivalCity(),aobject.getDepartureTime(),aobject.getArrivalTime(),aobject.getFlightClass(),"Rs."+String.valueOf(aobject.getCostPerSeat()));
     }
     if(optionCount==0)
     {
       ExtraProcess.clearScreen();
      System.out.println("********No Flights is available Now*******");
     }
     else{
       tablebook.print();
       available=true;
     System.out.println("Enter the Corresponding CODE for Booking:");
     int optionselection=ticketInfoScanner.nextInt();
     ticketAvailable=true;
      selectedAirlines=lAirlines.get(optionselection-1);
     SeatPrice=selectedAirlines.getCostPerSeat();

    new DatabaseHandler().airlinesUpdater(selectedAirlines.getFlightId(), NofSeats,"-");
    
     }

   return available;

   }    

   //Used to register the data in the database of the ticket booked
   public TicketInfo bookingRegister() throws Exception
   {
    if(!ticketAvailable)
    return null;
    FileInputStream fin=new FileInputStream(Resource.USERLOG_LOCATION);    
    int i=0;    
    UserId="";
    while((i=fin.read())!=-1){    
     UserId+=(char)i;    
    }    
    fin.close();
    String orderId="ord"+String.valueOf(ExtraProcess.sizeRandomizer(1000,9999));
    for(i=1;i<=NofSeats;i++)
    {
       
         float cost=(float) (userList.get(i-1).getUage()>12?SeatPrice:SeatPrice*(0.75));
   
        String cancelledOn="null";
        String bookedOn=ExtraProcess.dateTimeGetter();
        String isCancelled="no";
        BookedTickets bookedTickets=new BookedTickets(cost, selectedAirlines.getArrivalTime(), bookedOn, orderId, cancelledOn, selectedAirlines.getDepartureTime(), selectedAirlines.getFlight(), selectedAirlines.getFlightId(), UserId, isCancelled, "P"+String.valueOf(SeatsAllocate.seats.get(selectedAirlines.getFlightId()).get(0)), userList.get(i-1));
         new DatabaseHandler().bookingRegister(bookedTickets);
 
        ticketList.add("P"+String.valueOf(SeatsAllocate.seats.get(selectedAirlines.getFlightId()).get(0)));
        SeatsAllocate.seats.get(selectedAirlines.getFlightId()).remove(0);
    }
    //upto
    SeatsAllocate.seatStorer(SeatsAllocate.seats);
  System.out.println("**SENDING YOUR MAIL PLEASE WAIT............................");
  
  TicketInfo bookTicketInfo=new TicketInfo(selectedAirlines.getDepartureCity(), selectedAirlines.getArrivalCity(), NofSeats,selectedAirlines.getFlightClass(), new DatabaseHandler().getMailbyId(UserId), selectedAirlines.getDepartureTime(), selectedAirlines.getArrivalTime(), selectedAirlines.getFlight(), orderId, ticketList, userList);
    fin.close();
    return bookTicketInfo;
   }


  }
