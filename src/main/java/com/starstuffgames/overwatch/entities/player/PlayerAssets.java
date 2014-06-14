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
import com.starstuffgames.core.graphics.AnimationSet;
import com.starstuffgames.core.graphics.Camera;
import com.starstuffgames.core.graphics.cardinality.Direction;

public class PlayerAssets implements EntityAssets
{	
	private final String SPRITE_PATH = "/spriteSheetData/RedGuy.xml";
	private final String ANIMATION_PATH = "/animations/RedGuyAnim.xml";
	private AnimationSet animations;
	private Direction currentDirection;
	private EntityState currentState;
	
	public PlayerAssets(EntityState startingEntityState) throws Exception
	{
		animations = new AnimationSet(SPRITE_PATH, ANIMATION_PATH , startingEntityState);
		
		currentDirection = Direction.N;
		currentState = startingEntityState;
	}

	@Override
	public void draw(Camera camera, Point position) 
	{
		animations.draw(currentDirection, currentState, camera, position);
	}

	@Override
	public void update(int delta) {
		animations.update(delta);
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
