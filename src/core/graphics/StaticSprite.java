/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.graphics;

public class StaticSprite implements Sprite {
	
	private String name;
	
	public StaticSprite(String name,TextureCoord textureCoord)
	{
		this.name = name;
	}
	public StaticSprite(String name,TextureCoord textureCoord, int offSetX, int offSetY)
	{
		this.name = name;
	}

	public void draw(Camera camera) {
		
	}

	public void update(int delta) {
		// TODO Auto-generated method stub

	}
	public String getName()
	{
		return name;
	}


}
