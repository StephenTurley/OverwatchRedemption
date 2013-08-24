package Core.StateManager;
import java.awt.*;

public interface GameState {
	public abstract void update();
	public abstract void draw(Graphics2D g2);
	public abstract void resume();
	public abstract void pause();
	public abstract void save();
	public abstract void enter();
	public abstract void exit();

}
