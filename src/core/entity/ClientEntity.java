package core.entity;

import java.util.UUID;

import org.lwjgl.util.Point;

import core.graphics.Camera;

public abstract class ClientEntity extends Entity {
	
	public ClientEntity(UUID uuid, Point location, int width, int height,
			int layer) {
		super(uuid, location, width, height, layer);
	}
	@Override
	public abstract void update(int delta);
	@Override
	public abstract void draw(Camera camera);
	@Override
	public abstract void loadAssets();

}