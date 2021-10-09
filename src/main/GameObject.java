package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

/*
 * Parent class for all the visual objects in the JetPac game
 * 
 * For example: Hero and Platform extend GameObject
 * 
 * Example: GameObject test = new GameObject(100, 10, 50, 50, 8, Color.red);
 * 
 * @author Liam Waterbury and Sam Johnson
 * 
 */
public class GameObject {
	// instance variables
	private int x;
	private int y;
	private int height;
	private int width;
	private int velocity;
	private Color color;
	private BufferedImage img;

	/*
	 * ensures that a GameObject is initializes with the specified dimensions,
	 * location, velocity, and color.
	 * 
	 * @param centerX - the x coordinate of the top left corner of the GameObject.
	 * 
	 * @param centerY - the y coordinate of the top left corner of the GameObject.
	 * 
	 * @param height - the height of the GameObject
	 * 
	 * @param width - the width of the GameObject
	 * 
	 * @param velocity - the velocity of the GameObject
	 * 
	 * @param color - the color of the GameObject
	 */
	public GameObject(int centerX, int centerY, int height, int width, int velocity, Color color) {
		this.x = centerX;
		this.y = centerY;
		this.height = height;
		this.width = width;
		this.velocity = velocity;
		this.color = color;
	}// GameObject

	/*
	 * ensures that the GameObject is drawn on the graphic.
	 * 
	 * @param g2d - The graphic2D component that is to be drawn on.
	 */
	public void drawOn(Graphics2D g2d) {
		g2d.setColor(this.color);
		if (img != null) {
			g2d.drawImage(img, x, y, width, height, null);
		} else {
			g2d.fillRect(x, y, width, height);
		}
	}// drawOn

	/*
	 * ensures that the GameObject is moved to the right by an increment of the
	 * velocity.
	 */
	public void moveRight() {
		this.x += velocity;
	}// move right

	/*
	 * ensures that the GameObject is moved to the left by an increment of the
	 * velocity.
	 */
	public void moveLeft() {
		this.x -= velocity;
	}// move left

	/*
	 * ensures that the GameObject is moved up by an increment of the velocity.
	 */
	public void moveUp() {
		this.y -= velocity;
	}// move up

	/*
	 * ensures that the GameObject is moved down by an increment of the velocity.
	 */
	public void moveDown() {
		this.y += velocity;
	}// move up

	/*
	 * ensures that it returns whether the gameObject is touching/has collided with
	 * another object.
	 */
	public boolean checkOverlap(GameObject g) {
		if (g == null) {
			return false;
		} else if (x + width >= g.getX() && y + height > g.getY() && x < g.getX() + g.getWidth()
				&& y < g.getY() + g.getHeight()) {
			return true;
		} else {
			return false;
		}
	}// collision

	/*
	 * ensures that a new projectile is created.
	 * 
	 * @param direction - the direction that the bullet is fired.
	 */
	public Projectile fireProjectile(int direction) {
		return new Projectile(getX(), getY() + height / 2, direction);
	}// fireProjectile

	// getters and setters

	/*
	 * returns the x position of the game object (top left corner of object)
	 */
	public int getX() {
		return x;
	}// getX

	/*
	 * @param x - The new x position of the game object (top left corner of object)
	 */
	public void setX(int x) {
		this.x = x;
	}// setX

	/*
	 * returns the y position of the game object (top left corner of object)
	 */
	public int getY() {
		return y;
	}// getY

	/*
	 * @param y - The new y position of the game object (top left corner of object)
	 */
	public void setY(int y) {
		this.y = y;
	}// setY

	/*
	 * returns the height of the gameObject
	 */
	public int getHeight() {
		return height;
	}// getHeight

	/*
	 * @param height - sets the new height of the gameObject
	 */
	public void setHeight(int height) {
		this.height = height;
	}// setHeight

	/*
	 * returns the width of the gameObject
	 */
	public int getWidth() {
		return width;
	}// getWidth

	/*
	 * @param width - sets the new width of the gameObject
	 */
	public void setWidth(int width) {
		this.width = width;
	}// setWidth

	/*
	 * returns the velocity of the gameObject
	 */
	public int getVelocity() {
		return velocity;
	}// getVelocity

	/*
	 * @param velocity - sets the new velocity of the gameObject
	 */
	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}// setVelocity

	/*
	 * returns the color of the gameObject
	 */
	public Color getColor() {
		return color;
	}// getColor

	/*
	 * @param color - sets the new color of the gameObject
	 */
	public void setColor(Color color) {
		this.color = color;
	}// setColor

	/*
	 * ensures that determined image is set for the GameObject
	 * 
	 * @param - the string that shows the file location
	 */
	public void setImage(String img) {
		try {
			this.img = ImageIO.read(new File("src/images/" + img));
		} catch (IOException e) {
			// System.out.println("No image found");
		} // end try catch
	}// setImage

	/*
	 * ensures that the image is set to null.
	 */
	public void clearImage() {
		this.img = null;
	}// clearImage
}// end class
