package core.stateManager;

import core.DrawableComponent;


public abstract class GameState implements DrawableComponent{
	protected StateManager sm;
	
	public GameState(StateManager sm)
	{
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
