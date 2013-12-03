/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
//import com.esotericsoftware.minlog.Log;

import gameStates.*;
import core.*;
import core.configurationManager.ConfigurationManger;

public class Overwatch {
	
	private static Game game;
	
	public static void main(String[] args)
	{
		//Log.set(Log.LEVEL_DEBUG);
		//System.getProperties().list(System.out);
		//System.setProperty("org.lwjgl.librarypath", new File("bin/natives").getAbsolutePath());
		System.out.println("User operating system: " + System.getProperty("os.name"));
		System.out.println("User home directory: " + System.getProperty("user.home"));
		
		game = new Game(ConfigurationManger.loadConfiguration());
		game.start(new GameStart(game.getStateManager()));
	}
	
}
