package Core.StateManager;

public interface GameState {
	
	public void update();
	public void draw();
	public void load();
	public void pause();
	public void save();
	public void dispose();

}
