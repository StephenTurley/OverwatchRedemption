package core.entity;

import java.util.ArrayList;
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
	
	public EntityCollection(ArrayList<Entity> entityList)
	{
		collection = new HashMap<UUID, Entity>();
		
		for(Entity e : entityList)
		{
			collection.put(e.getID(), e);
		}
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
	 * Will update existing entities and add new ones
	 * @param entities The entities to be updated or added
	 * @param loadNewAssets if true, entity assets will be loaded for new entities
	 * @return List of new entities added
	 */
	public ArrayList<Entity> addUpdateEntities(ArrayList<Entity> entities, boolean loadNewAssets)
	{
		ArrayList<Entity> newEntities = new ArrayList<Entity>();
		
		for(Entity e: entities)
		{
			if(collection.get(e.id) == null && loadNewAssets) e.loadAssets() ;
			collection.put(e.id, e);
		}
		
		return newEntities;
		
	}
	public Entity getEntity(UUID uuid)
	{
		return collection.get(uuid);
	}
	
	public ArrayList<Entity> getEntities() 
	{
			return new ArrayList<Entity>(collection.values());
	}
			
	public ArrayList<Entity> getEntities(Class<? extends Entity> entityClass)
	{
		ArrayList<Entity> entities = new ArrayList<Entity>();
		
		for(Entity e : collection.values())
		{
			if(e.getClass() == entityClass)
			{
				entities.add(e);
			}
		}
		
		return entities;
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
			if(globalArea.contains(e.getBottomLocation()) && e.layer == layer)
			{
				e.draw(camera);
			}
		}
	}
	/**
	 * Draws all entities that are in view of the camera at a given layer
	 * @param camera
	 * @param layer
	 */
	public void drawVisible(Camera camera, int layer) {
		for(Entity e : collection.values())
		{
			if(camera.getCameraRect().contains(e.getBottomLocation()) && e.layer == layer)
			{
				e.draw(camera);
			}
		}
		
	}

	/**
	 * Load the assets for the Entities
	 * This should only happen on the Client
	 */
	public void loadAssets()
	{
		
	}

	public void update(int delta) {
		for(Entity e : collection.values())
		{
			e.update(delta);
		}
	}

}