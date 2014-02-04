/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.graphics;

import core.entity.ClientEntity;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;

import core.entity.Entity;

public class Camera {
	private Point position;
	int width, height;
	private boolean colliding;
	
	public Camera(int width,int height)
	{
		position = new Point(0, 0);
		colliding = true;
		this.width =  width;
		this.height = height;
	}
	public void setPosition(Entity entity, int globalPixelWidth, int globalPixelHeight)
	{
		int x = entity.getPosX() - (width/2);
		int y = entity.getPosY() - (height/2);
		
		boolean tempColliding = false; //thread safe =D
		
		if(x <= 0) 
		{
			tempColliding = true;
			x = 0;
		}
		if(y <= 0)
		{
			tempColliding = true;
			y = 0;
		}
		if(x + width >= globalPixelWidth)
		{
			tempColliding = true;
			x = (globalPixelWidth - width);
		}
		if(y + height >= globalPixelHeight)
		{
			tempColliding = true;
			y = (globalPixelHeight - height);
		}
		
		colliding = tempColliding;

		position.setLocation(x, y);
	}
	public Point getTileOffset(int tileWidth, int tileHeight)
	{
		int xOffSet = 0;
		int yOffSet = 0;
		
		if(position.getX() < tileWidth && position.getX() > 0)
		{
			xOffSet = position.getX();
		}
		else if(position.getX() >= tileWidth)
		{
			xOffSet = position.getX() % tileWidth;
		}
		else if(position.getX() < 0)
		{
			xOffSet = 0;
		}
		if(position.getY() < tileHeight && position.getY() > 0)
		{
			yOffSet = position.getY();
		}
		else if(position.getY() >= tileHeight)
		{
			yOffSet = position.getY() % tileHeight;
		}
		else if(position.getY() < 0)
		{
			yOffSet = 0;
		}
		
		return new Point(xOffSet, yOffSet);
	}
	public Point computeScreenCoordinates(Point globalCoordinate)
	{
		return (new Point(globalCoordinate.getX() - position.getX(), globalCoordinate.getY() - position.getY()));
	}
	public boolean isVisible(Point globalCoordinate)
	{
		Rectangle cameraRect = getCameraRect();
		
		return cameraRect.contains(globalCoordinate);
	}
	public boolean isVisible(Rectangle rectangle)
	{
		Rectangle cameraRect = getCameraRect();
		
		return cameraRect.contains(rectangle) || cameraRect.intersects(rectangle);
	}
	
	public Rectangle getCameraRect() {
		Rectangle cameraRect = new Rectangle(position.getX(), position.getY(), width, height);
		return cameraRect;
	}
	
	public int getX()
	{
		return position.getX();
	}
	public int getY()
	{
		return position.getY();
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
	public Point getPosition() {
		return position;
	}
	public boolean isColliding()
	{
		return colliding;
	}
	
	/**
	 * Draws all entities that are in view of the camera at a given layer
	 * @param e
	 * @param layer
	 */
	public void drawVisible(ClientEntity e,  int layer) {
		if(isVisible(e.getBoundingRect()) && e.getLayer() == layer)
		{
			e.draw(this);
		}
	}
}
