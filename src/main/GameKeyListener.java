package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * GameKeyListener listens and acts accordingly to the user inputs and directions from main
 * 
 * Implements both KeyListener and ActionListener.
 * 
 * Controls user inputs and advances ticks based on a timer
 * 
 * Example: GameKeyListener test = new GameKeyListener(gameComponent)
 * 
 * @author Liam Waterbury and Sam Johnson
 */
public class GameKeyListener implements KeyListener, ActionListener {
	private static final int NUM_LEVELS = 3;

	// instance variables
	private GameComponent c;
	private boolean isUp;
	private boolean isLeft;
	private boolean isRight;
	private boolean lastKey;

	/*
	 * ensures that a GameKeyListener for the GameComponent c is initialized.
	 * 
	 * @param c - The GameComponent that will utilize the KeyListener
	 */
	public GameKeyListener(GameComponent c) {
		this.c = c;
		this.isUp = false;
		this.isLeft = false;
		this.isRight = false;
	}// GameKeyListener constructor

	/*
	 * @Override ensures that if a key is Typed that a certain action is performed
	 * 
	 * @param e - The KeyEvent that occurred. No implementation as of yet.
	 */
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}// keyTyped

	/*
	 * @Override ensures that if a key is pressed that a certain action is
	 * performed. Either the hero moves in a direction or the level changes
	 * 
	 * @param e - The KeyEvent that occurred.
	 */
	public void keyPressed(KeyEvent e) {

		if (!c.isGameOver()) {// checks that game is running
			// check Hero Movement
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				this.isUp = true;
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				this.isLeft = true;
				this.lastKey = true;
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				this.isRight = true;
				this.lastKey = false;
			} // end else if

			// checkChangeLevel
			if (e.getKeyCode() == KeyEvent.VK_U && c.getLevel() != NUM_LEVELS) {
				System.out.println("Change Level Up");
				c.readFile("Level" + (c.getLevel() + 1));
				c.setLevel(c.getLevel() + 1);
			} else if (e.getKeyCode() == KeyEvent.VK_D && c.getLevel() != 1) {
				System.out.println("Change Level Down");
				c.readFile("Level" + (c.getLevel() - 1));
				c.setLevel(c.getLevel() - 1);
			} // end else if

			// fire bullets
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				if (this.lastKey) {
					this.c.newHeroProjectile(c.getHero(), -1);
				} else {
					this.c.newHeroProjectile(c.getHero(), 1);
				} // end else if

			} // end if

			// drop fuel
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				if (c.getHero().checkFuel() || c.getHero().checkPart()) {
					c.getHero().dropFuel();
				}
			} // end if
		} else {
			// start/reset game
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				System.out.println("START GAME");
				c.getHero().reset();
				c.readFile("Level1");
				c.setIsGameOver(false);
				c.setLevel(1);
			}
		}
	}// keyPressed

	/*
	 * @Override ensures that if a key is released that a certain action is
	 * performed
	 * 
	 * @param e - The KeyEvent that occurred.
	 * 
	 */
	public void keyReleased(KeyEvent e) {
		// set for diagonal movement
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			this.isUp = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			this.isLeft = false;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.isRight = false;
		} // end else if
	}// keyReleased

	/*
	 * @Override ensures that if an action is performed the game advancesOnetick. An
	 * action includes the Timer in main.
	 * 
	 * @param e - The ActionEvent that occurred.
	 */
	public void actionPerformed(ActionEvent e) {
		advanceOneTick();
		// move in the correct directions
		if (isUp) {
			c.updateGameObject("U");
			c.getHero().resetYVelocity();
		}
		if (isLeft) {
			c.updateGameObject("L");
		}
		if (isRight) {
			c.updateGameObject("R");
		} // end else if

	}// actionPerformed

	/*
	 * Advances the gameComponent by one tick. Regulates time for the changing of
	 * graphics and states in the gameComponent.
	 */
	public void advanceOneTick() {
		// update screen
		if (!c.isGameOver() || !c.getHero().checkInShip()) {
			this.c.updateState();
			this.c.updateGravity();
		} // end if
	}// advanceOneTick

}// GameKeyListener
