/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package com.starstuffgames.overwatch.serverStates;

import com.starstuffgames.overwatch.entities.player.ServerPlayer;

import com.esotericsoftware.kryonet.Connection;

import com.starstuffgames.core.Debug;
import com.starstuffgames.core.network.GameServer;
import com.starstuffgames.core.network.Network;
import com.starstuffgames.core.network.Network.FocusOn;
import com.starstuffgames.core.network.PlayerConnection;
import com.starstuffgames.core.stateManager.ServerState;
import java.util.ArrayList;
import java.util.UUID;
public class ServerGamePlayState extends ServerState {

	private boolean playersFocused;
	
	private ArrayList<UUID> players;
	
	public ServerGamePlayState(GameServer gameServer) {
		super(gameServer);
		
		players = new ArrayList<>();
		playersFocused = false;
	}

	@Override
	public void update(int delta) {
		if(gameServer.isPlayersReady())
		{
			if(!playersFocused)
			{
				focusOnPlayers();
			}
			
			//keep track of player entities
			if(players.isEmpty())
			{
				for(PlayerConnection p : gameServer.getPlayerConnections())
				{
					players.add(p.uuid);
				}
			}
			gameServer.sendEntitiesPacket();
			
		}

	}

	@Override
	public void enter() {
		Debug.Trace("ServerGamePlayState entered!");
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void connected(Connection c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void received(Connection c, Object object) {
		PlayerConnection pc = (PlayerConnection)c;

		if(object instanceof Network.MovePlayer)
		{
			
			ServerPlayer player = (ServerPlayer)gameServer.getCurrentLevel().getEntity(pc.uuid);
			
			player.setMovementVector(((Network.MovePlayer)object).movementVector);
			
			gameServer.updateEntity(player);
			
		}


	}

	@Override
	public void disconnected(Connection c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void idle(Connection c) {
		// TODO Auto-generated method stub

	}
	
	private void focusOnPlayers()
	{
		for(PlayerConnection pc : gameServer.getPlayerConnections())
		{
			FocusOn focus = new FocusOn();
			focus.uuid = pc.uuid;
			
			gameServer.sendPacketTcp(pc, focus);
		}
		playersFocused = true;
	}

}
