/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package com.starstuffgames.overwatch.entities.player;

import org.lwjgl.util.Point;

import com.starstuffgames.core.entity.EntityAssets;
import com.starstuffgames.core.entity.EntityState;
import com.starstuffgames.core.graphics.Camera;
import com.starstuffgames.core.graphics.SpriteSheet;
import com.starstuffgames.core.graphics.cardinality.Direction;
import com.starstuffgames.core.graphics.cardinality.DirectionMap;

public class PlayerAssets implements EntityAssets{
	
	private final DirectionMap directionMap;
	private Direction currentDirection;
	private EntityState currentState;
	
	public PlayerAssets(EntityState startingEntityState) throws Exception
	{
		SpriteSheet spriteSheet = new SpriteSheet("/spriteSheetData/RedGuy.xml");
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
