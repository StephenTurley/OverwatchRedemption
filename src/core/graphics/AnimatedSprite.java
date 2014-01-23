package core.graphics;

import java.util.ArrayList;

import org.lwjgl.util.Point;

/**
 * This class will draw a series of StaticSprites at a specified framerate and location. 
 *
 */
public class AnimatedSprite implements Sprite {
	
	private String name;
	private final ArrayList<StaticSprite> frames;
	private int fps, cycles, elapsed, currentFrame;
	
	public AnimatedSprite(String name, int fps)
	{
		this.name = name;
		this.fps = fps;
		frames = new ArrayList<>();
		reset();
	}
	
	public AnimatedSprite(String name,int fps, ArrayList<StaticSprite> frames)
	{
		this.name = name;
		this.frames = frames;
		this.fps = fps;
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
		frames.add(frameIdx, frame);
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
		if(elapsed >= fps/1000)
		{
			elapsed = 0;
			if(currentFrame < frames.size() - 1)
			{
				currentFrame++;
			}
			else
			{
				currentFrame = 0;
				cycles++;
			}
		}
		frames.get(currentFrame).draw(camera, position);
	}

	/**
	 * This method must be called each GameLoop cycle for the animation to work
	 * @param delta time elapsed in milliseconds
	 */
	public void update(int delta) {
		elapsed += delta;
	}
	
	
}
