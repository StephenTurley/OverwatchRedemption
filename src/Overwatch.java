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
import entities.Player;

public class Overwatch {
	
	private static Game game;
	
	public static void main(String[] args)
	{	
		game = new Game(ConfigurationManger.loadConfiguration());
		//register entities
		game.regiesterEntity(Player.class);
		
		game.start(new GameStart(game.getStateManager()));
	}
	
}
