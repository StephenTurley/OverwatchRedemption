package Core.StateManager;
import java.util.*;
import java.awt.*;

public class StateManager {
	
	private Stack<GameState> states;
	
	public StateManager()
	{
		states = new Stack<GameState>();
	}
	
	public StateManager(GameState startingState)
	{
		states = new Stack<GameState>();
		states.push(startingState);
		states.peek().enter();
	}
	public void draw(Graphics2D g2)
	{
		if(!states.empty())
		{
			states.peek().draw(g2);
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
