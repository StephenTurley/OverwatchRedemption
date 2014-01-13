/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package entities.entityAssets;

import org.lwjgl.util.Point;

import core.entity.EntityAssets;
import core.graphics.Camera;
import core.graphics.SpriteLoader;
import core.graphics.StaticSpriteSheet;

public class PlayerAssets implements EntityAssets{
	
	private StaticSpriteSheet spriteSheet;
	
	public PlayerAssets()
	{
		load();
	}

	@Override
	public void draw(Camera camera, Point position) {
		spriteSheet.getStaticSprite("N_IDLE_1").draw(camera, position);	
	}

	@Override
	public void update(int delta) {
		
	}

	@Override
	public void load() {
		spriteSheet = SpriteLoader.load("/spriteSheetData/RedGuy.xml");
	}

}
