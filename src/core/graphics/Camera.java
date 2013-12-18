package core.graphics;

import org.lwjgl.util.Point;

import core.Entity;

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
}
