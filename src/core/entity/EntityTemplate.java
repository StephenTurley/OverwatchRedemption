package core.entity;

import java.util.UUID;

import org.lwjgl.util.Point;

/*
 * This class is used to register and create entities.
 */

public interface EntityTemplate {
	
	public abstract ServerEntity createServerEntity(UUID uuid, Point location, int layer);
	public abstract ClientEntity createClientEntity(UUID uuid, Point location, int layer);

}
