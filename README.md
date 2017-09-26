# BiddingOblig


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
