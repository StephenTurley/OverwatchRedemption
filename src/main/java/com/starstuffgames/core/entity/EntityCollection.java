/*******************************************************************************
 * Copyright 2014 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package com.starstuffgames.core.entity;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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
	 * @param uuid
	 * @param entity
	 * @return returns a unique identifier for the entity
	 */
	public UUID putEntity(UUID uuid, T entity)
	{
		collection.put(uuid, entity);
		
		return uuid;
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
	public ArrayList<T> getEntities(Class<? extends T> clazz)
	{
		ArrayList<T> entities = new ArrayList<>();
		
		for(T e : collection.values())
		{
			if(e.getClass() == clazz)
			{
				entities.add(e);
			}
		}
		return entities;
	}

	public void update(int delta) {
		
		for(Entity e : collection.values())
		{
			e.update(delta);
		}
	}
	
	
}
