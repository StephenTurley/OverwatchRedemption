/*******************************************************************************
 * Copyright 2014 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.entity;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.lwjgl.util.Rectangle;

import core.exception.EntityNotConstructedException;
import core.exception.EntityNotFoundException;
import core.graphics.Camera;
import core.network.EntityDataPacket;
/**
 * Stores and provides access to all the entities in a Level.
 * 
 */
public class EntityCollection {
	
	private ConcurrentHashMap<UUID, Entity> collection;
	
	public EntityCollection(ConcurrentHashMap<UUID, Entity> entityMap)
	{
		collection = entityMap;
	}
	
	public EntityCollection(ArrayList<Entity> entityList)
	{
		collection = new ConcurrentHashMap<UUID, Entity>();
		
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
	public ArrayList<Entity> addUpdateEntities(EntityDataPacket[] entities, boolean loadNewAssets)
	{
		ArrayList<Entity> newEntities = new ArrayList<Entity>();
		
		for(EntityDataPacket e: entities)
		{
			
			try 
			{
				
				if(collection.get(e.uuid) == null && loadNewAssets) 
				{
					Entity theEntity = EntityFactory.createEntity(e.className, e.uuid, e.position, e.layer);
					theEntity.loadAssets() ;
				}
				else
				{
					Entity theEntity = collection.get(e.uuid);
					
					theEntity.setLocation(e.position);
					theEntity.setLayer(e.layer);
					
					collection.put(theEntity.getID(), theEntity);
				}
					
			} catch (EntityNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (EntityNotConstructedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
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
			if(camera.isVisible(e.getBoundingRect()) && e.layer == layer)
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
		for(Entity e : collection.values())
		{
			e.loadAssets();
		}
	}

	public void update(int delta) {
		for(Entity e : collection.values())
		{
			e.update(delta);
		}
	}

}
