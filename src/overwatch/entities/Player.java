/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package overwatch.entities;

import java.util.UUID;

import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import overwatch.cardinality.Direction;
import overwatch.entities.entityAssets.PlayerAssets;
import core.Debug;
import core.Game;
import core.entity.Entity;
import core.entity.EntityState;
import core.graphics.Camera;

public class Player extends Entity{

	
	private Vector2f movementVector;
	private PlayerAssets assets;
	
	private static final int WIDTH = 32;
	private static final int HEIGHT = 32;
	private final float VELOCITY = 0.25f;
	
	
	public static enum State implements EntityState
	{
		IDLE,
		WALKING,
		RUNNING,
		DYING,
		DEAD;

		@Override
		public int getStateValue() {
			return ordinal();
		}

		@Override
		public EntityState getState(String string) {
			return State.valueOf(string);
		}
		
	}
	
	public Player()
	{
		super(UUID.randomUUID(), new Point(0,0),WIDTH,HEIGHT, 0);
		super.currentState = State.IDLE;
	}
	public Player(UUID id, Point location, int layer)
	{
		super(id,location,WIDTH,HEIGHT,layer);
		super.currentState = State.IDLE;
		
		movementVector = new Vector2f(0,0);
	}

	public void setMovementVector(Vector2f movementVector)
	{ 
		this.movementVector = movementVector;
	}
	@Override
	public void serverUpdate(int delta)
	{
		super.rotation = (float) Math.atan2(-movementVector.y,movementVector.x);
		location.translate((int)(movementVector.x * VELOCITY * delta),(int)(movementVector.y * VELOCITY * delta));
	}
	
	@Override
	public void clientUpdate(int delta)
	{
		if(assets!= null)
		{
			assets.update(delta);
			assets.setDirection(Direction.fromRadian(super.rotation));
			assets.setState(State.IDLE);
		}
	}
	@Override
	public void draw(Camera camera)
	{
		if (assets != null && camera.isVisible(new Rectangle(location.getX(), location.getY(), width, height)))
		{
			assets.draw(camera, this.location);
		}
	}
	@Override
	public void collideWith(Entity entity) {

	}

	@Override
	public void loadAssets() {
		try
		{
			assets = new PlayerAssets(super.currentState);
		}
		catch (Exception e)
		{
			Debug.Trace(e.getMessage());
			Game.exit(-1);
		}
	}
	
	@Override
	public void setState(EntityState state)
	{
		super.setState(state);
	}
	@Override
	public State getCurrentState()
	{
		return (State)super.getCurrentState();
	}
	
}
