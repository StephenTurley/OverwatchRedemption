import Core.*;
import GameStates.*;

public class PixelFighter {
	
	private static Game game;
	
	public static void main(String[] args)
	{
		game = new Game();
		game.start(new GameStart());
	}
	
}
