package states;

import java.awt.Graphics2D;

import assets.*;
import core.*;

public class DemoState extends GameState {
	private float playerX, playerY;
	private Animation stickman;
	
	public DemoState(GameWindow gw) {
		super(gw);
		stickman = AssetLoader.loadAnimation("assets/sprites/stickman");
		playerX = -stickman.getWidth();
		playerY = gw.getInnerHeight() - stickman.getHeight() - 100;
	}
	
	@Override
	public void update(long dt) {
		playerX += (float) dt * 0.15f;
		if (playerX >= gw.getInnerWidth()) playerX = playerX % gw.getInnerWidth() - stickman.getWidth();
		stickman.updateFrameIndex(dt);
	}

	@Override
	public void render(Graphics2D g) {
		g.fillRect(0, gw.getInnerHeight() - 100, gw.getInnerWidth(), 100);
		g.drawImage(stickman.getCurrentFrame(), (int) playerX, (int) playerY, null);
	}

	@Override
	public void destroy() {
	}
}