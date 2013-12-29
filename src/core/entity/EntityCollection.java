package core.entity;

import java.util.ArrayList;

import org.lwjgl.util.Rectangle;

import core.graphics.Camera;
/**
 * Stores and provides access to all the entities in a Level.
 * 
 */
public class EntityCollection {
	
	private ArrayList<Entity> collection;
	
	public EntityCollection()
	{
		collection = new ArrayList<Entity>();
	}
	
	/**
	 * 
	 * @param entity
	 * @return returns the index of the entity. This is used to access the entity in the future
	 */
	public int addEntity(Entity entity)
	{
		collection.add(entity);
		
		return collection.indexOf(entity);
	}
	
	/**
	 * This method will draw all the entities that are contained by the globalArea Rectangle and on the given layer
	 * @param camera
	 * @param globalArea This Rectangle should represent the global coordinate system
	 * @param layer the current layer
 	 */
	public void drawInArea(Camera camera, Rectangle globalArea, int layer)
	{
		for(Entity e : collection)
		{
			if(globalArea.contains(e.location) && e.layer == layer)
			{
				e.draw(camera);
			}
		}
	}

}
