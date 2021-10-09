package main;

import java.awt.Color;

/**
 * The Fuel class for the arcade game.
 * 
 * This is the fuel needed for the rocket ship to rise.
 * 
 * Example: Fuel test = new Fuel(300,300);
 * 
 * @author Liam Waterbury and Sam Johnson
 *
 */
public class Fuel extends GameObject {
	// final variables
	private static final int HEIGHT = 20;
	private static final int WIDTH = 20;
	private static final int VELOCITY = 0;
	private static final Color COLOR = Color.magenta;

	// instance variables
	private int yVelocity;
	private boolean toRemove;
	private boolean beenCollected;
	private boolean beenUsed;

	/*
	 * ensures that a new fuel container is initiated
	 * 
	 * @param centerx is the x coord.
	 * 
	 * @param centery is the y coord.
	 */
	public Fuel(int centerX, int centerY) {
		super(centerX, centerY, HEIGHT, WIDTH, VELOCITY, COLOR);
		// TODO Auto-generated constructor stub
		this.yVelocity = 5;
		this.toRemove = false;
		this.beenCollected = false;
		this.beenUsed = false;
		super.setImage("fuel.png");
	}// fuelConstructor

	/*
	 * ensures that the fuel is grabbed by the hero
	 * 
	 * @param hero that grabs the fuel
	 */
	public void handleCollision(Hero g) {
		if (!this.beenCollected) {
			this.beenCollected = true;
			g.increaseScore();
		}
		this.setX(g.getX() + g.getWidth() - this.getWidth());
		this.setY(g.getY() + g.getHeight() - this.getHeight());
		// g.hasFuel();
	}// handle collision

	/*
	 * ensures that the fuel is inserted into the rocket
	 * 
	 * @param r - the rocket that is collided with
	 */
	public void handleCollision(Rocket r) {
		this.setBeenUsed(true);
		this.toRemove = true;
	}// handle collision

	/*
	 * ensures that a Fuel falls when it is called.
	 * 
	 */
	public void fall() {
		super.setY(super.getY() + yVelocity);
	}// fall

	/*
	 * returns if the fuel can should be removed.
	 */
	public boolean shouldRemove() {
		return toRemove;
	}// should remove

	/*
	 * returns if the fuel has already been collected
	 */
	public boolean isBeenCollected() {
		return beenCollected;
	}// isBeenCollected

	/*
	 * sets beenCollected
	 */
	public void setBeenCollected(boolean beenCollected) {
		this.beenCollected = beenCollected;
	}// setBeenCollected

	/*
	 * returns if the fuel has been used
	 */
	public boolean isBeenUsed() {
		return beenUsed;
	}// isBeenUsed

	/*
	 * sets beenUsed
	 */
	public void setBeenUsed(boolean beenUsed) {
		this.beenUsed = beenUsed;
	}// setBeenUsed

}// end class
