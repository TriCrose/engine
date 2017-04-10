package core;

import java.awt.Graphics2D;

public abstract class GameState {
	protected GameWindow gw;
	
	public GameState(GameWindow gw) {
		this.gw = gw;
	}
	
	public abstract void update(long dt);
	public abstract void render(Graphics2D g);
	public abstract void destroy();
}