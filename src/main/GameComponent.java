package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * GameComponent class for the JetPac main
 * 
 * This handles the graphics and change of graphics for the game.
 * 
 * Example: GameComponent g = new GameComponent();
 * 
 * @author Liam Waterbury and Sam Johnson
 * 
 */
public class GameComponent extends JComponent {

	// instance variables
	private Hero myHero;
	private Rocket myRocket;
	private boolean isGameOver;
	private int currLevel;
	private Random rnd;
	private ArrayList<GameObject> platforms = new ArrayList<GameObject>();
	private ArrayList<GameObject> alienBullets = new ArrayList<GameObject>();
	private ArrayList<GameObject> heroBullets = new ArrayList<GameObject>();
	private ArrayList<GameObject> bulletsToRemove = new ArrayList<GameObject>();
	private ArrayList<GameObject> aliens = new ArrayList<GameObject>();
	private ArrayList<GameObject> fuelTanks = new ArrayList<GameObject>();
	private ArrayList<GameObject> valuableItems = new ArrayList<GameObject>();
	private ArrayList<GameObject> rocketParts = new ArrayList<GameObject>();

	/*
	 * ensures: That a game component is initialized.
	 */
	public GameComponent() {
		this.myHero = new Hero();
		this.isGameOver = false;
		this.myRocket = null;
		this.currLevel = 1;
		this.rnd = new Random();
	}// GameComponent

	/*
	 * @Override ensures that hero and platforms are drawn onto the graphic.
	 * 
	 * @param g - the graphic to be drawn upon.
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		updateBackground(g2);
		myHero.drawOn(g2);
		myRocket.drawOn(g2);
		updatePlatforms(g2);
		updateBullets(g2);
		updateAliens(g2);
		updateValuableItems(g2);
		updateRocket(g2);
		updateHero(g2);
		updateScore(g2);
	}// paint component

	/*
	 * ensures that the screen is drawn and updates any graphics (repaint).
	 */
	public void drawScreen() {
		this.repaint();
	}// draw screen

	/*
	 * ensures that all the platforms are drawn on the graphic
	 * 
	 * @param g2d - The graphic that the platforms are drawn on.
	 */
	public void updatePlatforms(Graphics2D g2d) {
		for (GameObject plat : platforms) {
			plat = (Platform) plat;
			plat.drawOn(g2d);
		}
	}// updatePlatforms

	/*
	 * ensures that the game state is updated
	 * 
	 * will be used for gravity and other tick related events.
	 */
	public void updateState() {
		handleCollisions();
		drawScreen();
		checkFuel();
	}// update state

	/*
	 * ensures that the hero is drawn on the graphic or removed if no lives left
	 * 
	 * @param g2d - The graphic that the hero is drawn on.
	 */
	public void updateHero(Graphics2D g2d) {
		if (myHero.getLives() < 0) {
			isGameOver = true;
			g2d.setColor(Color.green);
			g2d.drawString("GAMEOVER", 400, 300);
			g2d.drawString("PRESS SPACE TO PLAY AGAIN", 380, 320);
			myHero.setColor(Color.black);
			myHero.clearImage();
		} // end
	}// updateHero

	/*
	 * ensures that the background is drawn on the graphic
	 * 
	 * @param g2d - The graphic that the background is drawn on.
	 */
	public void updateBackground(Graphics2D g2d) {
		try {
			g2d.drawImage(ImageIO.read(new File("src/images/background.jpg")), 0, 0, getWidth(), getHeight(), null);
		} catch (IOException e) {
		} // end try catch
	}// updateBackground

	/*
	 * ensures that the rocket is updated and that the next level is called if it
	 * reaches the top.
	 * 
	 * @param g2d - The graphic that the rocket is drawn on.
	 */
	public void updateRocket(Graphics2D g2d) {
		if (myRocket.willRise(myHero)) {
			myRocket.moveUp();
		} // end
			// checks if it reaches the top and which level
		if (myRocket.getY() + myRocket.getHeight() <= 0 && currLevel == 1) {
			this.readFile("level2");
			this.currLevel = 2;
			myHero.resetInShip();
		} else if (myRocket.getY() + myRocket.getHeight() <= 0 && currLevel == 2) {
			this.readFile("level3");
			this.currLevel = 3;
			myHero.resetInShip();
		} else if (myRocket.getY() + myRocket.getHeight() <= 0) {
			g2d.setColor(Color.green);
			g2d.drawString("YOU WIN!", 400, 300);
			g2d.drawString("PRESS SPACE TO PLAY AGAIN", 350, 320);

			this.isGameOver = true;
			JFrame f = new JFrame();
			String initials = JOptionPane.showInputDialog(f, "Enter Intials to Save Score:");
			myHero.setInitials(initials);

			addScoreToFile();
			updateScoreList(g2d);
		} // end
	}// updateRocket

	/*
	 * ensures that a GameObject moves in the specified direction.
	 * 
	 * @param direction - The direction that the GameObject should move.
	 */
	public void updateGameObject(String direction) {
		if (direction.equals("L") && checkLeft(myHero)) {
			myHero.moveLeft();
		} else if (direction.equals("R") && checkRight(myHero)) {
			myHero.moveRight();
		} else if (direction.equals("U") && checkUp(myHero)) {
			myHero.moveUp();
		} // end else if
		updateState();
	}// updateGameObject

	/*
	 * ensures that the score and lives graphic is updated.
	 * 
	 * @param g2d - The graphic that the scores are drawn on.
	 */
	public void updateScore(Graphics g2d) {
		g2d.setColor(Color.green);
		g2d.drawString("Score: " + myHero.getScore(), 20, 50);
		g2d.drawString("Lives: " + myHero.getLives(), 720, 50);
	}// updateScore

	/*
	 * ensures that a GameObject, Hero, updates if falling do to gravity.
	 * 
	 */
	public void updateGravity() {
		if (this.checkDown(myHero)) {
			myHero.fall();
		} // end if
		for (GameObject f : fuelTanks) {
			Fuel x = (Fuel) f;
			if (this.checkDown(x)) {
				x.fall();
			} // end if
		} // end for
		for (GameObject x : rocketParts) {
			RocketPart g = (RocketPart) x;
			if (this.checkDown(x)) {
				g.fall();
			} // end if
		} // end for
		updateState();
	}// updateGravity

	/*
	 * ensures that a GameObject, projectile, gets updated as it moves.
	 * 
	 * @param g2d - The Graphics2D used by the window.
	 */
	public void updateBullets(Graphics2D g2d) {
		if (heroBullets.size() != 0) {
			for (GameObject bul : heroBullets) {
				Projectile b = (Projectile) bul;
				b.setX(b.getX() + b.getVelocity());
				if (!checkRight(b)) {
					bulletsToRemove.add(b);
				} // end if
				b.drawOn(g2d);
			} // end for
		} // end if
		if (alienBullets.size() != 0) {
			for (GameObject bul : alienBullets) {
				Projectile b = (Projectile) bul;
				b.setX(b.getX() + b.getVelocity());
				if (!checkRight(b)) {
					bulletsToRemove.add(b);
				} // end if
				b.drawOn(g2d);
			} // end for
		} // end if
			// remove bullets
		for (GameObject bullet : bulletsToRemove) {
			this.alienBullets.remove(bullet);
			this.heroBullets.remove(bullet);
		} // end for
		bulletsToRemove.clear();
	}// end updateBullets

	/*
	 * ensures that a all valuable items and fuel are properly updated.
	 * 
	 * @param g2d - The Graphics2D used by the window.
	 */
	public void updateValuableItems(Graphics2D g2d) {
		ArrayList<GameObject> toRemove = new ArrayList<GameObject>();
		for (GameObject v : valuableItems) {
			Valuable g = (Valuable) v;
			if (g.getToRemove()) {
				toRemove.add(g);
			} // end if
		} // end for
		for (GameObject v : fuelTanks) {
			Fuel g = (Fuel) v;
			if (g.shouldRemove()) {
				toRemove.add(g);
			} // end if
		} // end for
		for (GameObject x : rocketParts) {
			RocketPart g = (RocketPart) x;
			if (g.shouldRemove()) {
				toRemove.add(g);
			} // end if
		} // end for
		for (GameObject v : toRemove) {
			valuableItems.remove(v);
			fuelTanks.remove(v);
			rocketParts.remove(v);
		} // end for
		for (GameObject g : fuelTanks) {
			g.drawOn(g2d);
		} // end for
		for (GameObject g : valuableItems) {
			g.drawOn(g2d);
		} // end for
		for (GameObject g : rocketParts) {
			g.drawOn(g2d);
		} // end for
		toRemove.clear();
	}// updateValuableItems

	/*
	 * ensures that a GameObject, Alien, gets updated as it moves. This updates both
	 * kinds of aliens.
	 * 
	 * @param g2d - The Graphics2D used by the window.
	 */
	public void updateAliens(Graphics2D g2d) {
		ArrayList<Alien> aliensToRemove = new ArrayList<Alien>();
		for (GameObject al : aliens) {
			Alien a = (Alien) al;
			if (a.getDead()) {
				aliensToRemove.add(a);
			} // end if
		} // end for
		for (int i = 0; i < aliensToRemove.size(); i++) {
			int temp = 50 + rnd.nextInt(700);
			if (rnd.nextInt(2) == 1) {
				aliens.add(new Alien(temp, 300));
			} else {
				aliens.add(new AlienTwo(temp, 300));
			} // end else if
		} // end for
		for (GameObject a : aliensToRemove) {
			aliens.remove(a);
		} // end for
		for (GameObject al : aliens) {
			Alien a = (Alien) al;
			a.moveAlien(this);
			a.drawOn(g2d);
		} // end for
	}// updateAliens

	/*
	 * ensures that any collisions are properly reported and handled.
	 * 
	 */
	public void handleCollisions() {
		// alienCollisions
		for (GameObject al : aliens) {
			Alien a = (Alien) al;
			if (myHero.checkOverlap(a)) {
				myHero.handleCollision();
				a.handleCollision();
			} // end if
		} // end for
			// alienBullets
		for (GameObject b : alienBullets) {
			if (myHero.checkOverlap(b)) {
				bulletsToRemove.add(b);
				myHero.handleCollision();
			} // end if
		} // end for
			// heroBullets
		for (GameObject b : heroBullets) {
			for (GameObject al : aliens) {
				Alien a = (Alien) al;
				if (a.checkOverlap(b)) {
					bulletsToRemove.add(b);
					a.handleCollision();
					myHero.increaseScore();
				} // end if
			} // end for
		} // end for
			// fuel
		for (GameObject f : fuelTanks) {
			Fuel x = (Fuel) f;
			if (myRocket.checkOverlap(x)) {
				myRocket.handleCollision(x);
				x.handleCollision(myRocket);
			} // end if
			if (myHero.checkOverlap(x) && myRocket.getBuildState() >= 2) {
				x.handleCollision(myHero);
				myHero.setCan(x);
			} // end if
		} // end for
			// rocketParts
		for (GameObject f : rocketParts) {
			RocketPart x = (RocketPart) f;
			if (myRocket.checkOverlap(x)) {
				myRocket.handleCollision(x);
				x.handleCollision(myRocket);
			} // end if
			if (myHero.checkOverlap(x)) {
				x.handleCollision(myHero);
				myHero.setPart(x);
			} // end if
		} // end for
			// coins
		for (GameObject v : valuableItems) {
			Valuable x = (Valuable) v;
			if (myHero.checkOverlap(x)) {
				x.handleCollision(myHero);
			} // end if
		} // end for
			// hero and rocket
		if (myHero.checkOverlap(myRocket) && myRocket.getRise()) {
			myHero.handleCollision(myRocket);
		} // end
	}// handleCollisions

	/*
	 * ensures that a GameObject, projectile, gets added to the herobullets list.
	 * 
	 * @param g - The GameOject to be added.
	 * 
	 * @param direction - The direction it is traveling.
	 */
	public void newHeroProjectile(GameObject g, int direction) {
		heroBullets.add(g.fireProjectile(direction));
	}// newProjectile

	/*
	 * ensures that a GameObject, projectile, gets added to the alienbullets list.
	 * 
	 * @param g - The GameOject to be added.
	 * 
	 * @param direction - The direction it is traveling.
	 */
	public void newAlienProjectile(GameObject g, int direction) {
		alienBullets.add(g.fireProjectile(direction));
	}// newProjectile

	/*
	 * ensures that a GameObject does not enter the platform from the top.
	 * 
	 * @param g - The GameOject that is colliding with the platform.
	 */
	public boolean checkTopPlatform(GameObject g) {
		for (GameObject p : platforms) {
			Platform plat = (Platform) p;
			if (!plat.checkTop(g)) {
				return false;
			} // end if
		} // end for
		return true;
	}// checkTopPlatform

	/*
	 * ensures that a GameObject does not enter the platform from the bottom.
	 * 
	 * @param g - The GameOject that is colliding with the platform.
	 */
	public boolean checkBottomPlatform(GameObject g) {
		for (GameObject p : platforms) {
			Platform plat = (Platform) p;
			if (!plat.checkBottom(g)) {
				return false;
			} // end if
		} // end for
		return true;
	}// checkBottomPlatform

	/*
	 * ensures that a GameObject does not enter the platform from the left.
	 * 
	 * @param g - The GameOject that is colliding with the platform.
	 */
	public boolean checkLeftPlatform(GameObject g) {
		for (GameObject p : platforms) {
			Platform plat = (Platform) p;
			if (!plat.checkLeft(g)) {
				return false;
			} // end if
		} // end for
		return true;
	}// checkBottomPlatform

	/*
	 * ensures that a GameObject does not enter the platform from the right.
	 * 
	 * @param g - The GameOject that is colliding with the platform.
	 */
	public boolean checkRightPlatform(GameObject g) {
		for (GameObject p : platforms) {
			Platform plat = (Platform) p;
			if (!plat.checkRight(g)) {
				return false;
			} // end if
		} // end for
		return true;
	}// checkBottomPlatform

	/*
	 * ensures that the GameObject can move left without going off the screen.
	 * 
	 * @param g - The GameObject that is being checked.
	 */
	public boolean checkLeft(GameObject g) {
		if (!checkRightPlatform(g)) {
			return false;
		} else if (g.getX() <= 0) {
			return false;
		} else {
			return true;
		} // end else if
	}// checkLeft

	/*
	 * ensures that the GameObject can move right without going off the screen.
	 * 
	 * @param g - The GameObject that is being checked.
	 */
	public boolean checkRight(GameObject g) {
		if (!checkLeftPlatform(g)) {
			return false;
		} else if (g.getX() + g.getWidth() >= this.getWidth()) {
			return false;
		} else {
			return true;
		} // end else if
	}// checkRight

	/*
	 * ensures that the GameObject can move down without going off the screen.
	 * 
	 * @param g - The GameObject that is being checked.
	 */
	public boolean checkDown(GameObject g) {
		if (!checkTopPlatform(g)) {
			return false;
		} else if (g.getY() + g.getHeight() >= this.getHeight()) {
			return false;
		} else {
			return true;
		} // end else if
	}// checkDown

	/*
	 * ensures that the GameObject can move up without going off the screen.
	 * 
	 * @param g - The GameObject that is being checked.
	 */
	public boolean checkUp(GameObject g) {
		if (!checkBottomPlatform(g)) {
			return false;
		} else if (g.getY() <= 0) {
			return false;
		} else {
			return true;
		} // end else if
	}// checkUp

	/*
	 * ensures that the hero of the game is returned.
	 * 
	 */
	public Hero getHero() {
		return myHero;
	}// getHero

	/*
	 * ensures that the isGameOver is removed
	 */
	public boolean isGameOver() {
		return isGameOver;
	}// isGameOver

	/*
	 * ensures that isGameOver is set
	 */
	public void setIsGameOver(boolean b) {
		this.isGameOver = b;
	}// setIsGameOver

	/*
	 * updates if a new fuel can should be added.
	 */
	public void checkFuel() {
		int temp = rnd.nextInt(750);
		if (fuelTanks.size() == 0) {
			fuelTanks.add(new Fuel(temp, 300));
		} // end
	}// checkFuel

	/*
	 * ensures that set level is set
	 * 
	 * @param i - the level to be set
	 */
	public void setLevel(int i) {
		currLevel = i;
	}// setLevel

	/*
	 * ensures that currLevel is returned.
	 */
	public int getLevel() {
		return currLevel;
	}// getLeve

	/*
	 * ensures that the specified level is loaded with platform placements by
	 * reading a text file.
	 * 
	 * @param filename - The name of the file that the level is being read from.
	 */
	public void readFile(String filename) {
		Scanner scanner;
		try {
			scanner = new Scanner(new File("src/levels/" + filename));
			int rowCounter = 0;

			// clear lists
			platforms.clear();
			aliens.clear();
			fuelTanks.clear();
			valuableItems.clear();
			rocketParts.clear();

			// add bottom
			platforms.add(new Platform(-20, 580, 50, 820, Color.green));

			System.out.println("Reading file . . .");
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				for (int i = 0; i < line.length(); i++) {
					if (line.charAt(i) == 'X') {
						String platLength = line.substring(i);
						int j = 0;

						if (j < platLength.length()) {
							while (platLength.charAt(j) == 'X') {
								j++;
							} // end while
						} // end if

						Platform tempPlat = new Platform(50 * i, 30 + 50 * rowCounter, 20, 50 * j, Color.green);
						platforms.add(tempPlat);

						// skip the not first X's
						if (i + j <= line.length()) {
							i += j;
						} // end if
					} else if (line.charAt(i) == 'A') {
						aliens.add(new Alien(50 * i, 30 + 50 * rowCounter));
					} else if (line.charAt(i) == 'T') {
						aliens.add(new AlienTwo(50 * i, 30 + 50 * rowCounter));
					} else if (line.charAt(i) == 'C') {
						valuableItems.add(new Valuable(50 * i, 30 + 50 * rowCounter));
					} else if (line.charAt(i) == 'F') {
						fuelTanks.add(new Fuel(50 * i, 30 + 50 * rowCounter));
					} else if (line.charAt(i) == 'P') {
						rocketParts.add(new RocketPart(50 * i, 30 + 50 * rowCounter));
					} // end else if
				} // end for
				rowCounter++;
			} // end while
			myRocket = new Rocket();
		} catch (FileNotFoundException e) {
			System.out.println("File not found . . .");
			return;
		} // end try catch
	}// readFile

	/*
	 * ensures that the user inputed score and intials are added to the scores file
	 */
	public void addScoreToFile() {
		try {
			FileWriter myWriter = new FileWriter("src/levels/scores", true);
			PrintWriter printWriter = new PrintWriter(myWriter);
			printWriter.println(myHero.getScore() + "," + myHero.getInitials());
			System.out.println("saved");
			myWriter.close();
			printWriter.close();
		} catch (IOException e) {
			System.out.println("file not found");
		} // try catch

	}// addScoreToFile

	/*
	 * ensures that the scores.txt is correctly updated and the top three scores are
	 * displayed on the screen.
	 * 
	 * @param - the graphics that scores will be displayed on.
	 */
	public void updateScoreList(Graphics2D g2d) {
		Scanner scanner;
		try {
			// organize scores file in descending order
			scanner = new Scanner(new File("src/levels/scores"));
			int count = 1;
			ArrayList<Integer> list = new ArrayList<Integer>();
			HashMap<Integer, String> scores = new HashMap<Integer, String>();
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				String[] splitStr = line.split(",");
				scores.put(Integer.parseInt(splitStr[0]), splitStr[1]);
				list.add(Integer.parseInt(splitStr[0]));
			} // end while
			Collections.sort(list, Collections.reverseOrder());

			// rewrite to the scores file with correct order
			try {
				PrintWriter printWriter = new PrintWriter(new FileWriter("src/levels/scores"));
				g2d.drawImage(ImageIO.read(new File("src/images/blackboard.png")), 320, 50, 200, 150, null);
				for (int i = 0; i < 3; i++) {
					printWriter.println(list.get(i) + "," + scores.get(list.get(i)));
				} // end for
				printWriter.close();
			} catch (IOException e) {
			}
			count = 1;

			// reset scanner to begin drawings on screen
			scanner = new Scanner(new File("src/levels/scores"));
			g2d.setColor(Color.white);
			g2d.drawString("High Scores!", 350, 100);
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				String[] splitStr = line.split(",");
				g2d.drawString("Player: " + splitStr[1] + " Score: " + splitStr[0], 350, 100 + 20 * count);
				count++;
			} // end while
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found . . .");
			return;
		} // end try catch
	}// updateScoreList

}// end class
