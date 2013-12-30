/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package entities;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.UUID;

import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import core.entity.Entity;
import core.graphics.Camera;

public class Player extends Entity{

	private String name;
	private boolean ready;
	private int id;
	private Vector2f movementVector;
	
	private static final int WIDTH = 50;
	private static final int HEIGHT = 50;
	private static final float VELOCITY = 0.25f;
	
	//placeholder until this is parsed from map 
	private static final UUID ID = UUID.fromString("083c3854-a2de-4c1d-a18c-9445dec51042");
	
	public Player()
	{
		super(ID, new Point(0,0), WIDTH,HEIGHT,0);
	}
	public Player(String name)
	{
		super(ID, new Point(0,0), WIDTH,HEIGHT,0);
		this.name = name;
		this.ready = false;
		init();
	}
	public Player(Point location) {
		super(ID, location, WIDTH, HEIGHT,0);
		this.ready = false;
		init();
	}
	public Player(Point location, String name)
	{
		super(ID, location, WIDTH, HEIGHT, 0);
		this.name = name;
		this.ready = false;
		init();
	}
	public Player(UUID id, Point location, int layer)
	{
		super(id,location,WIDTH,HEIGHT,layer);
	}
	private void init()
	{
		movementVector = new Vector2f(0,0);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isReady() {
		return ready;
	}
	public void setReady(boolean ready) {
		this.ready = ready;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setMovementVector(Vector2f movementVector)
	{
		//value copy... not sure if I have to do this but I don't want it getting changed. 
		this.movementVector = new Vector2f(movementVector.x, movementVector.y);	
	}
	@Override
	public void update(int delta)
	{
		location.translate((int)(movementVector.x * VELOCITY * delta),(int)(movementVector.y * VELOCITY * delta));
	}

	public void draw(Camera camera)
	{
		
		if(camera.isVisible(new Rectangle(location.getX(), location.getY(), width, height)))
		{
			Point screenCoord = camera.computeScreenCoordinates(super.location);
			int x = screenCoord.getX();
			int y = screenCoord.getY();
			
			glColor3f(0f,0f,0f);

			// draw quad
			glBegin(GL_QUADS);
				glVertex2f(x , y);					//TL
				glVertex2f(x + width, y);			//TR
				glVertex2f(x + width, y + height);	//BR
				glVertex2f(x, y + height);			//BL
			glEnd();
		}
		
	}
	@Override
	public void collideWith(Entity entity) {
		// TODO Auto-generated method stub
		
	}

}
