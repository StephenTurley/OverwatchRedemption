package GameStates;

import static org.lwjgl.opengl.GL11.*;

import java.util.HashMap;
import org.lwjgl.input.Keyboard;

import Core.Debug;
import Core.StateManager.GameState;
import Core.StateManager.StateManager;

public class GameStart extends GameState {
	
	private float xVector = 0;
	private float yVector = 0;
	private final float VELOCITY = 1;
	private HashMap<String, Boolean> flags = new HashMap<String, Boolean>();
	
	public GameStart(StateManager sm)
	{
		super(sm);
	}


	public void update(int delta) {
		
		handleInput();
		moveQuad(delta);
	}


	public void draw() {
		// Clear the screen and depth buffer
	
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 

		 

		// set the color of the quad (R,G,B,A)
	
		glColor3f(0.5f,0.5f,1.0f);

		// draw quad
		glBegin(GL_QUADS);
			glVertex2f(100 + xVector,100 + yVector);
			glVertex2f(300 + xVector,100 + yVector);
			glVertex2f(300 + xVector,300 + yVector);
			glVertex2f(100 + xVector,300 + yVector);
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
	
	private void moveQuad(int delta)
	{
		if (flags.get("up"))
		{
			yVector += VELOCITY * delta;
		}
		if (flags.get("down"))
		{
			yVector -= VELOCITY * delta;
		}
		if (flags.get("left"))
		{
			xVector -= VELOCITY * delta;
		}
		if (flags.get("right"))
		{
			xVector += VELOCITY * delta;
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
