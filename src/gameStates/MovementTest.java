package gameStates;

import java.util.HashMap;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import static org.lwjgl.opengl.GL11.*;

import core.Debug;
import core.Game;
import core.stateManager.GameState;
import core.stateManager.StateManager;
import net.java.games.input.*;


public class MovementTest extends GameState {

	public MovementTest(StateManager sm) {
		super(sm);
		// TODO Auto-generated constructor stub
	}

	private float xVector = 0;
	private float yVector = 0;
	private float deadZone;
	private final float VELOCITY = 1;
	private HashMap<String, Boolean> flags = new HashMap<String, Boolean>();
	private Controller gamepad;
	
	public void update(int delta) {
		
		handleInput(delta);
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
		deadZone = Game.getGameConfig().getJoyStickDeadZone();
		
		flags.put("up", false);
		flags.put("down", false);
		flags.put("left", false);
		flags.put("right", false);
		
		gamepad = Game.getGamePad();
		
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
	
	private void handleInput(int delta){
		
		if(gamepad != null)
		{
			gamepad.poll();
			float x = 0;
			float y = 0;
			for(Component c : gamepad.getComponents())
			{
				//System.out.println(c.getName()+" : "+ c.getPollData());
				if(c.getName().equals("x"))
				{	
					x = c.getPollData();
				}
				if(c.getName().equals("y"))
				{
					y = c.getPollData();
				}
				if(c.getName().equals("Select")||c.getName().equals("Unknown"))
				{
					if(c.getPollData() == 1.0)
					{
						super.sm.pop();
					}
				}
			}
			Vector2f leftStickInput = new Vector2f(x,y);
			if(leftStickInput.lengthSquared() > deadZone)
			{
				xVector += VELOCITY * delta * leftStickInput.x;
				yVector += VELOCITY * delta * leftStickInput.y * -1;
			}
		}
		
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
