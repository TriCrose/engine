package objects;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

public class AnimatedSprite {
	private BufferedImage[] mFrames;	// Array of frames
	private final int mFrameTime;		// Length of each frame in milliseconds
	private int mAnimationTime = 0;		// Time since start of animation in milliseconds
	private boolean mPaused = false;	// If true then the animation is paused
	private boolean mLoop = true;		// Should the animation loop
	
	// Load from a concat file
	public AnimatedSprite(InputStream file, int frameTime) {
		mFrameTime = frameTime;
		mFrames = new BufferedImage [filenames.length];
		for (int i = 0; i < mFrames.length; i++) {
			try {
				mFrames[i] = ImageIO.read(new File(filenames[i]));
			} catch (IOException e) {
				System.err.println("Can't open image '" + filenames[i] + "': " + e.getMessage());
				mFrames[i] = null;
			}
		}
	}

	public void play() {
		mPaused = false;
	}
	
	public void pause() {
		mPaused = true;
	}
	
	public boolean getPaused() {
		return mPaused;
	}
	
	// Go to a specific time in the animation
	public void goToTime(int ms) {
		mAnimationTime = ms;
	}
	
	public int getAnimationTime() {
		return mAnimationTime;
	}
	
	// Go to a specific frame in the animation
	public void goToFrame(int frameNo) {
		mAnimationTime = frameNo * mFrameTime;
	}
	
	public void setLoop(boolean loop) {
		mLoop = loop;
	}
	
	public boolean isLooping() {
		return mLoop;
	}
	
	public int getFrameTime() {
		return mFrameTime;
	}
	
	// Gets width of first frame
	public int getWidth() {
		return mFrames[0].getWidth();
	}
	
	// Gets width of first frame
	public int getHeight() {
		return mFrames[0].getHeight();
	}
	
	public void updateFrameIndex(long dt) {
		// If the animation has stopped, don't do anything
		if (mPaused) return;
		
		mAnimationTime += dt;
		
		// If the animation is beyond the end, then either loop or stop
		if (mAnimationTime >= mFrameTime * mFrames.length) {
			if (mLoop) {
				mAnimationTime %= mFrameTime * mFrames.length;
			} else {
				mAnimationTime = mFrameTime * mFrames.length - 1;
				mPaused = true;
			}
		}
	}
	
	public Image getCurrentFrame() {
		// Get the index of the current frame to draw (or the last one if the animation time is too far)
		int frameIndex = Math.min(mFrames.length - 1, mAnimationTime / mFrameTime);
		return mFrames[frameIndex];
	}
}