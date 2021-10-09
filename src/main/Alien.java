package main;

import java.awt.Color;
import java.util.Random;

/**
 * The alien class for the arcade game.
 * 
 * This is the enemy that can shoot and moves randomly.
 * 
 * Example: Alien test = new Alien(300,300);
 * 
 * @author Liam Waterbury and Sam Johnson
 *
 */
public class Alien extends GameObject {
	// final variables
	private static final int HEIGHT = 30;
	private static final int WIDTH = 30;
	private static final int VELOCITY = 15;
	private static final Color COLOR = Color.orange;
	// instance variables
	private int moveSleep;
	private int direction;
	private boolean dead;
	private Random rnd = new Random();

	/*
	 * ensures that an Alien with the constant dimensions, color, and velocity is
	 * initialized. It can delay the start of movement and change direction.
	 */
	public Alien(int centerX, int centerY) {
		super(centerX, centerY, HEIGHT, WIDTH, VELOCITY, COLOR);
		this.moveSleep = 6;
		this.dead = false;

		super.setImage("alienone.png");
	}// Alien constructor

	/*
	 * ensures that the alien is moved randomly
	 * 
	 * @param c - The GameComponent that will hold the aliens movement
	 */
	public void moveAlien(GameComponent c) {
		if (moveSleep == 0) {
			direction = rnd.nextInt(4);
			moveSleep = 6;
		} // end else if

		if (direction == 0 && c.checkLeft(this)) {
			super.moveLeft();
		} else if (direction == 1 && c.checkRight(this)) {
			super.moveRight();
		} else if (direction == 2 && c.checkUp(this)) {
			super.moveUp();
		} else if (direction == 3 && c.checkDown(this)) {
			super.moveDown();
		} // end else if
		moveSleep--;

		handleShoot(c);
	}// end handleRandomMovement

	/*
	 * ensures that the alien randomly shoots
	 * 
	 * @param c - The GameComponent that the new projectile is created in
	 */
	public void handleShoot(GameComponent c) {
		int shouldFire = rnd.nextInt(18);

		if (shouldFire == 1) {
			c.newAlienProjectile(this, 1);
		} else if (shouldFire == 2) {
			c.newAlienProjectile(this, -1);
		} // end if
	}// handleShoot

	/*
	 * ensures that alien reacts to being hit
	 */
	public void handleCollision() {
		this.dead = true;
	}// handleCollision

	/*
	 * returns the aliens alive/dead status
	 */
	public boolean getDead() {
		return dead;
	}// getDead

}// end class
