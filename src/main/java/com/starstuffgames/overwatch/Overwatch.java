package com.starstuffgames.overwatch;
/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/

import com.esotericsoftware.minlog.Log;
import com.starstuffgames.core.Game;
import com.starstuffgames.core.config.ConfigurationManger;
import com.starstuffgames.overwatch.entities.ammunition.bullet.Bullet;
import com.starstuffgames.overwatch.entities.enemies.turret.Turret;
import com.starstuffgames.overwatch.entities.objects.goal.Goal;
import com.starstuffgames.overwatch.entities.player.Player;
import com.starstuffgames.overwatch.gameStates.GameStart;


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
