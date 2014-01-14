package core.graphics;

import org.lwjgl.util.Point;

public interface Sprite {
	public void draw(Camera camera, Point position);
	public String getName();
}
