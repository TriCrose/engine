package states;

import java.awt.Graphics2D;
import java.io.File;
import core.*;
import objects.*;

public class DemoState extends GameState {
	private float playerX, playerY;
	private AnimatedSprite stickman;
	
	public DemoState(GameWindow gw) {
		super(gw);
		playerX = 0;
		playerY = gw.getInnerHeight() - 95;
		
		File[] list = new File("anim/stickman").listFiles();
		String[] frames = new String [list.length];
		
		for (int i = 0; i < list.length; i++) {
			frames[i] = "anim/stickman/" + list[i].getName();
		}
		
		stickman = new AnimatedSprite(frames, 40);
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