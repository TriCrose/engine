package states;

import java.awt.Graphics2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import core.*;
import objects.*;

public class DemoState extends GameState {
	private float playerX, playerY;
	private AnimatedSprite stickman;
	
	public DemoState(GameWindow gw) {
		super(gw);
		playerX = 0;
		playerY = gw.getInnerHeight() - 95;
		
		try { stickman = new AnimatedSprite(Files.readAllBytes(Paths.get("anim/stickman.anim"))); }
		catch (IOException e) { e.printStackTrace(); }
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