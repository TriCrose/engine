package assets;

import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.Clip;

public class AudioBank {
	private static Map<String, Clip> sounds;
	
	static {
		sounds = new HashMap<>();
	}
	
	public static void loadSound(String identifier, Clip sound) {
		sounds.put(identifier, sound);
	}
	
	public static void playSound(String identifier) {
		if (sounds.get(identifier) == null) {
			System.err.println("Sound \"" + identifier + "\" is null");
			return;
		}
		
		sounds.get(identifier).stop();
		sounds.get(identifier).setFramePosition(0);
		sounds.get(identifier).start();
	}
	
	public static void destroySound(String identifier) {
		if (sounds.get(identifier) == null) {
			System.err.println("Sound \"" + identifier + "\" is null");
			return;
		}
		
		sounds.get(identifier).stop();
		sounds.get(identifier).close();
		sounds.remove(identifier);
	}
}
