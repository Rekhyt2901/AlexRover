import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class Huegel extends Actor {
	private int steigung;

	public Huegel() {
		steigung = Greenfoot.getRandomNumber(30) + 31;
		setImage("images/huegelCOOL.png");
	}

	public void act() {
	}

	public int getSteigung() {
		return steigung;
	}
}
