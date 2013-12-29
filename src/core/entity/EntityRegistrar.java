package core.entity;

import java.lang.reflect.Constructor;
import java.util.HashMap;

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
	
	public Entity createEntity(String className,Point location,int width, int height, int layer) throws EntityNotFoundException, EntityNotConstructedException
	{
		if(entityClasses == null) throw new EntityNotFoundException("No Entities have been registered!");
		
		Class<? extends Entity> eClass = entityClasses.get(className);
		
		if(eClass == null) throw new EntityNotFoundException(className + " doesn't exist! have you registered it?");
		
		Constructor<? extends Entity> cstor;
		try {
			cstor = eClass.getConstructor(Point.class,Integer.TYPE,Integer.TYPE, Integer.TYPE);
			
			Entity entity = cstor.newInstance(location,width,height,layer);
			
			return entity;
			
		} catch (Exception e) {
			throw new EntityNotConstructedException(e.getMessage());
		} 
		
		
	}

}
