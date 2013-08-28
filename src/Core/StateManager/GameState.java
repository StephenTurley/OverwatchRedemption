package Core.StateManager;


public interface GameState {
	public abstract void update();
	public abstract void draw();
	public abstract void resume();
	public abstract void pause();
	public abstract void save();
	public abstract void enter();
	public abstract void exit();

}
