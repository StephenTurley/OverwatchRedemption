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
public class EntityRegistrar {
	
	private static HashMap<String, Class<? extends Entity>> entityClasses;
	
	public static void register(Class<? extends Entity> entityClass)
	{
		if(entityClasses == null)
		{
			entityClasses = new HashMap<String, Class<? extends Entity>>();
		}
		
		entityClasses.put(entityClass.getSimpleName(), entityClass);
	}
	
	/**
	 * This will create Entity classes if they have been registered
	 * @param className the class name that implements Entity
	 * @param uuid the unique identifier 
	 * @param location the starting location
	 * @param layer the starting layer
	 * @return the new Entity
	 * @throws EntityNotFoundException
	 * @throws EntityNotConstructedException
	 */
	public Entity createEntity(String className,UUID uuid, Point location, int layer) throws EntityNotFoundException, EntityNotConstructedException
	{
		if(entityClasses == null) throw new EntityNotFoundException("No Entities have been registered!");
		
		Class<? extends Entity> eClass = entityClasses.get(className);
		
		if(eClass == null) throw new EntityNotFoundException(className + " doesn't exist! Have you registered it?");
		
		Constructor<? extends Entity> cstor;
		try {
			cstor = eClass.getConstructor(UUID.class, Point.class, Integer.TYPE);
			
			Entity entity = cstor.newInstance(uuid, location,layer);
			
			return entity;
			
		} catch (Exception e) {
			throw new EntityNotConstructedException(e.getMessage());
		} 
		
		
	}

}
