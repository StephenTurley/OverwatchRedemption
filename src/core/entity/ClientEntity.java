package core.entity;

import java.util.UUID;

import org.lwjgl.util.Point;

import core.graphics.Camera;

public abstract class ClientEntity extends Entity {
	
	public ClientEntity(UUID uuid, Point location, int width, int height,
			int layer, String templateClassString) {
		super(uuid, location, width, height, layer, templateClassString);
	}
	@Override
	public abstract void update(int delta);
	public abstract void draw(Camera camera);
	public abstract void loadAssets();

}
