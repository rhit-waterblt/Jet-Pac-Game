package main;

import java.awt.Color;

/**
 * The alien(Two) class for the arcade game.
 * 
 * This is the enemy that bounces of walls and platforms.
 * 
 * Example: AlienTwo test = new AlienTwo(300,300);
 * 
 * @author Liam Waterbury and Sam Johnson
 *
 */
public class AlienTwo extends Alien {
	// final variables
	private static final int X_VELOCITY = 8;
	private static final int Y_VELOCITY = 8;
	private static final int HEIGHT = 30;
	private static final int WIDTH = 30;
	private static final Color COLOR = Color.CYAN;
	// instance variables
	private int YVelocity;
	private int XVelocity;

	/*
	 * ensures that an AlienTwo has constant dimensions, color, and velocity is
	 * initialized (X and Y).
	 * 
	 */
	public AlienTwo(int centerX, int centerY) {
		super(centerX, centerY);
		super.setColor(COLOR);
		super.setHeight(HEIGHT);
		super.setHeight(WIDTH);
		this.XVelocity = X_VELOCITY;
		this.YVelocity = Y_VELOCITY;

		super.setImage("alientwo.png");
	}// AlienTwo constructor

	/*
	 * ensures that the alien is moved by bouncing
	 * 
	 * @param c - The GameComponent that will hold the aliens movement
	 */
	public void moveAlien(GameComponent c) {
		// if(bounceSleep==0) {
		if (!c.checkUp(this)) {
			YVelocity *= -1;
		} else if (!c.checkDown(this)) {
			YVelocity *= -1;
		} else if (!c.checkRight(this)) {
			XVelocity *= -1;
		} else if (!c.checkLeft(this)) {
			XVelocity *= -1;
		} // end else if
			// bounceSleep = 4;
		super.setX(super.getX() + XVelocity);
		super.setY(super.getY() - YVelocity);
		// bounceSleep--;
	}// end moveAlienTwo

}// end class
