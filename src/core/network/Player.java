/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.network;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Vector2f;

import core.Entity;
import core.graphics.Camera;

public class Player extends Entity{

	private String name;
	private boolean ready;
	private int id;
	private Vector2f movementVector;
	
	private static final int WIDTH = 50;
	private static final int HEIGHT = 50;
	private static final float VELOCITY = 0.25f;
	
	public Player()
	{
		init();
	}
	
	public Player(int startX, int startY) {
		super(startX, startY, WIDTH, HEIGHT, 1.0f, 0.0f);
		this.ready = false;
		init();
	}
	public Player(int startX, int startY, String name)
	{
		super(startX, startY, WIDTH, HEIGHT, 1.0f, 0.0f);
		this.name = name;
		this.ready = false;
		init();
	}
	public Player(String name)
	{
		super(WIDTH,HEIGHT);
		this.name = name;
		this.ready = false;
		init();
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
		
		if(camera.isVisible(super.location))
		{
			Point screenCoord = camera.computeScreenCoordinates(super.location);
			int x = screenCoord.getX();
			int y = screenCoord.getY();
			
			glColor3f(0f,0f,0f);

			// draw quad
			glBegin(GL_QUADS);
				glVertex2f(x - width , y - height); //TL
				glVertex2f(x , y - height);			//TR
				glVertex2f(x , y);					//BR
				glVertex2f(x - width , y);			//BL
			glEnd();
		}
		
	}

}
