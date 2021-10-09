package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * Hero class that extends the GameObject Class
 * 
 * The main character for the JetPac game
 * 
 * Example: Hero test = new Hero();
 * 
 * @author Liam Waterbury and Sam Johnson
 * 
 */
public class Hero extends GameObject {

	// final variables
	private static int X_LOCATION = 400;
	private static int Y_LOCATION = 300;
	private static int WIDTH = 50;
	private static int LENGTH = 50;
	private static int VELOCITY = 10;
	private static Color COLOR = Color.blue;
	private static int Y_VELOCITY = 1;
	private static int Y_ACCELERATION = 1;
	private static int VELCAP = 20;
	private static int NUM_LIVES = 200;

	// instance variables
	private int yVelocity;
	private int yAcceleration;
	private int lives;
	private int score;
	private String initials;
	private boolean hasFuel;
	private boolean hasPart;
	private Fuel can;
	private RocketPart part;
	private boolean inShip;

	/*
	 * ensures that a Hero with the constant dimensions, color, and velocity is
	 * initialized.
	 * 
	 */
	public Hero() {
		super(X_LOCATION, Y_LOCATION, WIDTH, LENGTH, VELOCITY, COLOR);
		this.yVelocity = Y_VELOCITY;
		this.yAcceleration = Y_ACCELERATION;
		this.lives = NUM_LIVES;
		this.score = 0;
		this.hasFuel = false;
		this.inShip = false;
		this.can = null;
		this.hasPart = false;
		this.part = null;

		super.setImage("hero.png");
	}// Hero constructor

	/*
	 * ensures that a Hero falls when it is called.
	 * 
	 */
	public void fall() {
		super.setY(super.getY() + yVelocity);
		if (yVelocity < VELCAP) {
			yVelocity += yAcceleration;
		}
	}// fall

	/*
	 * Makes the Y velocity back to the original value.
	 * 
	 */
	public void resetYVelocity() {
		this.yVelocity = Y_VELOCITY;
	}// resetYVelocity

	/*
	 * ensures that the hero is properly affected by a collision
	 */
	public void handleCollision() {
		if (!inShip) {
			lives--;
		} // end if

	}// loseLife

	/*
	 * ensures that the hero is properly affected by a collision with a rocket
	 * 
	 * @param r - The rocket involved in the collision
	 */
	public void handleCollision(Rocket r) {
		this.inShip = true;
		this.setX(r.getX() + r.getWidth() - this.getWidth());
		this.setY(r.getY() + r.getHeight() - this.getHeight());
	}// loseLife

	/*
	 * ensures that the hero releases the possessed fuel can.
	 */
	public void dropFuel() {
		if (can != null) {
			can.setY(super.getY() + super.getHeight() + can.getHeight());
			hasFuel = false;
		} else if (part != null) {
			part.setY(super.getY() + super.getHeight() + part.getHeight());
			hasPart = false;
		} // end else if
	}// end

	/*
	 * ensures that the heros lives are returned
	 */
	public int getLives() {
		return lives / 2;
	}// getLives

	/*
	 * ensures that score is increased
	 */
	public void increaseScore() {
		score += 10;
	}// increaseScore

	/*
	 * ensures that hasFuel is set to true;
	 */
	public void hasFuel() {
		this.hasFuel = true;
	}// has fuel

	/*
	 * ensures that hasPart is set to true;
	 */
	public void hasPart() {
		this.hasPart = true;
	}// has fuel

	/*
	 * ensures that hasFuel is returned
	 */
	public boolean checkFuel() {
		return hasFuel;
	}// checkFuel

	/*
	 * ensures that hasPart is returned
	 */
	public boolean checkPart() {
		return hasPart;
	}// checkFuel

	/*
	 * ensures that possessed fuel is set to x
	 * 
	 * @param x - the fuel to be possessed.
	 */
	public void setCan(Fuel x) {
		can = x;
		hasFuel();
	}// setCan

	/*
	 * ensures that possessed part is set to x
	 * 
	 * @param x - the part to be possessed.
	 */
	public void setPart(RocketPart x) {
		part = x;
		hasPart();
	}// setPart

	/*
	 * ensures that the Hero's instance variables are reset
	 */
	public void reset() {
		this.lives = NUM_LIVES;
		this.score = 0;
		this.initials = "";
		super.setColor(COLOR);
		super.setX(X_LOCATION);
		super.setY(Y_LOCATION);
		this.inShip = false;
		this.yVelocity = Y_VELOCITY;
		super.setImage("hero.png");
	}// reset

	/*
	 * returns inShip
	 */
	public boolean checkInShip() {
		return this.inShip;
	}// checkInShip

	/*
	 * ensures that inShip is reset to false.
	 */
	public void resetInShip() {
		this.inShip = false;
	}// resetInShip

	/*
	 * returns score
	 */
	public int getScore() {
		return score;
	}// getScore

	/*
	 * returns initials
	 */
	public String getInitials() {
		return initials;
	}// getInitials

	/*
	 * sets initials
	 * 
	 * @param initials - the String that intitials is set to
	 */
	public void setInitials(String initials) {
		this.initials = initials;
	}// setInitials

}// end class
