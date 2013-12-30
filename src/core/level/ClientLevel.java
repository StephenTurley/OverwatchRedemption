/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.level;

import java.io.InputStream;
import java.util.ArrayList;

import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;

import core.Debug;
import core.Game;
import core.entity.EntityCollection;
import core.graphics.Camera;
import core.graphics.TextureCoord;
import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;

public class ClientLevel {
	
	private TileMap tileMap;
	private int mapWidth ,mapHeight ,tileWidth ,tileHeight;
	private ArrayList<Layer> layers;
	private EntityCollection entityCollection;

	public ClientLevel(InputStream fileStream) {
		try
		{
			MapParser mp = new MapParser(fileStream);
			mapWidth = mp.getWidth();
			mapHeight = mp.getHeight();
			tileWidth = mp.getTileWidth();
			tileHeight = mp.getTileHeight();
			layers = mp.getLayers();
			tileMap = new TileMap(mp.getTileSets());
			entityCollection = new EntityCollection(mp.getEntities());
			entityCollection.loadAssets();
		}
		catch (Exception e)
		{
			Debug.Trace(e.getMessage());
			Game.exit(-1);
		}
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
		tileMap.dispose();
	}

	public ArrayList<Layer> getLayers() {
		return layers;
	}

	public void setLayers(ArrayList<Layer> layers) {
		this.layers = layers;
	}
	
	public void draw(Camera camera)
	{
		glColor3f(1.0f, 1.0f, 1.0f);
		
		
		Point offSet = camera.getTileOffset(tileWidth, tileHeight);
		
		int columns = (camera.getWidth() / tileWidth);
		int rows = (camera.getHeight() / tileHeight);
		
		int startingGlobalX = camera.getX() / tileWidth;
		int startingGlobalY = camera.getY() / tileHeight;
		
		//buffer in the positive directions. Negative directions will be
		//buffered because of integer division rounding.
		if(startingGlobalX + columns + 1 < mapWidth) columns++;
		if(startingGlobalY + rows + 2 < mapHeight) rows+=2;
		
		
		//draw each row layer by layer
		//TODO: interpolate entities
		for(int row = 0; row < rows; row ++)
		{
			for (int col = 0; col < columns; col++)
			{
				int globalX = startingGlobalX + col;
				int globalY = startingGlobalY + row;
				
				for(Layer l : layers)
				{
					int currentGID = l.getGid(globalX, globalY);
					
					//no tile at this location
					if(currentGID == 0) break;
					
					int y = row * tileHeight;
					int x = col * tileWidth;
					//draw this
					TextureCoord t = tileMap.getTileByGID(currentGID);
					

					glBindTexture(GL_TEXTURE_RECTANGLE_ARB, t.glTextureID);

					
					glBegin(GL_QUADS);
			        glTexCoord2f(t.X, t.Y);
			        glVertex2f(x - offSet.getX(), y - offSet.getY());
			        glTexCoord2f(t.X, t.Y2);
			        glVertex2f(x - offSet.getX(), y + tileHeight - offSet.getY());
			        glTexCoord2f(t.X2, t.Y2);
			        glVertex2f(x + tileWidth - offSet.getX(), y + tileHeight - offSet.getY());
			        glTexCoord2f(t.X2, t.Y);
			        glVertex2f(x + tileWidth - offSet.getX(), y - offSet.getY());
			        glEnd();
			        
			        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);
			        
			        entityCollection.drawInArea(camera, new Rectangle(x,y,tileWidth,tileHeight), l.getValue());
					
				}
			}
		}
		
	}

}
