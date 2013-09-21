package core;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;


import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import core.configurationManager.GameConfig;
import core.network.NetworkClassRegister;
import core.stateManager.*;


public class Game {
	
	private static GameConfig config;
	private static StateManager sm;
	private GameLoop loop;
	private static Server server;
	private static Controller gamepad;


	
	
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
 	
 	public static Controller getGamePad(){
 		return gamepad;
 	}
 	public static void initializeServer()
 	{
 		server = new Server();
 		NetworkClassRegister.register(server);
 		server.addListener(new Listener(){
 			public void connected(Connection connection)
 			{
 				Debug.Trace("Client Connected!");
 			}
 		});
 	}
 	public static void startServer()
 	{
 		server.start();
 		try
 		{
 			server.bind(config.getServerTCP(),config.getServerUDP());
 		}catch(Exception e)
 		{
			Debug.Trace(e.getMessage());
 			Game.exit(-1);
 		}
 	}
 	public static void killServer()
 	{
 		server.close();
 	}
 	public static Server getServer()
 	{
 		return server;
 	}
 	public static void exit(int status)
 	{
 		if(server != null) killServer();
 		System.exit(status);
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
 	
 
}
