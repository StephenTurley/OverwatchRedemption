package core.entity;

import java.util.UUID;

import org.lwjgl.util.Point;

public abstract class ServerEntity extends Entity{
	
	public ServerEntity(UUID uuid, Point location, int width, int height,
			int layer) {
		super(uuid, location, width, height, layer);
		// TODO Auto-generated constructor stub
	}

	public abstract void update(int delta);
}
