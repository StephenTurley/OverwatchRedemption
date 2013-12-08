/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.level;

import java.io.InputStream;
import java.util.ArrayList;
import static org.lwjgl.opengl.GL11.*;

public class ClientLevel {
	
	private MapParser mp;
	private ArrayList<TileSet> tileSets;
	private TileMap tileMap;

	public ClientLevel(InputStream fileStream) {
		mp = new MapParser(fileStream);
		tileSets = mp.getTileSets();
		tileMap = new TileMap(tileSets);
	}
	
	public void dispose()
	{
		for(TileSet ts : tileSets)
		{
			glDeleteTextures(ts.getGlTextureID());
		}
	}

}
