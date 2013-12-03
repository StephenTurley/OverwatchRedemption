/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package gameStates;

import core.Game;
import core.stateManager.GameState;
import core.stateManager.StateManager;

public class QuitGame extends GameState {

	public QuitGame(StateManager sm) {
		super(sm);
		// TODO Auto-generated constructor stub
	}

	public QuitGame() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void enter() {
		Game.exit(0);
	}

	@Override
	public void exit() {

	}

}
