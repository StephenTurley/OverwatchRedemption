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
import core.Game;
import core.exception.LevelNotFoundException;
import core.level.LevelManager;
import core.network.GameServer;
import core.network.Network;
import core.network.Network.LoadLevel;
import core.stateManager.ServerState;
import entities.Player;

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
			gameServer.sendToAuthenticatedTCP(new Network.StartGame());
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
		
		gameServer.sendToAuthenticatedTCP(loadLevelPkt);
		
		try
		{
			gameServer.setCurrentLevel(LevelManager.loadServerLevel(loadLevelPkt.stage, loadLevelPkt.level));
		}
		catch (LevelNotFoundException e)
		{
			Debug.Trace("Level not found");
			Game.exit(-1);
		}
		int i = 0;
		for(Player p : gameServer.getPlayers().values())
		{
			Point startingPoint = gameServer.getCurrentLevel().getStartingPoints().get(i);
			p.setPosX(startingPoint.getX());
			p.setPosY(startingPoint.getY());
			i++;
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
