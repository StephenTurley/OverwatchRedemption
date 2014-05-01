/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/

package com.starstuffgames.core.level;


import java.util.ArrayList;
import java.util.HashMap;

import com.starstuffgames.core.graphics.TextureCoord;

/**
 *A TileMap maps Gids to TextureCoords 
 *
 */
public class TileMap {
	private HashMap<Integer, TextureCoord> tiles;
	
	public TileMap(ArrayList<TileSet> tileSets)
	{
		tiles = new HashMap<>();
		
		for(TileSet ts : tileSets)
		{
			int firstGID = ts.getFirstGID();
			int lastGID = ts.getLastGID();
			
			for(int i = firstGID; i <= lastGID; i++)
			{
				tiles.put(i, ts.getTileCoord(i));
			}
		}
	}
	
	public TextureCoord getTileByGID(int gid)
	{
		return tiles.get(gid);
	}
	public void dispose()
	{
		
	}


}
