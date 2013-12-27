/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.graphics;
/**
 * 
 * A TextureCoord stores texture demensions and texture ID
 * Useful for defining a specific sprite or tile within a TextureAtlas or TileSet
 */
public class TextureCoord {
	
	public final int X, Y, X2, Y2, glTextureID;
	
	public TextureCoord(int glTextureID, int X, int Y, int X2, int Y2)
	{
		this.X = X;
		this.Y = Y;
		this.X2 = X2;
		this.Y2 = Y2;
		this.glTextureID = glTextureID;
	}

}
