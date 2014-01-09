/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.entity;

import java.util.UUID;

import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;

import core.graphics.Camera;
import core.network.EntityDataPacket;


public abstract class Entity {
	
	protected Point location;
	protected int  width, height, layer;
	protected float scale, rotation;
	protected UUID id;
	protected EntityState entityState;
	
	public Entity(UUID uuid,Point location, int width, int height, int layer) {
		this.location = location;
		this.width = width;
		this.height = height;
		this.scale = 1.0f;
		this.rotation = 0.0f;
		this.layer = layer;
		this.id = uuid;
	}
	
	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}
	/*
	 * useful for checking if an entity is 'standing' on a specific tile.
	 */
	public Point getBottomLocation()
	{
		return new Point(location.getX(), location.getY() + height);
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
	
	public UUID getID()
	{
		return id;
	}
	
	public Rectangle getBoundingRect()
	{
		return new Rectangle(location.getX(),location.getY(), width,height);
	}
	
	public EntityDataPacket getDataPacket()
	{
		EntityDataPacket pkt = new EntityDataPacket();
		
		pkt.className = this.getClass().getSimpleName();
		pkt.uuid = this.id;
		pkt.position = this.location;
		pkt.layer = this.layer;
		
		return pkt;
	}
	
	public void setState(EntityState state)
	{
		entityState = state;
	}
	
	public EntityState getCurrentState()
	{
		return entityState;
	}
	
	public abstract void draw(Camera camera);

	public abstract void update(int delta);
	 
	public abstract void collideWith(Entity entity);
	
	public abstract void loadAssets();
	
	


}
