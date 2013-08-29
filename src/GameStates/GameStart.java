package GameStates;

import static org.lwjgl.opengl.GL11.*;

import java.util.HashMap;
import org.lwjgl.input.Keyboard;

import Core.Debug;
import Core.StateManager.GameState;
import Core.StateManager.StateManager;

public class GameStart extends GameState {
	
	private float xDelta = 0;
	private float yDelta = 0;
	private final float VELOCITY = 5;
	private HashMap<String, Boolean> flags = new HashMap<String, Boolean>();
	
	public GameStart(StateManager sm)
	{
		super(sm);
	}


	public void update() {
		handleInput();
		moveQuad();
	}


	public void draw() {
		// Clear the screen and depth buffer
	
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 

		 

		// set the color of the quad (R,G,B,A)
	
		glColor3f(0.5f,0.5f,1.0f);

		// draw quad
		glBegin(GL_QUADS);
			glVertex2f(100 + xDelta,100 + yDelta);
			glVertex2f(300 + xDelta,100 + yDelta);
			glVertex2f(300 + xDelta,300 + yDelta);
			glVertex2f(100 + xDelta,300 + yDelta);
		glEnd();
	}


	public void resume() {
		// TODO Auto-generated method stub

	}


	public void pause() {
		// TODO Auto-generated method stub

	}


	public void save() {
		// TODO Auto-generated method stub

	}
	
	public void enter()
	{
		flags.put("up", false);
		flags.put("down", false);
		flags.put("left", false);
		flags.put("right", false);
		
		
		Debug.Trace("Start State has been entered!");
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 800, 0, 600, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
	}
	
	public void exit() {
		// TODO Auto-generated method stub

	}
	
	private void moveQuad()
	{
		if (flags.get("up"))
		{
			yDelta += VELOCITY;
		}
		if (flags.get("down"))
		{
			yDelta -= VELOCITY;
		}
		if (flags.get("left"))
		{
			xDelta -= VELOCITY;
		}
		if (flags.get("right"))
		{
			xDelta += VELOCITY;
		}
	}
	
	private void handleInput(){
		while(Keyboard.next())
		{
			if(Keyboard.getEventKeyState() || Keyboard.isRepeatEvent())
			{
				if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE)
				{
					super.sm.pop();
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_A)
				{
					flags.put("left", true);
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_D)
				{
					flags.put("right", true);
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_W)
				{
					flags.put("up", true);
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_S)
				{
					flags.put("down", true);
				}
			}else
			{
				if(Keyboard.getEventKey() == Keyboard.KEY_A)
				{
					flags.put("left", false);
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_D)
				{
					flags.put("right", false);
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_W)
				{
					flags.put("up", false);
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_S)
				{
					flags.put("down", false);
				}
			}
		}
	}

}
