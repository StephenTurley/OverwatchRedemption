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
import com.starstuffgames.core.network.Network;
import com.starstuffgames.core.state.ServerState;

public class ServerLobbyState extends ServerState {
	
	private int countdownRemaining;
	private int previousCountDown;
	private final int TIME_TO_START = 1000; //seconds

	public ServerLobbyState(GameServer gameServer) {
		super(gameServer);
		countdownRemaining = TIME_TO_START;
		previousCountDown = -1;
	}

	@Override
	public void update(int delta) {
		
		if(gameServer.getPlayerCount() == 2 && gameServer.isPlayersReady())
		{
			countdownRemaining -= delta;
			int seconds = countdownRemaining / 1000;
			if(seconds != previousCountDown)
			{
				previousCountDown = seconds;
				gameServer.sendToAllTCP(new Network.ServerMessage("The game will start in "+seconds+" seconds."));
			}
		}else
		{
			if(countdownRemaining != TIME_TO_START)//timer had started but was stopped. 
			{
				gameServer.sendToAllTCP(new Network.ServerMessage("Are you ready?"));
				countdownRemaining = TIME_TO_START;
			}
		}
		if( countdownRemaining <= 0)
		{
			gameServer.changeState(new ServerLoadLevelState(gameServer));
		}

		gameServer.sendPlayersPacket();
	}

	@Override
	public void enter() {
		Debug.Trace("ServerLobbyState entered!");
		
	}

	@Override
	public void exit() {
		Debug.Trace("ServerLobbyState exited!");
		gameServer.setPlayersReady(false);
	}

	@Override
	public void connected(Connection c) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void received(Connection c, Object object) {
		
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
