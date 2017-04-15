package assets;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;

public class AssetLoader {
	public static Animation loadAnimation(String filename) {
		try {
			// TODO: use getResourceAsStream()
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
	
	public static Clip loadClip(String filename) {
		try {
			// TODO: use getResourceAsStream()
			
			// Load the audio file from the disk
			AudioInputStream rawStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(filename)));
			AudioFormat rawFormat = rawStream.getFormat();
			
			// Decode it from whatever it was into this format
			AudioFormat decodedFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED,
				rawFormat.getSampleRate(),
				16,
				rawFormat.getChannels(),
				rawFormat.getChannels() * 2,
				rawFormat.getSampleRate(),
				false
			);
			
			// Return the clip
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(decodedFormat, rawStream));
			return clip;
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
			return null;
		}
	}
}