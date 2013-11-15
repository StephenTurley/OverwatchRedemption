import com.esotericsoftware.minlog.Log;

import gameStates.*;
import core.*;
import core.configurationManager.ConfigurationManger;

public class Overwatch {
	
	private static Game game;
	
	public static void main(String[] args)
	{
		Log.set(Log.LEVEL_DEBUG);
		game = new Game(ConfigurationManger.loadConfiguration());
		game.start(new GameStart(game.getStateManager()));
	}
	
}
