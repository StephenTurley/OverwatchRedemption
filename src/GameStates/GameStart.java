package GameStates;
import java.awt.*;


import Core.Debug;
import Core.StateManager.GameState;

public class GameStart implements GameState {

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics2D g2) {
		g2.fillRect(100, 100, 300, 300);

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
