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
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/*
 * 
 * IntroComponent class for the Jet-Pac game
 * 
 * This handles creating the animated intro screen and displaying scoreboard and helpful information
 * 
 * Example: IntroComponent g = new IntroComponent();
 * 
 * @author Liam Waterbury and Sam Johnson
 * 
 */
public class IntroComponent extends JComponent {
	// instance variables
	private Rocket rocket;

	/*
	 * ensures that a new IntroComponent is initialized.
	 */
	public IntroComponent() {
		this.rocket = new Rocket();
		rocket.setImage("rocketred.png");
	}// end introViewer constructor

	/*
	 * @Override ensures that hero and platforms are drawn onto the graphic.
	 * 
	 * @param g - the graphic to be drawn upon.
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		updateBackground(g2);
		try {
			updateAnimation(g2);
		} catch (InterruptedException e) {
		}
		updateText(g2);
		//updateScoreList(g2);
	}// paint component

	/*
	 * ensures that the text is updated on the screen
	 * 
	 * @param g2d - the graphics that the text is displayed on
	 */
	public void updateText(Graphics2D g2d) {
		// need to center
		g2d.setColor(Color.green);
		g2d.drawString("Welcome to Jet-Pac!", 320, 150);
		g2d.drawString("Use the arrow keys to move and spacebar to shoot, down arrow will drop any collected fuel!",
				150, 200);
		g2d.drawString("Build the Rocket with parts and Collect fuel and drop it into the rocket!", 200, 250);
		g2d.drawString("Once the rocket window turns green, hop in and make it to the next level!", 200, 300);
		g2d.drawString("Be careful, you have three lives and various aliens will try to stop you!", 200, 350);
		g2d.drawString("Collect gold coins to increase your score!", 270, 400);
		g2d.drawString("Press spacebar to begin!", 310, 500);
	}// end updateText

	/*
	 * ensures that the rocket and movement is updated on the screen
	 * 
	 * @param g2d - the graphics that the rocket is displayed on
	 */
	public void updateAnimation(Graphics2D g2d) throws InterruptedException {
		rocket.drawOn(g2d);
		rocket.moveUp();
		checkResetRocket();
	}// updateAnimation

	/*
	 * ensures that the rocket is properly reset on the upper and lower boundaries
	 * of the screen
	 */
	public void checkResetRocket() throws InterruptedException {
		Random rnd = new Random();
		if (rocket.getY() + rocket.getHeight() < 0) {
			rocket.setY(getHeight());
			rocket.setX(50 + rnd.nextInt(700));
		} // end if
		Thread.sleep(10);
		repaint();
	}// end checkResetRocket();

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

	/*
	 * ensures that background is updated on the screen
	 * 
	 * @param g2d - the graphic that the background is displayed on
	 */
	public void updateBackground(Graphics2D g2d) {
		try {
			g2d.drawImage(ImageIO.read(new File("src/images/background.jpg")), 0, 0, getWidth(), getHeight(), null);
			g2d.drawImage(ImageIO.read(new File("src/images/blackboard.png")), 10, 10, 200, 150, null);
		} catch (IOException e) {
		} // end try catch
	}// updateBackground
}// end class
