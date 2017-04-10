package assets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class AssetLoader {
	public static Animation loadAnimation(String filename) {
		try {
			// Get the file as an array of bytes
			ByteBuffer animationFile = ByteBuffer.wrap(Files.readAllBytes(Paths.get(filename)));
			
			// Allocate space for all the frames and get the frame time
			BufferedImage[] frames = new BufferedImage [animationFile.getShort()];
			int frameTime = Math.round(1000.0f / (float) animationFile.get());
			
			// For each image file, read the bytes into an input stream and use ImageIO to get a BufferedImage from that
			for (int i = 0; i < frames.length; i++) {
				byte[] imageFile = new byte [animationFile.getInt()];
				animationFile.get(imageFile);
				frames[i] = ImageIO.read(new ByteArrayInputStream(imageFile));
			}
			
			return new Animation(frames, frameTime);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}