package assets;

import java.util.Map;

import javax.sound.sampled.Clip;

public class AudioBank {
	private static Map<String, Clip> sounds;
	
	public static void loadSound(String identifier, Clip sound) {
		sounds.put(identifier, sound);
	}
	
	public static void playSound(String identifier) {
		sounds.get(identifier).start();
	}
	
	public static void destroySound(String identifier) {
		sounds.get(identifier).close();
		sounds.remove(identifier);
	}
}
