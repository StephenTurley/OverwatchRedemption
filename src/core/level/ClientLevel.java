/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.level;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import core.Debug;
import core.Game;
import static org.lwjgl.opengl.GL11.*;

public class ClientLevel {
	
	private ArrayList<TileSet> tileSets;
	private TileMap tileMap;
	private int mapWidth ,mapHeight ,tileWidth ,tileHeight;
	private HashMap<Integer, Layer> layers;

	public ClientLevel(InputStream fileStream) {
		try
		{
			MapParser mp = new MapParser(fileStream);
			mapWidth = mp.getWidth();
			mapHeight = mp.getHeight();
			tileWidth = mp.getTileWidth();
			tileHeight = mp.getTileHeight();
			tileSets = mp.getTileSets();
			tileMap = new TileMap(tileSets);
			layers = mp.getLayers();
		}
		catch (Exception e)
		{
			Debug.Trace(e.getMessage());
			Game.exit(-1);
		}
	}
	
	public ArrayList<TileSet> getTileSets() {
		return tileSets;
	}

	public void setTileSets(ArrayList<TileSet> tileSets) {
		this.tileSets = tileSets;
	}

	public TileMap getTileMap() {
		return tileMap;
	}

	public void setTileMap(TileMap tileMap) {
		this.tileMap = tileMap;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}

	public void dispose()
	{
		for(TileSet ts : tileSets)
		{
			glDeleteTextures(ts.getGlTextureID());
		}
	}

	public HashMap<Integer, Layer> getLayers() {
		return layers;
	}

	public void setLayers(HashMap<Integer, Layer> layers) {
		this.layers = layers;
	}

}
