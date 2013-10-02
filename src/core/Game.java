package core;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;


import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


import core.configurationManager.GameConfig;
import core.network.GameServer;
import core.stateManager.*;


public class Game {
	
	private static GameConfig config;
	private static StateManager sm;
	private GameLoop loop;
	private static Controller gamepad;
	private static GameServer gameServer;
	public static boolean isServer;


	
	
	public Game(GameConfig config)
	{
		Game.config = config;
		sm = new StateManager();
		isServer = false;
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
 		loadGamepads();
		
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
			Game.exit(0);
		}
		
		sm.push(startingState);
		loop = new GameLoop(sm);
		
		loop.run();
		
	}
 	public static void startServer()
 	{
 		gameServer = new GameServer();
 		gameServer.init();
 		gameServer.start();
 		isServer = true;
 	}
 	public static void killServer()
 	{
 		isServer = false;
 		gameServer.kill();
 	}
 	public static void updateServer(int delta)
 	{
 		gameServer.update(delta);
 	}
 	private void loadGamepads()
 	{
 		for (Controller c : ControllerEnvironment.getDefaultEnvironment().getControllers())
		{
			if(c.getType() == Controller.Type.GAMEPAD)
			{
				gamepad = c;
				if(Game.getGameConfig().isDebugLogging())
				{
					Debug.Trace("Gamepad Detected: " + gamepad.getName());
				}
			}
		}
 	}
 	public static Controller getGamePad(){
 		return gamepad;
 	}
 	public static void exit(int status)
 	{
 		if(gameServer != null) gameServer.kill();
 		System.exit(status);
 	}
 	
 	
 
}
