/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package overwatch.serverStates;

import java.util.ArrayList;

import overwatch.entities.Player;

import com.esotericsoftware.kryonet.Connection;

import core.Debug;
import core.Game;
import core.entity.Entity;
import core.exception.LevelNotFoundException;
import core.level.LevelManager;
import core.network.GameServer;
import core.network.Network;
import core.network.Network.LoadLevel;
import core.network.PlayerConnection;
import core.stateManager.ServerState;

public class ServerLoadLevelState extends ServerState {
	private boolean levelLoaded;

	public ServerLoadLevelState(GameServer gameServer) {
		super(gameServer);
		levelLoaded = false;
	}

	@Override
	public void update(int delta) {
		if(gameServer.isPlayersReady() && levelLoaded)
		{
			gameServer.setPlayersReady(false);
			gameServer.sendToAllTCP(new Network.StartGame());
			gameServer.changeState(new ServerGamePlayState(gameServer));
		}
			

	}

	@Override
	public void enter() {
		//players will be ready after they load the level
		gameServer.setPlayersReady(false);
		
		levelLoaded = false;
		
		LoadLevel loadLevelPkt = new LoadLevel();
		loadLevelPkt.stage = 0;
		loadLevelPkt.level = 1;
		
		gameServer.sendToAllTCP(loadLevelPkt);
		
		try
		{
			gameServer.setCurrentLevel(LevelManager.loadServerLevel(loadLevelPkt.stage, loadLevelPkt.level));
		}
		catch (LevelNotFoundException e)
		{
			Debug.Trace("Level not found");
			Game.exit(-1);
		}
		
		//set connection uuid
		for(PlayerConnection pc : gameServer.getPlayerConnections())
		{
			ArrayList<Entity> players = gameServer.getCurrentLevel().getEntityCollection().getEntities(Player.class);
			
			//may want to check that the players are >= pc
			Player p = (Player)players.get(pc.getID() - 1);
			
			pc.uuid = p.getID();
			
		}
		levelLoaded = true;
		
	}

	@Override
	public void exit() {
		

	}

	@Override
	public void connected(Connection c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void received(Connection c, Object object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disconnected(Connection c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void idle(Connection c) {
		// TODO Auto-generated method stub

	}

}
