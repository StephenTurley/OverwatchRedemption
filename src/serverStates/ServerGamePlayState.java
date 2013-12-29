/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package serverStates;


import com.esotericsoftware.kryonet.Connection;

import core.Debug;
import core.network.GameServer;
import core.network.Network;
import core.network.PlayerConnection;
import core.stateManager.ServerState;
import entities.Player;

public class ServerGamePlayState extends ServerState {

	public ServerGamePlayState(GameServer gameServer) {
		super(gameServer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(int delta) {
		if(gameServer.isPlayersReady())
		{
			gameServer.sendPlayersPacket();
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
		if(gameServer.isPlayerAuthenticated(pc))
		{
			if(object instanceof Network.MovePlayer)
			{
				Player player = gameServer.getPlayer(pc.getID());
				
				player.setMovementVector(((Network.MovePlayer)object).movementVector);
				
			}
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

}
