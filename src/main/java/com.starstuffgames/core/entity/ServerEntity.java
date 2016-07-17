package com.starstuffgames.core.entity;

import java.util.UUID;

import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;

public abstract class ServerEntity extends Entity implements EntityObserver{
	
	public ServerEntity(UUID uuid, Point location, int width, int height,
			int layer, String templateClassString) {
		super(uuid, location, width, height, layer,templateClassString);
		// TODO Auto-generated constructor stub
	}

	@Override
	public abstract void update(int delta);
	
	public abstract void collideWith(Entity entity);
	
	public boolean checkCollision(Entity entity)
	{
		
		Rectangle thisRect = this.getBoundingRect();
		Rectangle thatRect = entity.getBoundingRect();
		
		if(this.getLayer() != entity.getLayer())
			return false;
		
		return (thisRect.intersects(thatRect) ||
				thisRect.contains(thatRect) ||
				thatRect.contains(thisRect));
	}
}
