/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package com.starstuffgames.overwatch.serverStates;

import java.util.ArrayList;

import com.starstuffgames.overwatch.entities.player.ServerPlayer;

import com.esotericsoftware.kryonet.Connection;

import com.starstuffgames.core.Debug;
import com.starstuffgames.core.Game;
import com.starstuffgames.core.entity.ServerEntity;
import com.starstuffgames.core.exception.LevelNotFoundException;
import com.starstuffgames.core.level.LevelManager;
import com.starstuffgames.core.level.ServerLevel;
import com.starstuffgames.core.network.GameServer;
import com.starstuffgames.core.network.Network;
import com.starstuffgames.core.network.Network.LoadLevel;
import com.starstuffgames.core.network.PlayerConnection;
import com.starstuffgames.core.stateManager.ServerState;

public class ServerLoadLevelState extends ServerState {
	
	private ServerLevel level;

	public ServerLoadLevelState(GameServer gameServer) {
		super(gameServer);
	}

	@Override
	public void update(int delta) {
		if(gameServer.isPlayersReady() && level != null)
		{
			gameServer.setPlayersReady(false);
			gameServer.sendToAllTCP(new Network.StartGame());
			gameServer.changeState(new ServerGamePlayState(gameServer, level));
		}
			

	}

	@Override
	public void enter() {
		//players will be ready after they load the level
		gameServer.setPlayersReady(false);
		level = null;
		
		LoadLevel loadLevelPkt = new LoadLevel();
		loadLevelPkt.stage = 0;
		loadLevelPkt.level = 2;
		
		gameServer.sendToAllTCP(loadLevelPkt);
		
		try
		{
			level = LevelManager.loadServerLevel(loadLevelPkt.stage, loadLevelPkt.level);
		}
		catch (LevelNotFoundException e)
		{
			Debug.Trace("Level not found");
			Game.exit(-1);
		}
		
		//set connection uuid
		for(PlayerConnection pc : gameServer.getPlayerConnections())
		{
			ArrayList<ServerEntity> players;
			players = level.getEntityCollection().getEntities(ServerPlayer.class);
			
			if(players.size() <= gameServer.getPlayerConnections().size())
			{
				ServerPlayer p = (ServerPlayer)players.get(pc.getID() - 1);
				pc.uuid = p.getID();
			}
			
		}
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
