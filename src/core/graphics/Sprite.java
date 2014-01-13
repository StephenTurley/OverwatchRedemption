package core.graphics;

public interface Sprite {
	public void draw(Camera camera);
	public void update(int delta);
	public String getName();
}
