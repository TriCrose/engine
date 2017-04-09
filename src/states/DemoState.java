package states;

import java.awt.Graphics2D;

import assets.*;
import core.*;

public class DemoState extends GameState {
	private float playerX, playerY;
	private Animation stickman;
	
	public DemoState(GameWindow gw) {
		super(gw);
		playerX = 0;
		playerY = gw.getInnerHeight() - 95;
		stickman = AssetLoader.loadAnimation("anim/stickman.anim");
	}
	
	@Override
	public void update(long dt) {
		playerX += (float) dt * 0.1f;
		if (playerX >= gw.getInnerWidth()) playerX = playerX % gw.getInnerWidth() - 48;
		stickman.updateFrameIndex(dt);
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(stickman.getCurrentFrame(), (int) playerX, (int) playerY, null);
	}
}