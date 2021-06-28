## Getting Started


Welcome to my FlightBookingSystem.The main code starts from App.java.
 
 App.java->MainUser->Views

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

## Database Structure
Table: userprofile
Columns:
          auto        int NOT-NULL Auto-increment Unique
          ID          varchar(100) primary key
          name        varchar(100) 
          DOB         varchar(100) 
          Email       varchar(100) 
          password    varchar(100) 
          phonenumber varchar(200)
        
Table: bookedtickets
Columns:
          ID              varchar(100) 
          Username        varchar(100) 
          Userage         varchar(100) 
          Usergender      varchar(100) 
          Flightid        varchar(100) 
          TicketId        varchar(100) 
          BookingId       varchar(100) 
          Flight          varchar(100) 
          DepartureTime   varchar(100) 
          Arrivaltime     varchar(100) 
          BookedOn        varchar(100) 
          CancelledOn     varchar(100) 
          IsCancelled     varchar(100) 
          flightclass     varchar(30)
          Amount          float
Table: journeyinfo
Columns:
        FlightNumber            varchar(20)  Primary key
        Flight                  varchar(50) 
        Departurecity           varchar(100) 
        Arrivalcity             varchar(100) 

Table: bookingavailablity
Columns:
        FlightNumber            varchar(20)
        Departuretime           varchar(100) 
        Arrivaltime             varchar(100) 
        flightclass             varchar(30) 
        noofseats               int 
        currentSeatsAvailable   int 
        Costperseat             bigint
        
## Dependency Management

The `JAVA DEPENDENCIES` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-pack/blob/master/release-notes/v0.9.0.md#work-with-jar-files-directly).
