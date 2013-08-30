import Core.*;
import Core.ConfigurationManager.ConfigurationManger;
import GameStates.*;

public class PixelFighter {
	
	private static Game game;
	
	public static void main(String[] args)
	{
		game = new Game(ConfigurationManger.loadConfiguration());
		game.start(new GameStart(game.getStateManager()));
	}
	
}
