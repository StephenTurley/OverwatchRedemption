package GameStates;

import org.lwjgl.opengl.Display;

import Core.Debug;
import Core.StateManager.GameState;

public class GameStart implements GameState {

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw() {
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
	public void enter()
	{
		Debug.Trace("Start State has been entered!");
	}
	
	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}
