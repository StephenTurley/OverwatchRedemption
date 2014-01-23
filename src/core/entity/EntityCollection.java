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

import core.Debug;
import core.exception.EntityNotConstructedException;
import core.exception.EntityNotFoundException;
import core.graphics.Camera;
import core.network.EntityDataPacket;
/**
 * Stores and provides access to all the entities in a Level.
 * 
 * @param <T> Entity subclass
 * 
 */
public class EntityCollection<T extends Entity> {
	
	private final ConcurrentHashMap<UUID, T> collection;
	
	
	public EntityCollection(ArrayList<T> entityList)
	{
		collection = new ConcurrentHashMap<>();
		
		for(T e : entityList)
		{
			collection.put(e.getID(), e);
		}
	}
	
	/**
	 * 
	 * @param entity
	 * @return returns a unique identifier for the entity
	 */
	public UUID addEntity(UUID uuid, T entity)
	{
		collection.put(uuid, entity);
		
		return uuid;
	}
	/**
	 * Will update existing entities and add new ones
	 * @param entities The entities to be updated or added
	 * @return List of new entities added
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<T> addUpdateEntities(EntityDataPacket[] entities, boolean isClient)
	{
		ArrayList<T> newEntities = new ArrayList<>();
		
		for(EntityDataPacket e: entities)
		{
			
			try 
			{
				Entity theEntity;
				if(collection.get(e.uuid) == null ) 
				{
					if(isClient)
					{
						theEntity = EntityFactory.createClientEntity(e.className, e.uuid, e.position, e.layer);
						theEntity.loadAssets() ;
					}
					else
					{
						theEntity = EntityFactory.createServerEntity(e.className, e.uuid, e.position, e.layer);
					}
				}
				else
				{
					theEntity = collection.get(e.uuid);
					
					theEntity.setLocation(e.position);
					theEntity.setLayer(e.layer);
				}
				
				theEntity.setRotation(e.rotation);
				if(e.direction != null)
				{
					theEntity.setDirection(e.direction);
				}
				
				collection.put(theEntity.getID(), (T)theEntity);
					
			} catch (EntityNotFoundException e1) {
				Debug.Trace("Entity not found!");
				e1.printStackTrace();
			} catch (EntityNotConstructedException e1) {
				Debug.Trace("Entity not constructed!");
				e1.printStackTrace();
			} catch (Exception ex)
			{
				Debug.Trace(ex.getMessage());
			}
			
		}
		
		return newEntities;
		
	}
	public T getEntity(UUID uuid)
	{
		return collection.get(uuid);
	}
	
	public ArrayList<T> getEntities() 
	{
		ArrayList<T> entities = new ArrayList<>();
		for(T e : collection.values())
		{
			entities.add(e);
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
