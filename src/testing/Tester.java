package testing;

import core.*;
import states.*;

public class Tester {
	public static void main(String[] args) {
		GameWindow gw = new GameWindow("Yasha", 1024, 768, null);
		gw.setGameState(new DemoState(gw));
		gw.startLoop();
	}
}