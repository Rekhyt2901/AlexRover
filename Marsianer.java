import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Marsianer extends Actor {
    
    int fps = 1;           //makes Game run at same Speed on every Device.
    double timePerTick = 1000000000 / fps;
    double delta = 0;
    long now;
    long lastTime = System.nanoTime();

    
    public void act() {
        int random = Greenfoot.getRandomNumber(5);
        now = System.nanoTime();
        delta += (now - lastTime) / timePerTick;
        lastTime = now;
        if(delta >= 1) {
            if (!huegelVorhanden("vorne")) {
                fahre();
            }
            if (random == 0) {
                drehe("links");
            } else if (random == 1) {
                drehe("rechts");
            } else if (random == 2) {
                drehe("links");
                drehe("links");
            }
            delta--;
        }
    }

    public void addedToWorld() {
        setImage("images/marsianerCOOL.png");
    }

    public void fahre() {
        if (!huegelVorhanden("vorne")) {
            move(1);
            if (getOneIntersectingObject(ExtendedRover.class) != null) {
                getWorld().removeObject(getOneIntersectingObject(ExtendedRover.class));
            }
            Greenfoot.delay(1);
        }
    }

    public void drehe(String richtung) {
        if (richtung == "rechts") {
            setRotation(getRotation() + 90);
        } else if (richtung == "links") {
            setRotation(getRotation() - 90);
        }
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
        return false;
    }
}
