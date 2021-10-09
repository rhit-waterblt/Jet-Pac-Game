package main;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The Rocket class for the arcade game.
 * 
 * This is the rocket that collects fuel and the hero to take them to the next
 * level.
 * 
 * Example: Rocket test = new Rocket();
 * 
 * @author Liam Waterbury and Sam Johnson
 *
 */
public class Rocket extends GameObject {
	// final variables
	private static final int X_LOCATION = 400;
	private static final int Y_LOCATION = 500;
	private static final int WIDTH = 60;
	private static final int LENGTH = 80;
	private static final int VELOCITY = 10;
	private static final Color COLOR = Color.RED;
	// instance variables
	private int numGas;
	private int buildState;
	private boolean rise;

	/*
	 * ensures that a rocket is initialized
	 */
	public Rocket() {
		super(X_LOCATION, Y_LOCATION, LENGTH, WIDTH, VELOCITY, COLOR);
		this.numGas = 0;
		this.rise = false;
		this.buildState = 0;
		super.setImage("rocketsmall.png");
	}// rocket

	/*
	 * ensures that a collision with another object is properly handled and it is
	 * fueled.
	 * 
	 * @param - fuel object that the rocket collides with
	 */
	public void handleCollision(Fuel f) {
		if (!f.isBeenUsed()) {// makes sure the fuel is only used once
			numGas++;
			if (numGas == 1) {
				super.setColor(Color.orange);
				super.setImage("rocketorange.png");
			} else if (numGas == 2) {
				super.setColor(Color.yellow);
				super.setImage("rocketyellow.png");
			} else if (numGas >= 3) {
				super.setColor(Color.green);
				super.setImage("rocketgreen.png");
			} // end
			checkRise();
		} // end if
	}// handleCollision

	/*
	 * ensures that a collision with another object is properly handled and it is
	 * fueled.
	 * 
	 * @param - fuel object that the rocket collides with
	 */
	public void handleCollision(RocketPart f) {
		if (!f.isBeenCollected()) {// makes sure that part is only used once
			f.setBeenCollected(true);
			buildState++;
			if (buildState == 1) {
				super.setImage("rockethalf.png");
			} else if (buildState >= 2) {
				super.setImage("rocketred.png");
			} // end if
		}
	}// handleCollision

	/*
	 * ensures that the rocket will rise if it has the hero and enough fuel.
	 */
	public boolean willRise(Hero h) {
		if (rise && h.checkOverlap(this)) {
			return true;
		} else {
			return false;
		} // end else if
	}// end will rise

	/*
	 * ensures that rise is checked and set to true if the ship is properly fueled.
	 */
	public void checkRise() {
		if (super.getColor() == Color.green) {
			this.rise = true;
		} // end if
	}// checkRise

	/*
	 * ensures that rise is returned
	 */
	public boolean getRise() {
		return this.rise;
	}// getRise

	/*
	 * ensures that rise is set to b
	 * 
	 * @param b - boolean value that rise is set to
	 */
	public void setRise(boolean b) {
		this.rise = b;
	}// setRise

	/*
	 * ensures that buildState is returned
	 */
	public int getBuildState() {
		return buildState;
	}// getBuildState

	/*
	 * ensures that buildState is set to b
	 * 
	 * @param buildState - int value that rise is set to
	 */
	public void setBuildState(int buildState) {
		this.buildState = buildState;
	}// setBuildState

}// end class
