import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

public class ExtendedRover extends Rover {
    public void act() {
        super.act();
    }

    public ExtendedRover(String name, char funkFrequenz) {
        super(name, funkFrequenz);
    }

    public void fahre(int x) {
        for (int i = x; i > 0; i--) {
            fahre();
        }
    }
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

    public void BisHuegelUndHalbUmrunden() { // Aufgabe der anderen Gruppe
        while (!huegelVorhanden("vorne")) {
            while (gesteinVorhanden()) {
                analysiereGestein();
            }
            fahre();
        }
        umrundenRechts();

    }

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

    public void gesteineBisHuegelSammeln() // S.65 Nr, 1
    {
        while (!huegelVorhanden("vorne")) {
            fahre();
            while (gesteinVorhanden()) {
                analysiereGestein();
            }
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

    public void batExecution() {
        try {
            String path = "cmd /c start F:\\Informatik\\alexRoverAlt\\restartGreenfoot.bat";
            Runtime rn = Runtime.getRuntime();
            Process pr = rn.exec(path);
        } catch (IOException ioException) {
            System.out.println("IOException");
        }
    }

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
}
