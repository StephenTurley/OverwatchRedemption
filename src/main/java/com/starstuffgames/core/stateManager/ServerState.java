/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package com.starstuffgames.core.stateManager;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import com.starstuffgames.core.network.GameServer;

public abstract class ServerState extends Listener {

	protected GameServer gameServer;
	
	public ServerState(GameServer gameServer) {
		this.gameServer = gameServer;
	}
	
	public abstract void update(int delta);
	public abstract void enter();
	public abstract void exit();
	@Override
	public abstract void connected(Connection c);
	@Override
	public abstract void received (Connection c, Object object);
	@Override
	public abstract void disconnected(Connection c);
	@Override
	public abstract void idle(Connection c);
	

}
