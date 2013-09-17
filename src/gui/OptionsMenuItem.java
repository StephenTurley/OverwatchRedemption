package gui;

import gameStates.Options;
import core.MenuItem;
import core.stateManager.GameState;

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
