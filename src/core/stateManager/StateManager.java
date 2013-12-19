/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.stateManager;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.util.*;

import org.lwjgl.opengl.Display;

import core.Game;
import core.graphics.DrawableComponent;

public class StateManager implements DrawableComponent {
	
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
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 
		if(!states.empty())
		{
			states.peek().draw();
		}
		Display.update();
	}
	
	public void update(int delta)
	{
		if(!states.empty())
		{
			states.peek().update(delta);
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
			Game.exit(0);
		}
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
			Display.destroy();
			Game.exit(0);
		}
	}
	
}
