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
import core.network.Network.FocusOn;
import core.network.PlayerConnection;
import core.stateManager.ServerState;
import entities.Player;
public class ServerGamePlayState extends ServerState {

	private boolean playersFocused;
	
	public ServerGamePlayState(GameServer gameServer) {
		super(gameServer);
		
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
			
			Player player = (Player)gameServer.getCurrentLevel().getEntity(pc.uuid);
			
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
