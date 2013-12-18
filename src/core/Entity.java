/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core;

import org.lwjgl.util.Point;


public abstract class Entity {
	
	protected Point location;
	protected int  width, height;
	protected float scale, rotation;
	
	public Entity()
	{
		
	}
	
	public Entity(int width, int height)
	{
		this.location = new Point(0,0);
		this.width =  width;
		this.height = height;
	}
	
	public Entity(int startX, int startY, int width, int height, float scale, float rotation) {
		this.location = new Point(startX, startY);
		this.width = width;
		this.height = height;
		this.scale = scale;
		this.rotation = rotation;
	}
	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public int getPosX() {
		return location.getX();
	}

	public void setPosX(int locX) {
		location.setX(locX);
	}

	public int getPosY() {
		return location.getY();
	}

	public void setPosY(int locY) {
		location.setY(locY);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public void update(int delta) {
		
	}
	 
	public void collideWith(Entity entity)
	{
		
	}

}
