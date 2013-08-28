package Core;
import Core.StateManager.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class Game {
	
	private final boolean DEBUG_MODE;
	private static StateManager sm;
	private GameLoop loop;
	
	
	public Game()
	{
		DEBUG_MODE = true;
		Debug.Trace("Game is running!");
	}
	
	
	public StateManager getStateManager()
	{
		return sm;
	}
	public void start(GameState startingState)
	{
		sm = new StateManager(startingState);
		
		try{
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		
		loop = new GameLoop(sm);
		
		loop.run();
		
	}

}
