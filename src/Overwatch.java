import java.io.File;

import gameStates.*;
import core.*;
import core.configurationManager.ConfigurationManger;

public class Overwatch {
	
	private static Game game;
	
	public static void main(String[] args)
	{
		game = new Game(ConfigurationManger.loadConfiguration());
		game.start(new GameStart(game.getStateManager()));
		
	}
	
}
