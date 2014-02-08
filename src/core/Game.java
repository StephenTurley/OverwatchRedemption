/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;

import core.configurationManager.GameConfig;
import core.entity.EntityFactory;
import core.entity.EntityTemplate;
import core.graphics.TextureLoader;
import core.network.GameServer;
import core.network.Network;
import core.stateManager.*;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.PixelFormat;
import org.newdawn.slick.openal.SoundStore;


public class Game {
	
	private static GameConfig config;
	private static StateManager sm;
	private GameLoop loop;
	private static Controller gamepad;
	private static GameServer gameServer;
	private static Client gameClient;
	public static boolean isServer;


	
	
	public Game(GameConfig config)
	{
		Game.config = config;
		sm = new StateManager();
		isServer = false;
		gameClient = new Client();
		
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
 		Network.register(gameClient);
		gameClient.start();
 		loadGamepads();
 		
		try{
			
			if (config.isFullScreen())
			{
				DisplayMode displayMode = null;
		        DisplayMode[] modes = Display.getAvailableDisplayModes();
				for(DisplayMode mode : modes)
				{
					if (mode.getWidth() == config.getDisplayWidth() && mode.getHeight() == config.getDisplayHeight() && mode.isFullscreenCapable())
					{
						displayMode = mode;
					}
				}
		         Display.setDisplayMode(displayMode);
		         Display.setFullscreen(true);
			}
			else{
				Display.setDisplayMode(new DisplayMode(config.getDisplayWidth(),config.getDisplayHeight()));
			}
			
			Display.setTitle(config.getDisplayName());
			PixelFormat pf = new PixelFormat();
			ContextAttribs contextAttribs = new ContextAttribs(2,1);

			
			Display.create(pf, contextAttribs);
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
 		if(isServer)
 		{
 			isServer = false;
 			gameServer.kill();
 		}
 	}
 	public static void updateServer(int delta)
 	{
 		gameServer.update(delta);
 	}
 	public static void bindClient(int timeout, String host, int tcp, int udp)
 	{
 		try
 		{
			gameClient.connect(timeout, host, tcp, udp);
			gameClient.setKeepAliveUDP(500);
		}catch(Exception e)
		{
			Debug.Trace(e.getMessage());
			Game.exit(-1);
		}	
 	}
 	public static void disconnectClient()
 	{
 		gameClient.close();
 	}
 	public static void removeClientListener(Listener listener)
 	{
 		if(gameClient != null)
 		{
 			gameClient.removeListener(listener);
 		}
 	}
 	
 	public static void addClientListener(Listener listener)
 	{
 		if(gameClient != null)
 		{
 			gameClient.addListener(listener);
 		}
 	}
 	
 	public static void clientSendTCP(Object packet)
 	{
 		gameClient.sendTCP(packet);
 	}
 	
 	public static void clientSendUDP(Object packet)
 	{
 		gameClient.sendUDP(packet);
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
 		if(gameClient != null) gameClient.stop();
 		TextureLoader.deleteTextures();
 		Display.destroy();
 		SoundStore.get().clear();
 		System.exit(status);
 	}
 	public void registerEntity(Class<? extends EntityTemplate> entityTemplate)
 	{
 		EntityFactory.register(entityTemplate);
 	}
 	public static int getClientID()
 	{
 		return gameClient.getID();
 	}
 	
 	
 
}
