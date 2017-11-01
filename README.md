# BiddingOblig Assignment 3. 

## Task 1

a) First one must run the application in the browser and place a test item in the auction. To do this log in with user "test and pw "test" and create a new product. To try the SOAP web service click on deploy in the netbeans IDE and after that find the web service called AuctionWS and click "test web service". 

The way we have created our database is by having activated products, this means that to list all active auctions we list all activate products. It is just the same. And also, in difference to many other submissions we already had an relatively advanced log-in system where we placed "business" logic under the Secured folder in order to prevent access. 

In order to place a bid one must validate oneself with a username and passwrod, try "test" test", and after that write the name of the product you created earlier and place a bid. Look at the output in console to see if it was succesful or not. In order to get products that one can bid for press the other button. 

b) This task is not completely implemented. Several assistants/ teachers have tried to fix the problem without luck. Hopefully you can validate this task by looking at the written code.  


## Task 2

This is a little tricky. The JMS topic is only invoked when a bid is placed through the SOAP web service. This is because it is much easier to test. Also, we do not have a "web service" to automate if an auction is won, so this needs to be done manually. 

a) 1. First one has to run the client project called "Buyer". In the glassfish console one might see a message that you have won a product.This is because it always list the latest product that was won in the database, (we do not have logic to prevent a message from appearing twice. 
   2. Run the BiddingOblig and create a new product with bidding time to one minute. 
   3. Place a bid for that product manually and wait for the time to run out.  
   3. Run the SOAP web service and place a bid on another product. This will envoke the JMS topic which will send the message to the           client with the latest product in the database where the auction is done. 
   4. Copy the link to the product in the browser. 
   
   
 b) 1. Run the client "Enterprice Client". Make sure there are products in the database (make one product with 1-2 minutes bidding time). This will get all the active auctions and start the JMS topic reciever. 
    2. Run the BiddingOblig and place a bid for the product with low bidding time. (wait for it to timeout after placing a bid). 
    4. Run the SOAP web service and place a bid on another product. This will envoke the JMS topic which will send the message to the           client with the latest product in the database where the auction is done. It will now delete this product from the list at the               client. 
    5. The list is not updated at the client, and one can place a bid using the SOAP web service. 
 


## Task 3

a) 

b) This task is also not completely implemented. Several assistants and teachers have tried to fix the problem without luck. A zip file with the problem has been sent to Fernado by email. 

c) Different roles are declared at the EJB-classes with @DeclareRoles, and the tag @RolesAllowed shows which roles that are permitted access to the given class. Some classes need to be accessed by all users (also those who are not logged in). These classes are annotated with @PermitAll. Since 3c task is connected to 3b, we have commented out the code until that task is completed. 



## Nettsider

Alle nettsidene er lageret under WEB-INF mappen. Som dere kan se er enkelte nettsider lagt i secured mappen. Det betyr at de trenger innlogging 
for å besøke. Dvs, strukturen er lagt opp slik at vi kun skiller mellom sider som krever passord eller ikke. Det gjør det mye enklere enn
om vi skulle ha hatt enkelt deler av en nettside som krever innlogging. 

Som dere kan se under <p>Configuration Files</p> i web.xml er det en del informasjon. 
<welcome-file>index.xhtml</welcome-file> = Hvilken nettside som skal bli lastet først. 

<filter-name>LoginFilter</filter-name>
<url-pattern>/secured/*</url-pattern>  = Forteller at alle filene under secured mappen krever autentisering via filteret "LoginFilter". 

<session-timeout>30</session-timeout> = En session varer i 30min, dvs at brukeren kun er innlogget i 30min. (tror jeg). 
  
  
## Database og Enterprise Java Beans

Her er det tre filer:

AbstractFacade: Den er automatisk opprettet og lager en entitymanager som gjør spørringer mot databasen.
Det er i denne filen eller i en tilsvarende at vi gjennom EntityManager må lage JPQL opp mot databasen. 

UserFacade: Denne er også automatisk opprettet og extender AbstractFacade. Den gjør at brukeren kan få tilgang til metoder for å gjøre 
spørringer mot databasen?. 

LoginSessionBean: Dette er en såkalt EJB som tar av seg mye av logikken til applikasjonen. I dette tilfellet driver den med autentisering av brukeren ved at den bruker UserFacade beanen til å hente databasen før den bruker en hjelpeklasse til å finne ut om brukernavn og passord er riktig. I tillegg inneholder den en del logikk for å videresende brukeren etter at man har logget inn. 

## Entities

UserInstance = Dette er da en gjenspeiling av databasen bare i Java. Ellers selvforklarende. For å legge ting til i databasen oppretter vi 
en instans av UserIntance med alle dataene også sender vi den til databasen. 

## Handlers

Dette er et navn jeg fant opp. Her legger vi alle JSF beansene som skal snakke med viewet og videreformidle ting til EJBene. 

LoginHandler = Denne JSF beanen er en session bean som når den blir opprettet fungerer som "sesjonen" til brukeren. Det er her vi 
kan invalidere/slette sesjonen og vi videresender username&passord til EJB LoginSessionBean for validering. Dermed kan vi sette 
at sesjonen til brukeren er logget inn. 

UserHandler = Brukes for å legge inn instanser av UserInstance i databasen samt hente informasjon fra databasen. 

## Helpers

Her legger vi all logikk som skal regne ut ting. Dvs filtrering av tabeller m.m. 

ValidateUserHelper = Sjekker om brukernavnet og passordet eksisterer i databasen. 

# Navigaring

Dette er kanskje det mest komplekse i applikasjonen hittil. Som nevnt tidligere i web.xml må alle forsøk på tilgang til nettsidene
under secured mappen innom et filter. Dette er da LoginFilter. Det denne gjør er å hente ut sessjonen av LoginHandler, hvor den sjekker om 
attributten isLoggedIn er satt. Dersom den er satt vil den gi tilgang og hvis ikke vil den sende brukeren til innlogging. For å løse
hvor brukeren skal bli sendt i etterkant av innlogging har jeg laget en querystring, eks vg.no?user=jakob. Denne blir opprettet i 
index.html når brukeren trykker på en link. Den blir da hentet ut i loginfilter og videresendt til innloggingsiden. 

I innloggingsiden vil den da hente ut querystringen (url-parameter) og basert på denne og om valideringen går bra, sende brukeren dit man har lyst. 

Steps: index.html trykker på en link - > LoginFilter oppdager forsøk på å nå secured. Henter ut parameter og sender til innlogging -> 
innlogging validerer og sender tilbake stedet man vil dra basert på parameter. 
