package testing;

import core.*;
import states.*;

public class Tester {
	public static void main(String[] args) {
		GameWindow gw = new GameWindow("Yasha", 800, 600, 30, null);
		gw.setGameState(new TitleState(gw));
		gw.startLoop();
	}
}