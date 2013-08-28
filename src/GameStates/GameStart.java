package GameStates;

import static org.lwjgl.opengl.GL11.*;

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
	
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 

		 

		// set the color of the quad (R,G,B,A)
	
		glColor3f(0.5f,0.5f,1.0f);

		// draw quad
		glBegin(GL_QUADS);
			glVertex2f(10,10);
			glVertex2f(100+200,100);
			glVertex2f(100+200,100+200);
			glVertex2f(100,100+200);
		glEnd();
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
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 800, 0, 600, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}
	
	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}
