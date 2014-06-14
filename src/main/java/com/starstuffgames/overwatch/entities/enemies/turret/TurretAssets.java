/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.starstuffgames.overwatch.entities.enemies.turret;

import com.starstuffgames.core.entity.EntityAssets;
import com.starstuffgames.core.entity.EntityState;
import com.starstuffgames.core.graphics.AnimationSet;
import com.starstuffgames.core.graphics.Camera;
import com.starstuffgames.core.graphics.cardinality.Direction;

import org.lwjgl.util.Point;

/**
 *
 * @author stephen
 */
public class TurretAssets implements EntityAssets
{
	private final String SPRITE_PATH = "/spriteSheetData/Turret.xml";
	private final String ANIMATION_PATH = "/animations/TurretAnim.xml";
    private AnimationSet animations;
    private Direction currentDirection;
    private EntityState currentState;
    
    public TurretAssets(EntityState startingEntityState) throws Exception
    {
        animations = new AnimationSet(SPRITE_PATH, ANIMATION_PATH, startingEntityState);
        currentDirection = Direction.N;
        currentState = startingEntityState; 
    }

    @Override
    public void setState(EntityState entityState)
    {
        this.currentState = entityState;
    }

    public void setDirection(Direction direction)
    {
        this.currentDirection = direction;
    }

    @Override
    public void draw(Camera camera, Point position)
    {
        animations.draw(currentDirection, currentState ,camera, position);
    }

    @Override
    public void update(int delta)
    {
        animations.update(delta);
    }
	
}
