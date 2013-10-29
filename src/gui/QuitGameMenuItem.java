package gui;

import gameStates.QuitGame;
import core.MenuItem;
import core.stateManager.GameState;

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
