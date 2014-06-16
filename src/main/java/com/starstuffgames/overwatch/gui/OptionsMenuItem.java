/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package com.starstuffgames.overwatch.gui;

import com.starstuffgames.overwatch.gameStates.Options;
import com.starstuffgames.core.gui.MenuItem;
import com.starstuffgames.core.state.GameState;

public class OptionsMenuItem extends MenuItem {

	public OptionsMenuItem() {
		super();
		super.text = "Options";
	}

	public OptionsMenuItem( int locX, int locY) {
		super();
		super.text = "Options";
	}
	
	public GameState execute()
	{
		return new Options();
	}

}
