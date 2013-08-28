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
		
		try{
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		
		sm = new StateManager(startingState);
		loop = new GameLoop(sm);
		
		loop.run();
		
	}

}
