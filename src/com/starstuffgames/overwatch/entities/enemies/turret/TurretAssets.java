/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.starstuffgames.overwatch.entities.enemies.turret;

import com.starstuffgames.core.entity.EntityAssets;
import com.starstuffgames.core.entity.EntityState;
import com.starstuffgames.core.graphics.Camera;
import com.starstuffgames.core.graphics.SpriteLoader;
import com.starstuffgames.core.graphics.StaticSpriteSheet;
import com.starstuffgames.overwatch.cardinality.Direction;
import com.starstuffgames.overwatch.cardinality.DirectionMap;
import org.lwjgl.util.Point;

/**
 *
 * @author stephen
 */
public class TurretAssets implements EntityAssets
{
    private final DirectionMap directionMap;
    private Direction currentDirection;
    private EntityState currentState;
    
    public TurretAssets(EntityState startingEntityState) throws Exception
    {
        StaticSpriteSheet spriteSheet = SpriteLoader.load("/spriteSheetData/Turret.xml");
        directionMap = new DirectionMap(spriteSheet, startingEntityState);
        currentDirection = Direction.N;
        currentState = startingEntityState;
    }

	@Override
	public void setState(EntityState entityState)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void draw(Camera camera, Point position)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void update(int delta)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
