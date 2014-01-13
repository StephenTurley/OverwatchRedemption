/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.entity;

import org.lwjgl.util.Point;

import core.graphics.Camera;
/*
 * This class will contain all sprites, animations, and sound effects
 * for the client. This class is used to separate features that are unique
 * to client entities, and not needed for server entities. 
 */

public interface EntityAssets {
	
	public void draw(Camera camera, Point position);
	public void update(int delta);
	public void load();

}
