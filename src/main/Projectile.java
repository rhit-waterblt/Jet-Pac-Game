package main;

import java.awt.Color;

/**
 * The projectile class for the arcade game.
 * 
 * These are the bullets that are fired by the hero and by the alien
 * 
 * Example: Projectile test = new Alien(300,300, 1);
 * 
 * @author Liam Waterbury and Sam Johnson
 *
 */
public class Projectile extends GameObject {
	// final variables
	private static final int HEIGHT = 10;
	private static final int WIDTH = 20;
	private static final int VELOCITY = 20;
	private static final Color COLOR = Color.red;

	/*
	 * ensures that a Hero with the constant dimensions, color, and velocity is
	 * initialized.
	 * 
	 */
	public Projectile(int centerX, int centerY, int direction) {
		super(centerX, centerY, HEIGHT, WIDTH, direction * VELOCITY, COLOR);
		if (direction == -1) {
			super.setX((int) (centerX - .5 * getWidth()));
		} else {
			super.setX((int) (centerX + 1.5 * getWidth()));
		} // end else if

		super.setImage("projectile.png");
	}// Projectile constructor

}// end class
