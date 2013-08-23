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
		if(!states.empty())
		{
			states.peek().draw();
		}
	}
	
	public void update()
	{
		if(!states.empty())
		{
			states.peek().update();
		}
	}
	
	public void push(GameState state)
	{
		if(!states.empty())
		{
			states.peek().pause();
		}
		states.push(state);
		state.enter();
	}
	
	public void pop()
	{
		if(!states.empty())
		{
			states.peek().exit();
			states.pop();
		}
		if(!states.empty())
		{
			states.peek().resume();
		}
		else
		{
			//ToDo: exit game
		}
	}
	
}
