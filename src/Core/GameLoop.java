package Core;

import org.lwjgl.opengl.Display;

import Core.StateManager.StateManager;


public class GameLoop implements Runnable {
	
	private StateManager sm;

	
	public GameLoop(StateManager sm)
	{
		super();
		this.sm = sm;
	}

	@Override
	public void run() {
		while(!Display.isCloseRequested())
		{
			Display.sync(60);	//60fps
			
			sm.draw();
			sm.update();
		}
	}
	
	

}
