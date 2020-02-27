import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Rover extends Actor {
    protected int Energie = 100;
    protected Integer strecke = 0;
    protected char funkFrequenz;
    protected boolean systemOK = true;

    private GroﬂesDisplay anzeige;
    private KleinesDisplay temperaturAnzeige;
    private KleinesDisplay streckenAnzeige;
    private KleinesDisplay energieAnzeige;

    public void act() {
        
    }

    public Rover() {
    }

    public void aufladen() {
        if (LadestationVorhanden("links") || LadestationVorhanden("rechts") || LadestationVorhanden("vorne")) {
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
            Planet.Tiles[getX()][getY()].setTemperatur(Planet.Tiles[getX()][getY()].getTemperatur() + 1);
            temperaturAnzeige.anzeigen(Planet.Tiles[getX()][getY()].getTemperatur().toString() + "∞C");
            Energie--;
            energieAnzeige.anzeigen(getEnergie() + "%");
            Greenfoot.delay(3);
        }
    }

    public void fahre() {
        if (Energie > 0 && Planet.Tiles[getX()][getY()].getTemperatur() > -50) {
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
        } else {
            if (Energie <= 0 && Planet.Tiles[getX()][getY()].getTemperatur() <= -50)
                anzeige.anzeigen("Keine Energie und zu niedrige Umgebungstemperatur.");
            else if (Energie <= 0)
                anzeige.anzeigen("Nicht genug Energie.");
            else if (Planet.Tiles[getX()][getY()].getTemperatur() <= -50)
                anzeige.anzeigen("Umgebungstemperatur zu niedrig.");
        }
    }

    public void drehe(String richtung) {
        if (Energie > 0 && Planet.Tiles[getX()][getY()].getTemperatur() > -50) {
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

    public void setzeMarke() {
        if (Energie > 0 && Planet.Tiles[getX()][getY()].getTemperatur() > -50) {
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
        if (Energie > 0 && Planet.Tiles[getX()][getY()].getTemperatur() > -50) {
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

        if (Energie > 0 && Planet.Tiles[getX()][getY()].getTemperatur() > -50) {
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
        if (getY() == 0) {
            setLocation(getX(), 1);
        }
        anzeige.anzeigen("Ihr wisst schon dass ich cool bin?");
        energieAnzeige.anzeigen(getEnergie() + "%");
        streckenAnzeige.anzeigen(strecke.toString() + "km");
        temperaturAnzeige.anzeigen(Planet.Tiles[getX()][getY()].getTemperatur().toString() + "∞C");
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
            setImage("images/nachricht.png");
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
            setImage("images/tempNachricht.png");
        }
    }
}