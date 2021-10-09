package main;

import java.awt.Color;

/*
 * Platform class for the JetPac game
 * 
 * Serves as platforms for the hero to interact with.
 * 
 * Example: Platform test = new Platform(10, 10, 10, 10, Color.red);
 * 
 * @author Liam Waterbury and Sam Johnson
 */
public class Platform extends GameObject {

	/*
	 * ensures that a playform with the specified dimensions, location, and color is
	 * initialized.
	 * 
	 * @param centerX - the x coordinate of the top left corner of the Platform.
	 * 
	 * @param centerY - the y coordinate of the top left corner of the Platform.
	 * 
	 * @param height - the height of the Platform
	 * 
	 * @param width - the width of the Platform
	 * 
	 * @param color - the color of the Platform
	 */
	public Platform(int centerX, int centerY, int height, int width, Color color) {
		super(centerX, centerY, height, width, 2, color);
		super.setImage("platform.png");
	}// Platform Constructor

	/*
	 * ensures that a GameObject does not enter the platform from the top.
	 * 
	 * @param g - The GameOject that is colliding with the platform.
	 */
	public boolean checkTop(GameObject g) {
		if (getY() < g.getY() + g.getHeight() && getY() + getHeight() > g.getY() + g.getHeight()
				&& getX() < g.getX() + g.getWidth() && getX() + getWidth() > g.getX()) {
			g.setY(getY() - g.getHeight() + 1);
			return false;
		} // end if
		return true;
	}// checkTop

	/*
	 * ensures that a GameObject does not enter the platform from the right.
	 * 
	 * @param g - The GameOject that is colliding with the platform.
	 */
	public boolean checkRight(GameObject g) {
		if (g.getX() == getX() + getWidth() && g.getY() + g.getHeight() >= getY() && g.getY() <= getY() + getHeight()) {
			return false;
		} // end if
		return true;
	}// checkRight

	/*
	 * ensures that a GameObject does not enter the platform from the left.
	 * 
	 * @param g - The GameOject that is colliding with the platform.
	 */
	public boolean checkLeft(GameObject g) {
		if (g.getX() + g.getWidth() == getX() && g.getY() + g.getHeight() >= getY()
				&& g.getY() <= getY() + getHeight()) {
			return false;
		} // end if
		return true;
	}// check Left

	/*
	 * ensures that a GameObject does not enter the platform from the bottom.
	 * 
	 * @param g - The GameOject that is colliding with the platform.
	 */
	public boolean checkBottom(GameObject g) {
		if (getY() < g.getY() && getY() + getHeight() > g.getY() && getX() < g.getX() + g.getWidth()
				&& getX() + getWidth() > g.getX()) {
			g.setY(getY() + getHeight());
			return false;
		} // end if
		return true;
	}// checkBottom

}// end class
