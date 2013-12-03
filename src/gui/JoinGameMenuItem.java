/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package gui;

import gameStates.JoinGame;
import core.MenuItem;
import core.stateManager.GameState;

public class JoinGameMenuItem extends MenuItem {


	public JoinGameMenuItem() {
		super();
		super.text = "Join Game";
	}

	public JoinGameMenuItem(int locX, int locY) {
		super(locX, locY);
		super.text = "Join Game";
	}
	
	public GameState execute()
	{
		return new JoinGame();
	}

}
