package main;

import java.awt.Color;

/**
 * The Valuable class for the arcade game.
 * 
 * This is the Valuable that increases the hero's score if collected.
 * 
 * Example: Valuable test = new Valuable(100, 100);
 * 
 * @author Liam Waterbury and Sam Johnson
 *
 */
public class Valuable extends GameObject {
	// final variables
	private static final int HEIGHT = 20;
	private static final int WIDTH = 20;
	private static final int VELOCITY = 0;
	private static final Color COLOR = Color.yellow;
	// instance variables
	private boolean toRemove;

	/*
	 * ensures that a valuable with the specificed coordinates is initialized.
	 * 
	 * @param centerx - xcoord
	 * 
	 * @param centery - ycoord
	 */
	public Valuable(int centerX, int centerY) {
		super(centerX, centerY, HEIGHT, WIDTH, VELOCITY, COLOR);
		toRemove = false;

		super.setImage("coin.png");
	}// valuable

	/*
	 * ensures that a collision with a hero is properly handled.
	 * 
	 * @param g - The hero collided with
	 */
	public void handleCollision(Hero g) {
		g.increaseScore();
		toRemove = true;
	}// handleCollision

	/*
	 * ensures that toRemove is returned
	 */
	public boolean getToRemove() {
		return toRemove;
	}// getToRemove

}// end
