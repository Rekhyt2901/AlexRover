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

	protected void addedToWorld(World world) {
		setImage("images/LadestationCOOL.png");
		Planet.Tiles[getX()][getY()].setTemperatur(5);

		Planet.Tiles[getX() - 1][getY() + 1].setTemperatur(5);
		Planet.Tiles[getX() - 1][getY()].setTemperatur(5);
		Planet.Tiles[getX() - 1][getY() - 1].setTemperatur(5);

		Planet.Tiles[getX()][getY() - 1].setTemperatur(5);
		Planet.Tiles[getX()][getY() + 1].setTemperatur(5);

		Planet.Tiles[getX() + 1][getY() + 1].setTemperatur(5);
		Planet.Tiles[getX() + 1][getY()].setTemperatur(5);
		Planet.Tiles[getX() + 1][getY() - 1].setTemperatur(5);

		Planet.Tiles[getX()][getY()].setImage("images/bodenWarmCOOL.png");

		Planet.Tiles[getX() - 1][getY() + 1].setImage("images/bodenWarmCOOL.png");
		Planet.Tiles[getX() - 1][getY()].setImage("images/bodenWarmCOOL.png");
		Planet.Tiles[getX() - 1][getY() - 1].setImage("images/bodenWarmCOOL.png");

		Planet.Tiles[getX()][getY() - 1].setImage("images/bodenWarmCOOL.png");
		Planet.Tiles[getX()][getY() + 1].setImage("images/bodenWarmCOOL.png");

		Planet.Tiles[getX() + 1][getY() + 1].setImage("images/bodenWarmCOOL.png");
		Planet.Tiles[getX() + 1][getY()].setImage("images/bodenWarmCOOL.png");
		Planet.Tiles[getX() + 1][getY() - 1].setImage("images/bodenWarmCOOL.png");
	}
}
