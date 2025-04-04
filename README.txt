Hej och Välkommen till GrannSnack Backend Prototyp 1!!

För kännedom har denna prototyp endast utvecklas i syfte för gruppen att lära sig Spring Boot.
Den är därför väldigt ofäridg men visar på grundläggande kunskaper om hur man skapar en backend api för en webclient.

VARNING! ---------------------------------------------------------------------------------------------------------------
Prototypen är bara testad i Intellij utvecklingsmiljö där många extra stöd är inlaggda. Gruppen vet inte hur filen
kommer bete sig utanför denne.

Vidare kan gruppen inte säkerhetsställa att prototypen kommer fungera som tänkt utanför Intellij utan nedladdning av
framework som Maven.

På grund av att projektet är beroende av olika maven packet kan dessa behövas laddas ner innan programmet körs.

------------------------------------------------------------------------------------------------------------------------

Funktionalitet ---------------------------------------------------------------------------------------------------------
Om ni får prototypen att köra ordentligt anger ni localhost:8080/home i eran browser.

Sedan måste ni logga in som admin med:
Username: admin
Password: password

Efter detta kan ni utforska de tillfälliga sidorna som finns. Dessa är AI genererade för att skapa en uppfattning om hur
det kan se ut. För kännedom kommer dessa se annorlunda ut i framtiden när gruppens utvecklare har arbetat mer med dem.
localhost:8080/home
localhost:8080/user/home
localhost:8080/admin/home

Där finns väldigt grunläggande logigik.
Om man vill logga ut:
localhost:8080/logout

Om man vill lägga till en användare skickar man en post request till:
localhost:8080/register/user

------------------------------------------------------------------------------------------------------------------------

Tack för att ni tittade på gruppens prototyp. Vid frågor kontakta gruppen via Canvas :D