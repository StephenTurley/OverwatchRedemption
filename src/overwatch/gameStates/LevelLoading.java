/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package overwatch.gameStates;

import com.esotericsoftware.kryonet.Connection;

import core.Debug;
import core.Game;
import core.level.ClientLevel;
import core.network.Network.StartGame;
import core.stateManager.GameState;
import core.stateManager.StateManager;
import core.level.LevelManager;
import core.network.Network.PlayerReady;

public class LevelLoading extends GameState {
	
    private volatile boolean startGame;
    private ClientLevel clientLevel;
    private int stage, level;
    
    public LevelLoading(StateManager sm, int stage, int level)
    {
    	super(sm);
    	this.stage = stage;
    	this.level = level;
    }

	@Override
	public void update(int delta) {
		//will push new GamePlay state and pass it the client level eventually. 
		if(startGame)
		{
			sm.pop();
			sm.push(new MovementTest(sm, clientLevel));
		}
		
		if (clientLevel != null)
		{
			PlayerReady readyPacket = new PlayerReady();
			readyPacket.isReady=true;
			Game.clientSendUDP(readyPacket);
		}
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		Game.addClientListener(this);

	}

	@Override
	public void pause() {
		startGame = false;
		Game.removeClientListener(this);
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void enter() {
		Game.addClientListener(this);
		
		Debug.Trace("LevelLoading state entered!");
		startGame = false;
	
		try {
			clientLevel = LevelManager.loadClientLevel(stage, level);
		} catch (Exception e) {

			Debug.Trace(e.getMessage());
		}
		
		

	}

	@Override
	public void exit() {
		Debug.Trace("LevelLoading state exited!");
		Game.removeClientListener(this);
	}
	@Override
	public void received (Connection c, Object object)
	{
		if(object instanceof StartGame)
		{
			startGame = true;
		}
	}

}