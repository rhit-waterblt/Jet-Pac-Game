package main;

import java.awt.Color;

/**
 * The RocketPart class for the arcade game.
 * 
 * This is the RocketPart that is used to rebuild the Rocket.
 * 
 * Example: RocketPart test = new RocketPart();
 * 
 * @author Liam Waterbury and Sam Johnson
 *
 */
public class RocketPart extends Fuel {
	// final variables
	private static final int HEIGHT = 30;
	private static final int WIDTH = 30;
	private static final Color COLOR = Color.gray;

	/*
	 * ensures that new RocketPart is initialized at a specified location
	 * 
	 * @param centerx - the x coord of the top right corner
	 * 
	 * @param centery - the y coord of the top right corner
	 */
	public RocketPart(int centerX, int centerY) {
		super(centerX, centerY);
		super.setHeight(HEIGHT);
		super.setWidth(WIDTH);
		super.setColor(COLOR);
		super.setImage("rocketpart.png");
	}// end rocketPart

	/*
	 * ensures that the RocketPart properly collides and is grabbed by the hero
	 * 
	 * @param g - the Hero class that grabs the RocketPart
	 */
	public void handleCollision(Hero g) {
		this.setX(g.getX() + g.getWidth() - this.getWidth());
		this.setY(g.getY() + g.getHeight() - this.getHeight());
	}// handle collision

}// end class
