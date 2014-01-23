/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
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
	
	private int desiredFrameRate;
	
	/** last fps time */
	private long lastFPS;

	
	public GameLoop(StateManager sm)
	{
		super();
		this.sm = sm;
	}

	@Override
	public void run() {
		
		getDelta(); // call once before loop to initialize lastFrame
		
		desiredFrameRate = Game.getGameConfig().getFrameRate();
		
		lastFPS = getTime(); // call before loop to initialize fps timer
		
		while(!Display.isCloseRequested())
		{
			int delta = getDelta();
				
			
			
			sm.update(delta);
			sm.draw();
			updateFPS();
			
			if(Game.isServer)
			{
				Game.updateServer(delta);
			}
			
			
			
			Display.sync(desiredFrameRate);
		}

		Game.exit(0);

	}
	
	public void runServer()
	{
		
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
