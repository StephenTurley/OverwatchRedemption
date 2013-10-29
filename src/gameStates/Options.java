package gameStates;

import org.lwjgl.input.Keyboard;

import core.stateManager.GameState;
import core.stateManager.StateManager;

public class Options extends GameState {

	public Options(StateManager sm) {
		super(sm);
		// TODO Auto-generated constructor stub
	}
	
	public Options()
	{
		
	}
	@Override
	public void update(int delta) {
		handleInput();
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
		// TODO Auto-generated method stub

	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}
	
	private void handleInput()
	{
		while(Keyboard.next())
		{
			if(Keyboard.getEventKeyState() || Keyboard.isRepeatEvent())
			{
				if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE)
				{
					super.sm.pop();
				}
			}
		}
	}

}
