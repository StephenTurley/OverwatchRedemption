/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.level;

import java.util.ArrayList;
import java.util.HashMap;

public class TileMap {
	private HashMap<Integer, TileCoord> tiles;
	
	public TileMap(ArrayList<TileSet> tileSets)
	{
		tiles = new HashMap<Integer, TileCoord>();
		
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
	
	public TileCoord getTileByGID(int gid)
	{
		return tiles.get(gid);
	}

}
