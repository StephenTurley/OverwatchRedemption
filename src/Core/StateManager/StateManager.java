package Core.StateManager;
import java.util.*;

public class StateManager {
	
	private Stack<GameState> states;
	
	public StateManager()
	{
		states = new Stack<GameState>();
	}
	
	public void draw()
	{
		states.peek().draw();
	}
	
	public void update()
	{
		states.peek().update();
	}
	
}
