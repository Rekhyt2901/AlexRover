import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Ladestation here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Ladestation extends Actor {
    /**
     * Act - do whatever the Ladestation wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        // Add your action code here.
    }

    public Ladestation() {
    }

    private void setTileToRightTemperatur(int x, int y) {
        if(getWorld().getWidth() > x && getWorld().getHeight() > y && x >= 0 && y >= 0) {
            Planet.Tiles[x][y].setTemperatur(5);  
        }
    }
    
    private void setTileToRightImage(int x, int y) {
        if(getWorld().getWidth() > x && getWorld().getHeight() > y && x >= 0 && y >= 0) {
            Planet.Tiles[x][y].setImage("images/bodenWarmCOOL.png");
        }
    }
    
    protected void addedToWorld(World world) {
        setImage("images/ladestationCOOL.png");
        Planet.Tiles[getX()][getY()].setHasLadestation(true);
        
        setTileToRightTemperatur(getX() + 1, getY() + 1);
        setTileToRightTemperatur(getX() + 1, getY());
        setTileToRightTemperatur(getX() + 1, getY() - 1);
        
        setTileToRightTemperatur(getX(), getY() + 1);
        setTileToRightTemperatur(getX(), getY());
        setTileToRightTemperatur(getX(), getY() - 1);
        
        setTileToRightTemperatur(getX() - 1, getY() + 1);
        setTileToRightTemperatur(getX() - 1, getY());
        setTileToRightTemperatur(getX() - 1, getY() - 1);
        
        
        setTileToRightImage(getX() + 1, getY() + 1);
        setTileToRightImage(getX() + 1, getY());
        setTileToRightImage(getX() + 1, getY() - 1);
        
        setTileToRightImage(getX(), getY() + 1);
        setTileToRightImage(getX(), getY());
        setTileToRightImage(getX(), getY() - 1);
        
        setTileToRightImage(getX() - 1, getY() + 1);
        setTileToRightImage(getX() - 1, getY());
        setTileToRightImage(getX() - 1, getY() - 1);
    }
}
