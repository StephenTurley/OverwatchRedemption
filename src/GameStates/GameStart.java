package GameStates;

import org.lwjgl.opengl.*;

import Core.Debug;
import Core.StateManager.GameState;

public class GameStart implements GameState {

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw() {
		// Clear the screen and depth buffer
	
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); 

		 

		// set the color of the quad (R,G,B,A)
	
		GL11.glColor3f(0.5f,0.5f,1.0f);

		// draw quad
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(100,100);
			GL11.glVertex2f(100+200,100);
			GL11.glVertex2f(100+200,100+200);
			GL11.glVertex2f(100,100+200);
		GL11.glEnd();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void enter()
	{
		Debug.Trace("Start State has been entered!");
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 0, 600, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}
