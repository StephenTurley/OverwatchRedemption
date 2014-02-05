package overwatch;
/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/

import overwatch.entities.player.Player;
import overwatch.gameStates.*;

import com.esotericsoftware.minlog.Log;

import core.*;
import core.configurationManager.ConfigurationManger;
import overwatch.entities.bullet.Bullet;
import overwatch.entities.goal.Goal;
import overwatch.entities.turret.Turret;

public class Overwatch {
	
	private static Game game;
	
	public static void main(String[] args)
	{	
		Log.set(Log.LEVEL_WARN);
		
		game = new Game(ConfigurationManger.loadConfiguration());
		//register entities
		game.registerEntity(Player.class);
		game.registerEntity(Turret.class);
		game.registerEntity(Bullet.class);
		game.registerEntity(Goal.class);
		
		game.start(new GameStart(game.getStateManager()));
	}
	
}
