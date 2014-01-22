package core.entity;

import java.util.UUID;

import org.lwjgl.util.Point;

/*
 * This class is used to register and create entities.
 */

public interface EntityTemplate {
	
	public ServerEntity createServerEntity(UUID uuid, Point location, int layer);
	public ClientEntity createClientEntity(UUID uuid, Point location, int layer);

}
