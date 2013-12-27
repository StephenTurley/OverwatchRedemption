/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.level;

import java.io.InputStream;
import java.util.ArrayList;

import core.Debug;
import core.Entity;
import core.Game;
import core.graphics.Camera;
import static org.lwjgl.opengl.GL11.*;

public class ClientLevel {
	
	private ArrayList<TileSet> tileSets;
	private TileMap tileMap;
	private int mapWidth ,mapHeight ,tileWidth ,tileHeight;
	private ArrayList<Layer> layers;

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
	public int getPixelWidth()
	{
		return tileWidth * mapWidth;
	}
	public int getPixelHeight()
	{
		return tileHeight * mapHeight;
	}

	public void dispose()
	{
		for(TileSet ts : tileSets)
		{
			glDeleteTextures(ts.getGlTextureID());
		}
	}

	public ArrayList<Layer> getLayers() {
		return layers;
	}

	public void setLayers(ArrayList<Layer> layers) {
		this.layers = layers;
	}
	
	public int[][] getGidsInCamera(int layer, Camera camera)
	{
		int[][] gids;
		Layer currentLayer = layers.get(layer);
		
		int columns = (camera.getWidth() / tileWidth);
		int rows = (camera.getHeight() / tileHeight);
		
		int startingGlobalX = camera.getX() / tileWidth;
		int startingGlobalY = camera.getY() / tileHeight;
		
		//This will add the next partial tile to be drawn.. unless id doesn't exist
		//It would be nice if the camera would stop moving before this happened.
		if(startingGlobalX + columns + 1 < mapWidth) columns++;
		if(startingGlobalY + rows + 2 < mapHeight) rows+=2;
		
		gids = new int[columns][rows];
		
		for(int row = 0; row < rows; row ++)
		{
			for (int col = 0; col < columns; col++)
			{
				int globalX = startingGlobalX + col;
				int globalY = startingGlobalY + row;
			
				gids[col][row] = currentLayer.getGid(globalX, globalY);
			}
		}
		
		return gids;
	}
	public void draw(Camera camera, ArrayList<Entity> entities)
	{
		
	}

}
