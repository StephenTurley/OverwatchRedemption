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
import com.starstuffgames.core.level.ServerLevel;
import com.starstuffgames.core.network.GameServer;
import com.starstuffgames.core.network.Network.FocusOn;
import com.starstuffgames.core.network.Network.MovePlayer;
import com.starstuffgames.core.network.PlayerConnection;
import com.starstuffgames.core.stateManager.ServerState;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
public class ServerGamePlayState extends ServerState {

	private boolean playersFocused;
	
	private ArrayList<UUID> players;
	private final ServerLevel currentLevel;
	private volatile ConcurrentHashMap<UUID,MovePlayer> movementBuffer;
	
	/**
	 *
	 * @param gameServer
	 * @param currentLevel
	 */
	public ServerGamePlayState(GameServer gameServer, ServerLevel currentLevel) {
		super(gameServer);
		
		players = new ArrayList<>();
		this.currentLevel = currentLevel;
		playersFocused = false;
		movementBuffer =  new ConcurrentHashMap<>(2);
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
			
			currentLevel.update(delta);
			gameServer.sendEntitiesPacket(currentLevel.getEntityCollection().getEntities());
			
		}
	
		for(UUID uuid : movementBuffer.keySet())
		{
			MovePlayer pkt  = movementBuffer.remove(uuid);

			ServerPlayer player = (ServerPlayer)currentLevel.getEntity(uuid);

			player.setMovementVector(pkt.movementVector);
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

		if(object instanceof MovePlayer)
		{	
			MovePlayer pkt = (MovePlayer)object;
	
			movementBuffer.putIfAbsent(pc.uuid, pkt);
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
