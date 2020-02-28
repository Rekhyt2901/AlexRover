import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.FileReader;

public class Planet extends World {
	private static int zellenGroesse = 50;
	private static int x = 21;
	private static int y = 12;

	public static Tile[][] Tiles = new Tile[x][y];
	public ExtendedRover rover = new ExtendedRover();
	public Ladestation ladestation0 = new Ladestation();
	public Ladestation ladestation1 = new Ladestation();
	public Ladestation ladestation2 = new Ladestation();

	public Planet() {
		super(x, y, zellenGroesse);
		prepareTiles();
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
		// setBackground("images/bodenCOOL.png");
		setPaintOrder(String.class, Rover.class, Marke.class, Gestein.class, Huegel.class);
		Greenfoot.setSpeed(50);
		addObject(rover, 0, 6);
		addObject(ladestation0, 12, 9);
		addObject(ladestation1, 15, 6);
		addObject(ladestation2, 12, 3);
	}

	public void prepareTiles() {
		for (int i = 0; i < Tiles.length; i++) {
			for (int j = 0; j < Tiles[i].length; j++) {
				Tiles[i][j] = new Tile(i, j);
				addObject(Tiles[i][j], Tiles[i][j].getX(), Tiles[i][j].getY());
			}
		}
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

	public static int getX() {
		return x;
	}

	public static int getY() {
		return y;
	}
}