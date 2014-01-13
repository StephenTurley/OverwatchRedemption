package core.graphics;

import org.lwjgl.util.Point;

public interface Sprite {
	public void draw(Camera camera, Point position);
	public void update(int delta);
	public String getName();
}
