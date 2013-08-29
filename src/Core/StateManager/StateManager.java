package Core.StateManager;
import java.util.*;

import org.lwjgl.opengl.Display;

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
	public void draw()
	{
		if(!states.empty())
		{
			states.peek().draw();
		}
		Display.update();
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
		if(!states.empty() && states.size() == 1)
		{
			Display.destroy();
			System.exit(0);
		}
		if(!states.empty())
		{
			
		}
		if(!states.empty())
		{
			states.peek().resume();
		}
		else
		{
			Display.destroy();
			System.exit(0);
		}
	}
	
}
