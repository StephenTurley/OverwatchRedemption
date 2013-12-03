/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package gui;

import gameStates.HostGame;
import core.MenuItem;
import core.stateManager.GameState;


public class HostGameMenuItem extends MenuItem {

	public HostGameMenuItem() {
		super();
		super.text = "Host Game";
	}

	public HostGameMenuItem(int locX, int locY) {
		super(locX, locY);
		super.text = "Host Game";
	}
	public GameState execute()
	{
		return new HostGame();
	}

}
