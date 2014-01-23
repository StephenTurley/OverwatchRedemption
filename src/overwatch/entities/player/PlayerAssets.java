/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package overwatch.entities.player;

import org.lwjgl.util.Point;

import overwatch.cardinality.Direction;
import overwatch.cardinality.DirectionMap;
import core.entity.EntityAssets;
import core.entity.EntityState;
import core.graphics.Camera;
import core.graphics.SpriteLoader;
import core.graphics.StaticSpriteSheet;

public class PlayerAssets implements EntityAssets{
	
	private final DirectionMap directionMap;
	private Direction currentDirection;
	private EntityState currentState;
	
	public PlayerAssets(EntityState startingEntityState) throws Exception
	{
		StaticSpriteSheet spriteSheet = SpriteLoader.load("/spriteSheetData/RedGuy.xml");
		directionMap = new DirectionMap(spriteSheet, startingEntityState);
		currentDirection = Direction.N;
		currentState = startingEntityState;
	}

	@Override
	public void draw(Camera camera, Point position) 
	{
		directionMap.getAnimation(currentDirection, currentState).draw(camera, position);
	}

	@Override
	public void update(int delta) {
		directionMap.update(delta, currentState);
	}

	@Override
	public void setState(EntityState entityState) 
	{
		currentState = entityState;
	}
	
	public void setDirection(Direction direction)
	{
		currentDirection = direction;
	}

}
