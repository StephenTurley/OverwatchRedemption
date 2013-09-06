package gameStates;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import core.stateManager.GameState;
import core.stateManager.StateManager;
import gui.*;

public class startJoin extends GameState {
	
	private StartJoinInterface uiWidget;

	public startJoin(StateManager sm) {
		super(sm);
		uiWidget = new StartJoinInterface();
	}

	@Override
	public void update(int delta) {
		

	}

	@Override
	public void draw() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		uiWidget.update();
		Display.update();

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
		// TODO Auto-generated method stub

	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}
