package main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.Timer;

/*
 * 
 * GameViewer class for the Jet-Pac game
 * 
 * This handles creating the JFrame and displaying the graphics for the game
 * 
 * Example: GameViewer g = new GameViewer();
 * 
 * @author Liam Waterbury and Sam Johnson
 * 
 */
public class GameViewer {
	// final variables
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	private static final int DELAY = 50;

	/*
	 * ensures that a new GameViewer is initialized.
	 */
	public GameViewer() {
		JFrame frame = new JFrame();
		frame.setTitle("Jet-Pac");

		IntroComponent i = new IntroComponent();
		i.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.add(i);
		frame.addKeyListener((KeyListener) new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {

				// ensures that on spacebar the gameComponent class is added and the game begins
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					frame.remove(i);
					frame.removeKeyListener(this);

					GameComponent c = new GameComponent();
					c.setPreferredSize(new Dimension(WIDTH, HEIGHT));
					GameKeyListener keyListener = new GameKeyListener(c);
					frame.addKeyListener(keyListener);
					frame.add(c);

					Timer timer = new Timer(DELAY, (ActionListener) keyListener);
					timer.start();

					c.readFile("Level1");

					frame.setVisible(true);
				} // end if
			}// end keyPressed

			/*
			 * no implementation
			 * 
			 * @Override
			 */
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}// keyReleased

			/*
			 * no implementation
			 * 
			 * @Override
			 */
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}// keyTyped
		});// end

		frame.pack();
		frame.setLocationRelativeTo(null);// center on screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}// GameViewer
}// end class
