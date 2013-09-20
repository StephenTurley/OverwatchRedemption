package core;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

import core.stateManager.StateManager;



public class GameLoop implements Runnable {
	
	private StateManager sm;
	/** time at last frame */
	private long lastFrame;
	
	/** frames per second */
	private int fps;
	
	/** last fps time */
	private long lastFPS;

	
	public GameLoop(StateManager sm)
	{
		super();
		this.sm = sm;
	}

	@Override
	public void run() {
		getDelta(); // call once before loop to initialise lastFrame

		lastFPS = getTime(); // call before loop to initialise fps timer
		
		while(!Display.isCloseRequested())
		{
			int delta = getDelta();
				
			
			sm.draw();
			sm.update(delta);
			
			updateFPS();
			Display.sync(120);	//60fps
		}
		if(Game.getServer()!=null)
		{
			Game.killServer();
		}
		Display.destroy();
	}
	
	 

	/**

	* Calculate how many milliseconds have passed

	* since last frame.

	*

	* @return milliseconds passed since last frame

	*/

	public int getDelta() {
	
		long time = getTime();
	
		int delta = (int) (time - lastFrame);
	
		lastFrame = time;
	
		 
	
		return delta;

	}

	 

	/**

	* Get the accurate system time

	*

	* @return The system time in millisecond
	*/

	public long getTime() {

		return (Sys.getTime() * 1000) / Sys.getTimerResolution();

	}

	 

	/**

	* Calculate the FPS and set it in the title bar

	*/

	public void updateFPS() {

		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		
		fps++;
	}
	

}
