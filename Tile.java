import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Tile extends Actor
{
    private Integer temperatur;
    private int x;
    private int y;
    
    private Color red = new Color(255, 0, 0);
    private Color orange = new Color(255, 128, 0);
    private Color yellow = new Color(255, 255, 0);
    private Color green = new Color(0, 255, 0);
    private Color lightblue = new Color(100, 255, 100);
    private Color blue = new Color (0, 0, 255);
    
    public void act() 
    {
        
    }    
    
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        temperatur = Greenfoot.getRandomNumber(88) - 73;
    }
    
    public Integer getTemperatur() {
        return temperatur;
    }
    
    public void setTemperatur(int temperatur) {
        this.temperatur = temperatur;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
}
