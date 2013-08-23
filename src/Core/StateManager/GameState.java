package Core.StateManager;

public interface GameState {
	
	public abstract void update();
	public abstract void draw();
	public abstract void load();
	public abstract void pause();
	public abstract void save();
	public abstract void dispose();

}
