/*******************************************************************************
 * Copyright 2014 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.entity;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.UUID;

import org.lwjgl.util.Point;

import core.exception.EntityNotConstructedException;
import core.exception.EntityNotFoundException;

/*
 * This class is used to define Entities at runtime. 
 * The core engine has no way of knowing what your Entity implementations are
 */
public class EntityFactory {
	
	private static HashMap<String, Class<? extends EntityTemplate>> entityTemplates;
	
	public static void register(Class<? extends EntityTemplate> entityClass)
	{
		if(entityTemplates == null)
		{
			entityTemplates = new HashMap<>();
		}
		
		entityTemplates.put(entityClass.getSimpleName(), entityClass);
	}
	
	
	/**
	 * This will create ServerEntity classes if the template has been registered
	 * @param className the class name that implements Entity
	 * @param uuid the unique identifier 
	 * @param location the starting location
	 * @param layer the starting layer
	 * @return the new Entity
	 * @throws EntityNotFoundException
	 * @throws EntityNotConstructedException
	 */
	public static ServerEntity createServerEntity(String className,UUID uuid, Point location, int layer) throws EntityNotFoundException, EntityNotConstructedException
	{		
		EntityTemplate template = getTemplate(className);
		
		return template.createServerEntity(uuid, location, layer);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Entity> T createEntity(Class<T> clazz, String className,UUID uuid, Point location, int layer) throws EntityNotFoundException, EntityNotConstructedException
	{
		if(clazz == ServerEntity.class)
		{
			return (T)createServerEntity(className, uuid, location, layer);
		}
		if(clazz == ClientEntity.class)
		{
			return (T)createClientEntity(className, uuid, location, layer);
		}
		throw new EntityNotConstructedException("Invalid Entity class type");
	}
	
	/**
	 * This will create ClientEntity classes if the template has been registered
	 * @param className the class name that implements Entity
	 * @param uuid the unique identifier 
	 * @param location the starting location
	 * @param layer the starting layer
	 * @return the new Entity
	 * @throws EntityNotFoundException
	 * @throws EntityNotConstructedException
	 */
	public static ClientEntity createClientEntity(String className,UUID uuid, Point location, int layer) throws EntityNotFoundException, EntityNotConstructedException
	{
		EntityTemplate template = getTemplate(className);
		
		return template.createClientEntity(uuid, location,layer);
	}
	
	private static EntityTemplate getTemplate(String className) throws EntityNotFoundException, EntityNotConstructedException
	{

		if(entityTemplates == null) throw new EntityNotFoundException("No Entities have been registered!");
		
		Class<? extends EntityTemplate> eTemplate = entityTemplates.get(className);
		
		if(eTemplate == null) throw new EntityNotFoundException(className + " doesn't exist! Have you registered it?");
		
		Constructor<? extends EntityTemplate> cstor;
		try {
			cstor = eTemplate.getConstructor();
			
			EntityTemplate template = cstor.newInstance();
			
			return template;
			
		} catch (Exception e) 
		{
			throw new EntityNotConstructedException(e.getMessage());
		} 
	}

}
