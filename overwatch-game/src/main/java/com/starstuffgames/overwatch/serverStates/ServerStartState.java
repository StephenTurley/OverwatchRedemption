/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package com.starstuffgames.overwatch.serverStates;

import com.esotericsoftware.kryonet.Connection;
import com.starstuffgames.core.Debug;
import com.starstuffgames.core.network.GameServer;
import com.starstuffgames.core.network.PlayerConnection;
import com.starstuffgames.core.network.Network.Login;
import com.starstuffgames.core.network.Network.PlayerReady;
import com.starstuffgames.core.network.Network.ServerMessage;
import com.starstuffgames.core.state.ServerState;


public class ServerStartState extends ServerState {
	


	public ServerStartState(GameServer gameServer) {
		super(gameServer);
	}

	@Override
	public void update(int delta) {
		gameServer.changeState(new ServerLobbyState(gameServer));
	}

	@Override
	public void enter() {
		Debug.Trace("ServerStartState entered!");	
	}

	@Override
	public void exit() {
		Debug.Trace("ServerStartState exited!");
		
	}

	@Override
	public void connected(Connection c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void received(Connection c, Object object) {
		PlayerConnection pc = (PlayerConnection)c;
		
		if(object instanceof Login)
		{
			//ignore if two players are logged in. 
			if(pc.name != null)
			{
				return;
			}
			
			String name  =  ((Login)object).name;
			
			if (name == null) return;
			pc.name = name;
			
			
			//send connection message to clients
			gameServer.sendToAllTCP(new ServerMessage(pc.name + " has connected"));
			
			if(gameServer.getPlayerCount() == 2)
			{
				gameServer.sendToAllTCP(new ServerMessage("All players have connected, are you ready?"));
			}
		}
		

		if(object instanceof PlayerReady)
		{
			PlayerReady readyPacket = (PlayerReady)object;
			gameServer.setPlayerReady(pc.getID(), readyPacket.isReady);
		}


	}
	@Override
	public void disconnected(Connection c) {
		PlayerConnection pc = (PlayerConnection)c;
		
		Debug.Trace(pc.name +" has disconnectd");
	}

	@Override
	public void idle(Connection c) {
		// TODO Auto-generated method stub
		
	}

}
