/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package serverStates;

import org.lwjgl.util.Point;

import com.esotericsoftware.kryonet.Connection;

import core.Debug;
import core.exception.LevelNotFoundException;
import core.level.LevelManager;
import core.network.GameServer;
import core.network.Player;
import core.stateManager.ServerState;

public class ServerLoadLevelState extends ServerState {

	public ServerLoadLevelState(GameServer gameServer) {
		super(gameServer);
	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enter() {
		try
		{
			gameServer.setCurrentLevel(LevelManager.loadServerLevel(0, 0));
		}
		catch (LevelNotFoundException e)
		{
			Debug.Trace("Level not found");
		}
		int i = 0;
		for(Player p : gameServer.getPlayers().values())
		{
			Point startingPoint = gameServer.getCurrentLevel().getStartingPoints().get(i);
			p.setPosX(startingPoint.getX());
			p.setPosY(startingPoint.getY());
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
