package core.entity;

import java.util.HashMap;
import java.util.UUID;

import org.lwjgl.util.Rectangle;

import core.graphics.Camera;
/**
 * Stores and provides access to all the entities in a Level.
 * 
 */
public class EntityCollection {
	
	private HashMap<UUID, Entity> collection;
	
	public EntityCollection(HashMap<UUID, Entity> entityMap)
	{
		collection = entityMap;
	}
	
	/**
	 * 
	 * @param entity
	 * @return returns a unique identifier for the entity
	 */
	public UUID addEntity(UUID uuid, Entity entity)
	{
		collection.put(uuid, entity);
		
		return uuid;
	}
	
	/**
	 * This method will draw all the entities that are contained by the globalArea Rectangle and on the given layer
	 * @param camera
	 * @param globalArea This Rectangle should represent the global coordinate system
	 * @param layer the current layer
 	 */
	public void drawInArea(Camera camera, Rectangle globalArea, int layer)
	{
		for(Entity e : collection.values())
		{
			if(globalArea.contains(e.location) && e.layer == layer)
			{
				e.draw(camera);
			}
		}
	}

}
