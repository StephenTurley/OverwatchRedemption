/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package com.starstuffgames.overwatch.gui;

import com.starstuffgames.overwatch.gameStates.QuitGame;
import com.starstuffgames.core.gui.MenuItem;
import com.starstuffgames.core.stateManager.GameState;

public class QuitGameMenuItem extends MenuItem {
	
	public QuitGameMenuItem() {
		super();
		super.text = "Quit Game";
	}

	public QuitGameMenuItem(int locX, int locY) {
		super(locX, locY);
		super.text = "Quit Game";
	}

	@Override
	public GameState execute() {
		return new QuitGame();
	}

}
