package core;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import core.configurationManager.GameConfig;
import core.stateManager.*;


public class Game {
	
	private static GameConfig config;
	private static StateManager sm;
	private GameLoop loop;

	
	
	public Game(GameConfig config)
	{
		Game.config = config;
		sm = new StateManager();
		if(Game.config.isDebugLogging())
		{
			Debug.Trace("Game initialized!");
		}
	}
	
	
	public StateManager getStateManager()
	{
		return sm;
	}
	public static GameConfig getGameConfig()
	{
		return config;
	}
 	public void start(GameState startingState)
	{
		
		try{
			
			if (config.isFullScreen())
			{
				DisplayMode displayMode = null;
		        DisplayMode[] modes = Display.getAvailableDisplayModes();

		         for (int i = 0; i < modes.length; i++)
		         {
		             if (modes[i].getWidth() == config.getDisplayWidth()
		             && modes[i].getHeight() == config.getDisplayHeight()
		             && modes[i].isFullscreenCapable())
		               {
		                    displayMode = modes[i];
		               }
		         }
		         Display.setDisplayMode(displayMode);
		         Display.setFullscreen(true);
			}
			else{
				Display.setDisplayMode(new DisplayMode(config.getDisplayWidth(),config.getDisplayHeight()));
			}
			
			Display.setTitle(config.getDisplayName());
			Display.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		
		sm.push(startingState);
		loop = new GameLoop(sm);
		
		loop.run();
		
	}

}
