package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
	// Game panel where drawing happens
	private JPanel gamePanel;
	
	// Current game state
	private GameState gameState;
	
	// Next game state (when transitioning)
	private GameState nextGameState;
	
	public void setGameState(GameState gameState) {
		// Pending game state change
		nextGameState = gameState;
	}
	
	// Keyboard input
	private Map<Integer, Boolean> keyboardState;
	
	public boolean keyPressed(int keyCode) {
		return keyboardState.containsKey(keyCode) ? keyboardState.get(keyCode) : false;
	}
	
	// Inner window dimensions
	public int getInnerWidth() {
		return getContentPane().getWidth();
	}
	
	public int getInnerHeight() {
		return getContentPane().getHeight();
	}
	
	public GameWindow(String title, int width, int height, Image icon) {
		super(title);
		gameState = nextGameState = null;
		
		// Setup non-observer keyboard input
		keyboardState = new HashMap<>();
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				synchronized (GameWindow.class) {
					if (e.getID() == KeyEvent.KEY_PRESSED) keyboardState.put(e.getKeyCode(), true);
					else if (e.getID() == KeyEvent.KEY_RELEASED) keyboardState.put(e.getKeyCode(), false);
					return false;
				}
			}
		});
		
		// Setup game window
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setSize(width, height);
		setLocationRelativeTo(null);
		setIconImage(icon);
		
		// Create game panel using the gameState to render
		gamePanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			{ setBackground(Color.WHITE); }

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;

				// Turn on antialiasing
				g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				
				// Render each game object
				if (gameState != null) gameState.render(g2);
			}
		};
		add(gamePanel);
		
		// Show window
		setVisible(true);
	}
	
	// Game loop
	public void startLoop() {
		long t = System.currentTimeMillis();
		while (isShowing()) {
			long dt = System.currentTimeMillis() - t;
			t = System.currentTimeMillis();
			
			// If a state change is pending then do it now
			if (nextGameState != null) {
				gameState = nextGameState;
				nextGameState = null;
			}
			
			// Now perform the update-render loop providing we have a valid game state
			if (gameState != null) {
				gameState.update(dt);
				gamePanel.repaint();
			}
			
			try { Thread.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
		}
	}
}