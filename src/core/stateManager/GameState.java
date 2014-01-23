/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.stateManager;

import com.esotericsoftware.kryonet.Listener;

import core.graphics.DrawableComponent;


public abstract class GameState extends Listener implements DrawableComponent{
	protected StateManager sm;
	
	public GameState(StateManager sm)
	{
		this.sm = sm;
	}
	
	public GameState()
	{
		
	}
	
	public StateManager getSm() {
		return sm;
	}

	public void setSm(StateManager sm) {
		this.sm = sm;
	}

	@Override
	public abstract void update(int delta);
	@Override
	public abstract void draw();
	public abstract void resume();
	public abstract void pause();
	public abstract void save();
	public abstract void enter();
	public abstract void exit();

}
