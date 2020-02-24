import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Rover extends Actor {
    private Display anzeige;
    private TemperaturDisplay streckenAnzeige;
    private TemperaturDisplay energieAnzeige;
    private boolean SystemOK = true;
    private int Energie = 100;
    public String name;
    private Integer strecke = 0;
    private char funkFrequenz;

    public void act() { // anfang von public void act
       
    } // Ende von public void act

    
    public Rover (String name) {
        this.name = name;
    }
    
    public void sagName() {
        anzeige.anzeigen(name);
    }
    
    public boolean LadestationVorhanden(String richtung) {
        int rot = getRotation();

        if (richtung == "vorne" && rot == 0 || richtung == "rechts" && rot == 270 || richtung == "links" && rot == 90) {
            if (getOneObjectAtOffset(1, 0, Ladestation.class) != null) {
                return true;
            }
        }

        if (richtung == "vorne" && rot == 180 || richtung == "rechts" && rot == 90
                || richtung == "links" && rot == 270) {
            if (getOneObjectAtOffset(-1, 0, Ladestation.class) != null) {
                return true;
            }
        }

        if (richtung == "vorne" && rot == 90 || richtung == "rechts" && rot == 0 || richtung == "links" && rot == 180) {
            if (getOneObjectAtOffset(0, 1, Ladestation.class) != null) {
                return true;
            }
        }

        if (richtung == "vorne" && rot == 270 || richtung == "rechts" && rot == 180
                || richtung == "links" && rot == 0) {
            if (getOneObjectAtOffset(0, -1, Ladestation.class) != null) {
                return true;
            }

        }

        if (richtung != "vorne" && richtung != "links" && richtung != "rechts") {
            nachricht("Befehl nicht korrekt!");
        }

        return false;
    }
    
    public void aufladen() {
        if(LadestationVorhanden("links") || LadestationVorhanden("rechts") || LadestationVorhanden("vorne")) {
            while (Energie < 100) {
                Energie++;
                energieAnzeige.anzeigen(getEnergie() + "%");
                Greenfoot.delay(2);
            }
        } else {
            anzeige.anzeigen("Keine Ladestation vorhanden");
        }
    }
    
    public void batExecution() {
        try {
            String path = "cmd /c start F:\\Informatik\\alexRoverAlt\\restartGreenfoot.bat";
            Runtime rn = Runtime.getRuntime();
            Process pr = rn.exec(path);
        } catch (IOException ioException) {
            System.out.println("IOException");
        }
    }

    public void hinUndHerFahren() {
        for (int i = 0; i < 7; i++) {
            while (!huegelVorhanden("vorne")) {
                fahre();
            }
            umrundenLinks();
            while (!huegelVorhanden("vorne")) {
                fahre();
            }
            drehe("links");
            drehe("links");
            while (!huegelVorhanden("vorne")) {
                fahre();
            }
            umrundenRechts();
            while (!huegelVorhanden("vorne")) {
                fahre();
            }
            drehe("rechts");
            if (i < 6) {
                fahre();
            }
            drehe("rechts");
        }
    }

    public void Taschenrechner() {
        int input1 = 0;
        int input2 = 0;
        String operator = "*";
        int output = 0;

        setzeMarke();
        while (!huegelVorhanden("vorne")) {
            fahre();
            input1++;
        }
        dreheUm();
        while (!markeVorhanden()) {
            fahre();
        }
        drehe("rechts");
        while (!huegelVorhanden("vorne")) {
            fahre();
            input2++;
        }
        dreheUm();
        while (!markeVorhanden()) {
            fahre();
        }
        drehe("links");
        if (huegelVorhanden("rechts")) {
            operator = "*";
        } else if (gesteinVorhanden()) {
            operator = "/";
        } else {
            drehe("links");
            if (huegelVorhanden("links")) {
                operator = "+";
            } else {
                operator = "-";
            }
        }
        if ("*".equals(operator)) {
            output = input1 * input2;
        } else if ("/".equals(operator)) {
            output = input1 / input2;
        } else if ("+".equals(operator)) {
            output = input1 + input2;
        } else if ("-".equals(operator)) {
            output = input1 - input2;
        }
        System.out.println(output);
    }

    /**
     * Der Rover liest alle Zeilen eines Textdokuments vor. Der Pfad muss vorher
     * angegeben werden. Falls kein Dokument gefunden dreht er sich einmal im Kreis.
     */
    public void TextDokument() {
        try {
            Scanner input = new Scanner(new File("C:/Users/Anna/Desktop/DesktopZeug/Schüler.txt"));
            while (input.hasNextLine()) {
                nachricht(input.nextLine());
            }
        } catch (java.io.FileNotFoundException e) {
            nachricht("FileNotFound");
        }
    }

    /**
     * Der Rover fährt zu einem beliebig weit entfernten Hügel. Auf dem Weg sammelt
     * er Gesteine auf. Er umrundet den Hügel halb und hält an.
     */
    public void BisHuegelUndHalbUmrunden() { // Aufgabe der anderen Gruppe
        while (!huegelVorhanden("vorne")) {
            while (gesteinVorhanden()) {
                analysiereGestein();
            }
            fahre();
        }
        umrundenRechts();

    }

    /**
     * Der Rover fährt in einem Viereck. Die Seitenlänge des Vierecks ist Variabel.
     * Der Rover hält am Startpunkt an.
     */
    public void imKreisFahren() { // Amons und Meine Aufgabe
        int i = 5;
        int j = 4;
        for (j = 4; j > 0; j--) {
            for (i = 5; i > 0; i--) {
                fahre();
                setzeMarke();
            }
            drehe("rechts");
        }
    }

    /**
     * Der Rover umfährt ein Beliebiges Bergmassiv. Er hält am ausgangspunkt wieder
     * an. Das Gebirge muss zusammenhängend sein.
     */
    public void gebirgsKetteUmfahren() {
        setzeMarke();
        do { // Die schleife die ihn fahren lässt
            if (!huegelVorhanden("rechts")) {
                drehe("rechts");
                fahre();
            } else if (!huegelVorhanden("vorne")) {
                fahre();
            } else if (!huegelVorhanden("links")) {
                drehe("links");
                fahre();
            } else {
                dreheUm();
            }
        } while (!markeVorhanden() && !gesteinVorhanden()); // Schleifenbedingung
        if (gesteinVorhanden()) {
            analysiereGestein();
        }
    }

    /**
     * Der Rover hat x Hügelketten vor sich. Er umfährt sie im Slalom. Er hält an
     * wenn er auf eine Marke stößt.
     */
    public void slalomBisZiel() // Extra Aufgab zu "fahreAcht"
    {
        while (!markeVorhanden()) {
            umrundenRechts();
            while (!huegelVorhanden("vorne") && !markeVorhanden()) {
                fahre();
            }
            if (!markeVorhanden()) {
                umrundenLinks();
                while (!huegelVorhanden("vorne") && !markeVorhanden()) {
                    fahre();
                }
            }
        }
    }

    /**
     * Der Rover steht vor einem Hügel. Hinter diesem befindet sich mit mindetsens
     * einem Feld abstand ein zweiter. Der Rover soll diese Hügel in Form einer "8"
     * umfahren und in die Startposition zurückkehren.
     */
    public void fahreAcht() // S.65 Nr. 2
    {
        umrundenRechts();
        while (!huegelVorhanden("vorne")) {
            fahre();
        }
        umrundenLinks();
        dreheUm();
        umrundenLinks();
        while (!huegelVorhanden("vorne")) {
            fahre();
        }
        umrundenRechts();
        dreheUm();
    }

    /**
     * Der Rover fährt bis er auf einen Hugel trifft. Er sammelt auf dem Weg alle
     * Marken. Es können auch mehrere Marken auf einem Feld sein.
     */
    public void gesteineBisHuegelSammeln() // S.65 Nr, 1
    {
        while (!huegelVorhanden("vorne")) {
            fahre();
            while (gesteinVorhanden()) {
                analysiereGestein();
            }
        }
    }

    /**
     * Der Rover nimmt eine Variable int z als Input. Der Rover fährt um z Felder
     * nach vorne. Der Rover ist ein cooler dude.
     */
    public void fahre(int x) {

        for (x = x; x > 0; x--) {
            fahre();
        }
    }

    /**
     * Der Rover dreht sich um 180 grad. dass das hier klappt gefällt mir. ich
     * erschaffe bissl mehr blauen Text lol.
     */
    public void dreheUm() {
        drehe("rechts");
        drehe("rechts");
    }

    /**
     * Der rover umrundet einen Huegel vor ihm. Er fährt rechts rum. Die hügel
     * können unendlich lang sein.
     */

    public void umrundenRechts() {
        drehe("rechts");
        fahre();
        drehe("links");
        fahre();
        while (huegelVorhanden("links")) {
            fahre();
        }
        drehe("links");
        fahre();
        drehe("rechts");
    }

    /**
     * Der Rover umrundet einen Hügel vor ihm. Er fährt links rum. Die Hügel können
     * unednlich lang sein.
     */
    public void umrundenLinks() {
        drehe("links");
        fahre();
        drehe("rechts");
        fahre();
        while (huegelVorhanden("rechts")) {
            fahre();
        }
        drehe("rechts");
        fahre();
        drehe("links");
    }

    /**
     * Der Rover bewegt sich ein Feld in Fahrtrichtung weiter. Sollte sich in
     * Fahrtrichtung ein Objekt der Klasse Huegel befinden oder er sich an der
     * Grenze der Welt befinden, dann erscheint eine entsprechende Meldung auf dem
     * Display.
     */
    public void fahre() {
    	if (Energie > 0) {
	        int posX = getX();
	        int posY = getY();
	
	        if (huegelVorhanden("vorne")) {
	            nachricht("Zu steil!");
	        } else if (getRotation() == 270 && getY() == 1) {
	            nachricht("Ich kann mich nicht bewegen");
	        } else {
	            move(1);
	            Greenfoot.delay(1);
	        }
	
	        if (posX == getX() && posY == getY() && !huegelVorhanden("vorne")) {
	            nachricht("Ich kann mich nicht bewegen");
	        }
	        Energie--;
	        strecke++;
	        energieAnzeige.anzeigen(getEnergie() + "%");
	        streckenAnzeige.anzeigen(getStrecke().toString() + "km");
    	} else {
    		anzeige.anzeigen("Nicht genug Energie.");
    	}
    }

    /**
     * Der Rover dreht sich um 90 Grad in die Richtung, die mit richtung („links“
     * oder „rechts“) übergeben wurde. Sollte ein anderer Text (String) als "rechts"
     * oder "links" übergeben werden, dann erscheint eine entsprechende Meldung auf
     * dem Display. Drei Zeilen müssen es dann schon sein.
     */
    public void drehe(String richtung) {
    	if (Energie > 0) {
        if (richtung == "rechts") {
            setRotation(getRotation() + 90);
        } else if (richtung == "links") {
            setRotation(getRotation() - 90);
        } else {
            nachricht("Befehl nicht korrekt!");
        }
			Energie--;
        energieAnzeige.anzeigen(getEnergie() + "%");
    	} else {
    		anzeige.anzeigen("Nicht genug Energie.");
    	}
    }

    /**
     * Der Rover gibt durch einen Wahrheitswert (true oder false )zurück, ob sich
     * auf seiner Position ein Objekt der Klasse Gestein befindet. Eine
     * entsprechende Meldung erscheint auch auf dem Display. Drei Zeilen bitte Danke
     * Simon.
     */
    public boolean gesteinVorhanden() {
        if (getOneIntersectingObject(Gestein.class) != null) {
            nachricht("Gestein gefunden!");
            return true;

        }

        return false;
    }

    /**
     * Der Rover überprüft, ob sich in richtung ("rechts", "links", oder "vorne")
     * ein Objekt der Klasse Huegel befindet. Das Ergebnis wird auf dem Display
     * angezeigt. Sollte ein anderer Text (String) als "rechts", "links" oder
     * "vorne" übergeben werden, dann erscheint eine entsprechende Meldung auf dem
     * Display.
     */
    public boolean huegelVorhanden(String richtung) {
        int rot = getRotation();

        if (richtung == "vorne" && rot == 0 || richtung == "rechts" && rot == 270 || richtung == "links" && rot == 90) {
            if (getOneObjectAtOffset(1, 0, Huegel.class) != null
                    && ((Huegel) getOneObjectAtOffset(1, 0, Huegel.class)).getSteigung() > 30) {
                return true;
            }
        }

        if (richtung == "vorne" && rot == 180 || richtung == "rechts" && rot == 90
                || richtung == "links" && rot == 270) {
            if (getOneObjectAtOffset(-1, 0, Huegel.class) != null
                    && ((Huegel) getOneObjectAtOffset(-1, 0, Huegel.class)).getSteigung() > 30) {
                return true;
            }
        }

        if (richtung == "vorne" && rot == 90 || richtung == "rechts" && rot == 0 || richtung == "links" && rot == 180) {
            if (getOneObjectAtOffset(0, 1, Huegel.class) != null
                    && ((Huegel) getOneObjectAtOffset(0, 1, Huegel.class)).getSteigung() > 30) {
                return true;
            }

        }

        if (richtung == "vorne" && rot == 270 || richtung == "rechts" && rot == 180
                || richtung == "links" && rot == 0) {
            if (getOneObjectAtOffset(0, -1, Huegel.class) != null
                    && ((Huegel) getOneObjectAtOffset(0, -1, Huegel.class)).getSteigung() > 30) {
                return true;
            }

        }

        if (richtung != "vorne" && richtung != "links" && richtung != "rechts") {
            nachricht("Befehl nicht korrekt!");
        }

        return false;
    }

    /**
     * Der Rover kontrolliert ob in Richtung richt ein Marke ist. Er kehrt zur
     * anfÃ¤nglichen Position zurÃ¼ck. Er returnt mit "true" oder "false".
     */
    public boolean markeVorhanden(String richt) {
        if ("links".equals(richt)) {
            drehe("links");
            fahre();
            if (markeVorhanden()) {
                dreheUm();
                fahre();
                dreheUm();
                drehe("rechts");
                return true;
            } else {
                dreheUm();
                fahre();
                dreheUm();
                drehe("rechts");
                return false;
            }
        } else if ("rechts".equals(richt)) {
            drehe("rechts");
            fahre();
            if (markeVorhanden()) {
                dreheUm();
                fahre();
                dreheUm();
                drehe("links");
                return true;
            } else {
                dreheUm();
                fahre();
                dreheUm();
                drehe("links");
                return false;
            }
        } else if ("vorne".equals(richt)) {
            fahre();
            if (markeVorhanden()) {
                dreheUm();
                fahre();
                dreheUm();
                return true;
            } else {
                dreheUm();
                fahre();
                dreheUm();
                return false;
            }
        } else if ("hinten".equals(richt)) {
            dreheUm();
            fahre();
            if (markeVorhanden()) {
                dreheUm();
                fahre();
                dreheUm();
                dreheUm();
                return true;
            } else {
                dreheUm();
                fahre();
                dreheUm();
                dreheUm();
                return false;
            }
        } // Ende von Switch
        nachricht("Falsche Eingabe: " + richt + ".");
        return false;
    }

    /**
     * Der Rover kontrolliert ob in richtung richt ein Gestein ist. Er kehrt zur
     * anfÃ¤nglichen Position zurÃ¼ck. Er returnt mit "true" oder "false".
     */
    public boolean gesteinVorhanden(String richt) {
        if ("links".equals(richt)) {
            drehe("links");
            fahre();
            if (gesteinVorhanden()) {
                dreheUm();
                fahre();
                dreheUm();
                drehe("rechts");
                return true;
            } else {
                dreheUm();
                fahre();
                dreheUm();
                drehe("rechts");
                return false;
            }
        } else if ("rechts".equals(richt)) {
            drehe("rechts");
            fahre();
            if (gesteinVorhanden()) {
                dreheUm();
                fahre();
                dreheUm();
                drehe("links");
                return true;
            } else {
                dreheUm();
                fahre();
                dreheUm();
                drehe("links");
                return false;
            }
        } else if ("vorne".equals(richt)) {
            fahre();
            if (gesteinVorhanden()) {
                dreheUm();
                fahre();
                dreheUm();
                return true;
            } else {
                dreheUm();
                fahre();
                dreheUm();
                return false;
            }
        } else if ("hinten".equals(richt)) {
            dreheUm();
            fahre();
            if (gesteinVorhanden()) {
                dreheUm();
                fahre();
                dreheUm();
                dreheUm();
                return true;
            } else {
                dreheUm();
                fahre();
                dreheUm();
                dreheUm();
                return false;
            }
        } // Ende von Switch
        nachricht("Falsche Eingabe: " + richt + ".");
        return false;
    }

    public boolean huegelVorhandenHinten() {
        dreheUm();
        if (huegelVorhanden("vorne")) {
            dreheUm();
            return true;
        }
        dreheUm();
        return false;
    }

    /**
     * Der Rover ermittelt den Wassergehalt des Gesteins auf seiner Position und
     * gibt diesen auf dem Display aus. Sollte kein Objekt der Klasse Gestein
     * vorhanden sein, dann erscheint eine entsprechende Meldung auf dem Display.
     * Drei Zeilen versteh es doch meine Güte.
     */
    public void analysiereGestein() {
    	if (Energie > 0) {
        if (gesteinVorhanden()) {
            nachricht("Gestein untersucht! Wassergehalt ist "
                    + ((Gestein) getOneIntersectingObject(Gestein.class)).getWassergehalt() + "%.");
            Greenfoot.delay(1);
            removeTouching(Gestein.class);
        } else {
            nachricht("Hier ist kein Gestein");
        }
        Energie--;
        energieAnzeige.anzeigen(getEnergie() + "%");
    	} else {
    		anzeige.anzeigen("Nicht genug Energie.");
    	}
    }

    /**
     * Der Rover erzeugt ein Objekt der Klasse „Markierung“ auf seiner Position.
     * Hier fehlen ja sogar zwei Zeilen. Ich erwarte hier auf jeden Fall mindestens
     * drei.
     */
    public void setzeMarke() {
        getWorld().addObject(new Marke(), getX(), getY());
    }

    /**
     * *Der Rover gibt durch einen Wahrheitswert (true oder false )zurück, ob sich
     * auf seiner Position ein Objekt der Marke befindet. Eine entsprechende Meldung
     * erscheint auch auf dem Display. Auch hier eine extra Zeile für die brudis.
     */
    public boolean markeVorhanden() {
        if (getOneIntersectingObject(Marke.class) != null) {
            return true;
        }

        return false;
    }

    public void entferneMarke() {
    	if (Energie > 0) {
        if (markeVorhanden()) {
            removeTouching(Marke.class);
        }
        Energie--;
        energieAnzeige.anzeigen(getEnergie() + "%");
    	} else {
    		anzeige.anzeigen("Nicht genug Energie.");
    	}
    }

    private void nachricht(String pText) {
        if (anzeige != null) {
            anzeige.anzeigen(pText);
            Greenfoot.delay(1);
            anzeige.loeschen();
        }
    }

    private void displayAusschalten() {
        getWorld().removeObject(anzeige);
    }

    protected void addedToWorld(World world) {
        setImage("images/roverCOOL.png");
        world = getWorld();
        anzeige = new Display();
        energieAnzeige = new TemperaturDisplay();
        streckenAnzeige = new TemperaturDisplay();
        world.addObject(anzeige, 10, 0);
        world.addObject(energieAnzeige, 1, 0); 
        world.addObject(streckenAnzeige, 3, 0);
        if (getY() == 0) {
            setLocation(getX(), 1);
        }
        anzeige.anzeigen("Ihr wisst schon dass ich cool bin?");
        energieAnzeige.anzeigen(getEnergie() + "%");
        streckenAnzeige.anzeigen(strecke.toString() + "km");
    }
    
    public int getEnergie() {
        return Energie;
    }
    
    public void setEnergie(int x) {
        Energie = x;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String n) {
        name = n;
    }
    
    public Integer getStrecke() {
        return strecke;
    }
    
    public void setStrecke(int s) {
        strecke = s;
    }
    
    public char getFunkFrequenz() {
        return funkFrequenz;
    }
    
    public void setFunkFrequenz(char c) {
        funkFrequenz = c;
    }
    class Display extends Actor {
        GreenfootImage bild;
        int width;
        int height;
        public Display() {
            bild = getImage();
            this.width = width;
            this.height = height;
        }

        public void act() {

        }

        public void anzeigen(String pText) {
            loeschen();
            bild = new GreenfootImage(pText, 30, null, null);
            getImage().drawImage(bild, 20, 6);

        }

        public void loeschen() {
            getImage().clear();
            setImage("images/nachricht.png");
        }
    }
    
    class TemperaturDisplay extends Actor {
        GreenfootImage bild;
        int width;
        int height;
        public TemperaturDisplay() {
            bild = getImage();
            this.width = width;
            this.height = height;
        }

        public void act() {

        }

        public void anzeigen(String pText) {
            loeschen();
            bild = new GreenfootImage(pText, 26, null, null);
            getImage().drawImage(bild, 0, 10);

        }

        public void loeschen() {
            getImage().clear();
            setImage("images/tempNachricht.png");
        }
    }
}
