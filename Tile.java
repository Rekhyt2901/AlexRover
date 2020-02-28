import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Tile extends Actor {
	private Integer temperatur;
	private int x;
	private int y;

	public void act() {

	}

	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
		temperatur = Greenfoot.getRandomNumber(88) - 73;
	}

	public void addedToWorld(World world) {
		if (temperatur > 0)
			setImage("images/bodenWarm.png");
		else if (temperatur > -50)
			setImage("images/bodenCOOL.png");
		else
			setImage("images/bodenKalt.png");
		world = getWorld();
		world.addObject(this, x, y);
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
