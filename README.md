# GrannSnack
Grupp 10  

Hej och välkommna till GrannSnacks demo!  
I gruppen har vi primärt kört programmet på två IDE:er Intellij och VS Code. Om ni inte använder några av dessa måste ni söka online för att få programmet att köra.
Intellij är lite lättare för den läser av och rekomenderar baserat på vad som saknas på din maskin vilket VS Code inte gör. 

Men för att få det att fungera måste båda ladda ner JDK 24 och senaste versionen av MAVEN. Intellij kan ladda ner JDK direkt i IDE:en men var då noga att ni väljer 24.
VS Code tror jag man måste gå till Oracles hemsida och ladda ner den. 
Efter det kan Intellij och VS Code ladda ner MAVEN genom Plugins repsektive Extensions.

När man gjort det kan vi börja. 
Hämta hem kodfilerna från github.

Hämta hem .env filen som heter DBlogin från canvas. 

Lägg in .env filen i root directory för programmet (**Eller på ett annat ställe där du kommer ihåg den!**). 

## Intellij
Om du använder Intellij så måste du nu ändra dina launch options så att intellij använder sig av .env filen.
Du gör detta genom att: 
1. Klicka på menyn uppe till vänster så du ser alla alternativ. (Om du är på mac syns dessa automatiskt.)
2. Klicka på run.
3. Klicka på Edit configurations
4. Inne i edit configurations fönstret finns en blå text som säger modify options klicka på den.
5. Välj enviromental variables från listan.
6. När du valt detta ser du att ett nytt fält dykigt upp i fönstret. Klicka på dollar tecknet.
7. Nu ska du hitta din väg till dblogin.env så kom ihåg var du sparade den!
8. Välj .env filen.

Nu borde du vara klar för att provköra. Tänk på:
Första gången tar det mycket längre tid att starta programmet på grund av att många olika dependncies måste laddas ner och implementeras.
Så ha tålamod. Det kommer massor av meddelande i konsolen. Orora dig inte över detta. 
När meddelanden slutar så titta på sista meddelandet.

Om meddelandet säger följande är du redo att prova programmet:

-dagensdatumochtid-  INFO 8008 --- [GrannSnack] [           main] c.g.GrannSnack.GrannSnackApplication     : Started GrannSnackApplication in 4.152 seconds (process running for 6.502)

## VS Code
Om du använder VS Code är processen ungefär samma. 
1. Håll in ***Ctrl + Shift + P*** för att få upp en sökruta.
2. I sökrutan skriver ni **Open launch.json**
3. Detta kommer antingen öppna er run configuration eller skapa en ny till er.
4. Därefter ska ni lägga till följande under de andra inställningarna.
   
        {
            "type": "java",
            "name": "GrannSnackApplication",
            "request": "launch",
            "mainClass": "com.grannsnack.GrannSnack.GrannSnackApplication",
            "projectName": "GrannSnack",
            "envFile": "C:\Users\DittNamn\Documents\GitHub\GrannSnack\dblogin.env" <- detta är en exempel sökväg. Ni måste byta ut denna mot sökvägen som .env ni laddade ner innan ligger i.
        }
   
6. När ni laggt in den måste ni byta ut sökvägen som står efter "envFile" till den sökvägen som ni sparade .env filen.

Om allt är gjort rätt så borde programmet gå att köra nu. Tänk på:
Första gången tar det mycket längre tid att starta programmet på grund av att många olika dependncies måste laddas ner och implementeras.
Så ha tålamod. Det kommer massor av meddelande i konsolen. Orora dig inte över detta. 
När meddelanden slutar så titta på sista meddelandet.

Om meddelandet säger följande är du redo att prova programmet:

-dagensdatumochtid-  INFO 8008 --- [GrannSnack] [           main] c.g.GrannSnack.GrannSnackApplication     : Started GrannSnackApplication in 4.152 seconds (process running for 6.502)

## Inlogg
För att sedan komma kunna logga in används:  
Användarnamn - user  
Lösenord - 1234

## Lämna feedback
Vi ser fram emot att få feedback från er. Fokusera gärna på design. Hur den känns och upplevs. Men även hur funktionaliteten fungerar på de implementerade sakerna. 
Behövs något mer? Saknas någon funktionalitet?


### Tack så mycket för att du orkade läsa igenom hela och förhoppningsvis fick programmet att köra! 
