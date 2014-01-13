/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.entity;

/*
 * This class will contain all sprites, animations, and sound effects
 * for the client. This class is used to separate features that are unique
 * to client entities, and not needed for server entities. 
 */
import core.graphics.DrawableComponent;

public interface EntityAssets extends DrawableComponent {
	
	public void load();

}
