package core.stateManager;

import com.esotericsoftware.kryonet.Listener;

import core.DrawableComponent;


public abstract class GameState extends Listener implements DrawableComponent{
	protected StateManager sm;
	
	public GameState(StateManager sm)
	{
		this.sm = sm;
	}
	
	public GameState()
	{
		
	}
	
	public StateManager getSm() {
		return sm;
	}

	public void setSm(StateManager sm) {
		this.sm = sm;
	}

	public abstract void update(int delta);
	public abstract void draw();
	public abstract void resume();
	public abstract void pause();
	public abstract void save();
	public abstract void enter();
	public abstract void exit();

}
