/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package serverStates;

import org.lwjgl.opengl.Display;

import com.esotericsoftware.kryonet.Connection;

import core.network.GameServer;
import core.network.Player;
import core.stateManager.ServerState;

public class ServerLoadLevelState extends ServerState {

	public ServerLoadLevelState(GameServer gameServer) {
		super(gameServer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enter() {
		//lets arbitrarily set the start position until we have it loaded via XML
		int i = 1;
		for(Player p : gameServer.getPlayers().values())
		{
			p.setPosX(200 * i);
			p.setPosY(Display.getHeight() - 200);
			i++;
		}
		gameServer.changeState(new ServerGamePlayState(gameServer));
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
