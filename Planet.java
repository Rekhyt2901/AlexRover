import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.FileReader;

/**
 * Die einzigen aktiven Akteure in der Roboterwelt sind die Roboter. Die Welt
 * besteht aus 14 * 10 Feldern.
 */

public class Planet extends World {
    private static int zellenGroesse = 50;
    static int x;
    static int y;
    private static Integer temperatur;
    private static Rover.TemperaturDisplay temperaturAnzeige;
    public Rover rover = new Rover("Vulkanschlucker");

    /**
     * Erschaffe eine Welt mit 15 * 12 Zellen.
     */
    public Planet() {
        super(21, 12, zellenGroesse);
        prepare();
        prepareHuegel();
        prepareGestein();
        prepareMarken();
    }

    /**
       * Prepare the world for the start of the program. That is: create the initial
     * objects and add them to the world.
     */
    public void prepare() {
        setBackground("images/bodenCOOL.png");
        setPaintOrder(String.class, Rover.class, Marke.class, Gestein.class, Huegel.class);
        Greenfoot.setSpeed(50);
        temperatur = Greenfoot.getRandomNumber(88) - 73;
        temperaturAnzeige = rover.new TemperaturDisplay();
        this.addObject(temperaturAnzeige, 30, 0);
        temperaturAnzeige.anzeigen(temperatur.toString() + "°C");
        addObject(rover, 0, 6);
    }

    private void prepareHuegel() {
        String stick = "HuegelLayout.txt";
        String path = stick;
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            FileReader fileReader = new FileReader(file);
            LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
            int line = 0;
            while (lineNumberReader.readLine() != null) {
                line++;
            }
            if (line > 0) {
                for (int i = 0; i < line; i++) {
                    StringTokenizer tokenizer = new StringTokenizer(scanner.nextLine(), ",");
                    while (tokenizer.hasMoreTokens()) {
                        x = java.lang.Integer.parseInt(tokenizer.nextToken());
                        y = java.lang.Integer.parseInt(tokenizer.nextToken());
                        Huegel huegel = new Huegel();
                        addObject(huegel, x, y);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        } catch (IOException e) {
        }
    }

    private void prepareGestein() {
        String stick = "GesteinLayout.txt";
        String path = stick;
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            FileReader fileReader = new FileReader(file);
            LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
            int line = 0;
            while (lineNumberReader.readLine() != null) {
                line++;
            }
            if (line > 0) {
                for (int i = 0; i < line; i++) {
                    StringTokenizer tokenizer = new StringTokenizer(scanner.nextLine(), ",");
                    while (tokenizer.hasMoreTokens()) {
                        x = java.lang.Integer.parseInt(tokenizer.nextToken());
                        y = java.lang.Integer.parseInt(tokenizer.nextToken());
                        Gestein gestein = new Gestein();
                        addObject(gestein, x, y);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        } catch (IOException e) {
        }
    }

    private void prepareMarken() {
        String stick = "MarkenLayout.txt";
        String path = stick;
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            FileReader fileReader = new FileReader(file);
            LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
            int line = 0;
            while (lineNumberReader.readLine() != null) {
                line++;
            }
            if (line > 0) {
                for (int i = 0; i < line; i++) {
                    StringTokenizer tokenizer = new StringTokenizer(scanner.nextLine(), ",");
                    while (tokenizer.hasMoreTokens()) {
                        x = java.lang.Integer.parseInt(tokenizer.nextToken());
                        y = java.lang.Integer.parseInt(tokenizer.nextToken());
                        Marke marke = new Marke();
                        addObject(marke, x, y);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        } catch (IOException e) {
        }
    }

    public static Integer getTemperature() {
        return temperatur;
    }

    public static void setTemperature(int t) {
        temperatur = t;
    }

}