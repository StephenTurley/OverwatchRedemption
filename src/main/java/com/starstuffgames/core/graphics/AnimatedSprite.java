package com.starstuffgames.core.graphics;

import org.lwjgl.util.Point;

/**
 * This class will draw a series of StaticSprites at a specified framerate and location. 
 *
 */
public class AnimatedSprite implements Sprite {
	
	private String name;
	private final StaticSprite frames[];
	private int fps, cycles, elapsed, currentFrame;
	private boolean looping;
	
	public AnimatedSprite(String name, int fps, int frameCount, boolean looping)
	{
		this.name = name;
		this.fps = fps;
		this.looping = looping;
		frames = new StaticSprite[frameCount];
		reset();
	}
	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getName()
	{
		return name;
	}

	public void addFrame(int frameIdx, StaticSprite frame)
	{
		frames[frameIdx] = frame;
	}

	/**
	 * @return number of completed animation cycles.
	 */
	public int getCycles() {
		return cycles;
	}

	private void reset()
	{
		cycles = 0;
		elapsed = 0;
		currentFrame = 0;
	}
	@Override
	public void draw(Camera camera, Point position) {
		if(elapsed  >= 1000/fps)
		{
			elapsed = 0;
			if(currentFrame < frames.length - 1)
			{
				currentFrame++;
			}
			else if (looping)
			{
				currentFrame = 0;
				cycles++;
			}
			else //not looping, stop on last frame
			{
				currentFrame = frames.length - 1;
				cycles = 1;
			}
		}
		frames[currentFrame].draw(camera, position);
	}

	/**
	 * This method must be called each GameLoop cycle for the animation to work
	 * @param delta time elapsed in milliseconds
	 */
	public void update(int delta) {
		elapsed += delta;
		
		//if draw has stopped being called, reset animation
		if(elapsed > 2000 / fps)
		{
			reset();
		}
		
	}
}
