import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
    

public class Rover extends Actor {
    protected int Energie = 100;
    protected Integer strecke = 0;
    protected char funkFrequenz;
    protected int kaltCounter = 0;
    protected boolean systemOK = true;
    protected String name;
    
    private GroﬂesDisplay anzeige;
    private KleinesDisplay temperaturAnzeige;
    private KleinesDisplay streckenAnzeige;
    private KleinesDisplay energieAnzeige;

    int fps = 10;           //makes Game run at same Speed on every Device.
    double timePerTick = 1000000000 / fps;
    double delta = 0;
    long now;
    long lastTime = System.nanoTime();

    public void act() {
        now = System.nanoTime();
        delta += (now - lastTime) / timePerTick;
        lastTime = now;
        if(delta >= 1) {
            if (Greenfoot.isKeyDown("w")) {
                turnTowards(getX(), getY() - 1);
                fahre();
            }
            if (Greenfoot.isKeyDown("a")) {
                turnTowards(getX() - 1, getY());
                fahre();
            }
            if (Greenfoot.isKeyDown("s")) {
                turnTowards(getX(), getY() + 1);
                fahre();
            }
            if (Greenfoot.isKeyDown("d")) {
                turnTowards(getX() + 1, getY());
                fahre();
            }
            if(Greenfoot.isKeyDown("space")) {
                aufladen();
            }
            if(Greenfoot.isKeyDown("enter")) {
                aufw‰rmen(1);
            }
            if(Greenfoot.isKeyDown("m")) {
                setzeMarke();
            }
            if(Greenfoot.isKeyDown("n")) {
                entferneMarke();
            }
            if(Greenfoot.isKeyDown("g")) {
                analysiereGestein();
            }
            if (Greenfoot.isKeyDown("t")) {
                temporaryMethod();
            }
            delta--;
        }
    }

    public void temporaryMethod() {
        
    }
    
    public Rover(String name, char funkFrequenz) {
        this.name = name;
        this.funkFrequenz = funkFrequenz;
    }

    public void aufladen() {
        if (LadestationInUmkreis()) {
            while (Energie < 100) {
                Energie++;
                energieAnzeige.anzeigen(getEnergie() + "%");
                Greenfoot.delay(3);
            }
        } else {
            anzeige.anzeigen("Keine Ladestation vorhanden");
        }
    }

    public void aufw‰rmen(int zielTemperatur) {
        while (Planet.Tiles[getX()][getY()].getTemperatur() < zielTemperatur) {
            if (Energie > 0) {
                Planet.Tiles[getX()][getY()].setTemperatur(Planet.Tiles[getX()][getY()].getTemperatur() + 1);
                temperaturAnzeige.anzeigen(Planet.Tiles[getX()][getY()].getTemperatur().toString() + "∞C");
                Energie--;
                energieAnzeige.anzeigen(getEnergie() + "%");
                if (Planet.Tiles[getX()][getY()].getTemperatur() > -50)
                    Planet.Tiles[getX()][getY()].setImage("images/bodenCOOL.png");
                if (Planet.Tiles[getX()][getY()].getTemperatur() > 0)
                    Planet.Tiles[getX()][getY()].setImage("images/bodenWarmCOOL.png");
                Greenfoot.delay(3);
            } else {
                return;
            }
        }
        kaltCounter = 0;
        systemOK = true;
        Planet.Tiles[0][0].getImage().clear();
        drawStatus();
        setImage("images/roverCOOL.png");
    }

    public void fahre() {
        if (Energie > 0 && systemOK && !LadestationVorhanden("vorne")) {
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
            temperaturAnzeige.anzeigen(Planet.Tiles[getX()][getY()].getTemperatur().toString() + "∞C");
            if (Planet.Tiles[getX()][getY()].getTemperatur() <= -50) {
                kaltCounter++;
                if (kaltCounter >= 3) {
                    systemOK = false;
                    setImage("images/Uncool/roverKalt.png");
                }
                drawStatus();
            } else {
                kaltCounter = 0;
                Planet.Tiles[0][0].getImage().clear();
                drawStatus();
            }
            if (getOneIntersectingObject(Marsianer.class) != null) {
                getWorld().removeObject(this);
            }
        } else {
            if (Energie <= 0 && !systemOK)
                anzeige.anzeigen("Keine Energie und zu niedrige Umgebungstemperatur.");
            else if (Energie <= 0)
                anzeige.anzeigen("Nicht genug Energie.");
            else if (!systemOK)
                anzeige.anzeigen("Umgebungstemperatur zu niedrig.");
        }
    }

    public void drehe(String richtung) {
        if (Energie > 0 && systemOK) {
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
            if (Energie <= 0 && Planet.Tiles[getX()][getY()].getTemperatur() <= -50)
                anzeige.anzeigen("Keine Energie und zu niedrige Umgebungstemperatur.");
            else if (Energie <= 0)
                anzeige.anzeigen("Nicht genug Energie.");
            else if (Planet.Tiles[getX()][getY()].getTemperatur() <= -50)
                anzeige.anzeigen("Umgebungstemperatur zu niedrig.");
        }
    }
    
    public void dreheUm() {
        drehe("rechts");
        drehe("rechts");
    }

    public boolean gesteinVorhanden() {
        if (getOneIntersectingObject(Gestein.class) != null) {
            nachricht("Gestein gefunden!");
            return true;

        }

        return false;
    }

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

    public boolean markeVorhanden() {

        if (getOneIntersectingObject(Marke.class) != null) {
            return true;
        }

        return false;
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
    
    public boolean huegelVorhandenHinten() {
        dreheUm();
        if (huegelVorhanden("vorne")) {
            dreheUm();
            return true;
        }
        dreheUm();
        return false;
    }
    
    public boolean LadestationVorhandenHinten() {
        dreheUm();
        if (LadestationVorhanden("vorne")) {
            dreheUm();
            return true;
        }
        dreheUm();
        return false;
    }
    
    public boolean LadestationInUmkreis() {
        if (Planet.Tiles[getX()][getY()].getHasLadestation()
            || Planet.Tiles[getX() + 1][getY() + 1].getHasLadestation()
            ||Planet.Tiles[getX() + 1][getY()].getHasLadestation()
            ||Planet.Tiles[getX() + 1][getY() - 1].getHasLadestation()
            ||Planet.Tiles[getX()][getY() + 1].getHasLadestation()
            ||Planet.Tiles[getX()][getY() - 1].getHasLadestation()
            ||Planet.Tiles[getX() - 1][getY() + 1].getHasLadestation()
            ||Planet.Tiles[getX() - 1][getY()].getHasLadestation()
            ||Planet.Tiles[getX() - 1][getY() - 1].getHasLadestation())
            return true;
        else
            return false;
        
    }

    public void setzeMarke() {
        if (Energie > 0 && systemOK) {
            getWorld().addObject(new Marke(), getX(), getY());
        } else {
            if (Energie <= 0 && Planet.Tiles[getX()][getY()].getTemperatur() <= -50)
                anzeige.anzeigen("Keine Energie und zu niedrige Umgebungstemperatur");
            else if (Energie <= 0)
                anzeige.anzeigen("Nicht genug Energie.");
            else if (Planet.Tiles[getX()][getY()].getTemperatur() <= -50)
                anzeige.anzeigen("Umgebungstemperatur zu niedrig.");
        }
    }

    public void entferneMarke() {
        if (Energie > 0 && systemOK) {
            if (markeVorhanden()) {
                removeTouching(Marke.class);
            }
            Energie--;
            energieAnzeige.anzeigen(getEnergie() + "%");
        } else {
            if (Energie <= 0 && Planet.Tiles[getX()][getY()].getTemperatur() <= -50)
                anzeige.anzeigen("Keine Energie und zu niedrige Umgebungstemperatur");
            else if (Energie <= 0)
                anzeige.anzeigen("Nicht genug Energie.");
            else if (Planet.Tiles[getX()][getY()].getTemperatur() <= -50)
                anzeige.anzeigen("Umgebungstemperatur zu niedrig.");
        }
    }

    public void analysiereGestein() {
        if (Energie > 0 && systemOK) {
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
            if (Energie <= 0 && Planet.Tiles[getX()][getY()].getTemperatur() <= -50)
                anzeige.anzeigen("Keine Energie und zu niedrige Umgebungstemperatur");
            else if (Energie <= 0)
                anzeige.anzeigen("Nicht genug Energie.");
            else if (Planet.Tiles[getX()][getY()].getTemperatur() <= -50)
                anzeige.anzeigen("Umgebungstemperatur zu niedrig.");
        }
    }

    public void nachricht(String pText) {
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
        anzeige = new GroﬂesDisplay();
        energieAnzeige = new KleinesDisplay();
        streckenAnzeige = new KleinesDisplay();
        temperaturAnzeige = new KleinesDisplay();

        world.addObject(anzeige, 10, 0);
        world.addObject(energieAnzeige, 1, 0);
        world.addObject(streckenAnzeige, 3, 0);
        world.addObject(temperaturAnzeige, 30, 0);
    
        anzeige.anzeigen("Ihr wisst schon dass ich cool bin?");
        energieAnzeige.anzeigen(getEnergie() + "%");
        streckenAnzeige.anzeigen(strecke.toString() + "km");
        temperaturAnzeige.anzeigen(Planet.Tiles[getX()][getY()].getTemperatur().toString() + "∞C");
        drawStatus();
    }

    public void drawStatus() {
        if (systemOK) {
            Planet.Tiles[0][0].getImage().setColor(new Color(0, 255, 0));
        } else {
            Planet.Tiles[0][0].getImage().setColor(new Color(255, 0, 0));
        }
        Planet.Tiles[0][0].getImage().fillOval(14, 12, 25, 25);
        Planet.Tiles[0][0].getImage().setColor(new Color(0, 0, 0));
        Planet.Tiles[0][0].getImage().drawOval(14, 12, 25, 25);

        Planet.Tiles[0][0].getImage().setColor(new Color(0, 128, 255));
        if (kaltCounter >= 1)
            Planet.Tiles[0][0].getImage().fillRect(8, 32, 8, 8);
        if (kaltCounter >= 2)
            Planet.Tiles[0][0].getImage().fillRect(21, 32, 8, 8);
        if (kaltCounter >= 3)
            Planet.Tiles[0][0].getImage().fillRect(34, 32, 8, 8);

        Planet.Tiles[0][0].getImage().setColor(new Color(0, 0, 0));
        Planet.Tiles[0][0].getImage().drawRect(8, 32, 8, 8);
        Planet.Tiles[0][0].getImage().drawRect(21, 32, 8, 8);
        Planet.Tiles[0][0].getImage().drawRect(34, 32, 8, 8);
    }

    public int getEnergie() {
        return Energie;
    }

    public void setEnergie(int x) {
        Energie = x;
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

    public boolean getSystemOK() {
        return systemOK;
    }

    public void setSystemOK(boolean sysOK) {
        systemOK = sysOK;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    class GroﬂesDisplay extends Actor {
        GreenfootImage bild;
        int width;
        int height;

        public GroﬂesDisplay() {
            bild = getImage();
            this.width = width;
            this.height = height;
        }

        public void anzeigen(String pText) {
            loeschen();
            bild = new GreenfootImage(pText, 30, null, null);
            getImage().drawImage(bild, 20, 6);

        }

        public void loeschen() {
            getImage().clear();
            setImage("images/Uncool/nachricht.png");
        }
    }
    class KleinesDisplay extends Actor {
        GreenfootImage bild;
        int width;
        int height;

        public KleinesDisplay() {
            bild = getImage();
            this.width = width;
            this.height = height;
        }

        public void anzeigen(String pText) {
            loeschen();
            bild = new GreenfootImage(pText, 26, null, null);
            getImage().drawImage(bild, 0, 10);

        }

        public void loeschen() {
            getImage().clear();
            setImage("images/Uncool/kleineNachricht.png");
        }
    }
}