/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.level;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;

import core.Debug;
import core.Game;
import core.entity.Entity;
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
	
	public Entity getEntity(UUID uuid)
	{
		return entityCollection.getEntity(uuid);
	}
	
	public void addUpdateEntity(ArrayList<Entity> entities)
	{
		entityCollection.addUpdateEntities(entities, true);
	}
	
	public void draw(Camera camera)
	{
		
		
		
		Point offSet = camera.getTileOffset(tileWidth, tileHeight);
		
		int columns = (camera.getWidth() / tileWidth);
		int rows = (camera.getHeight() / tileHeight);
		
		int startingGlobalTileX = camera.getX() / tileWidth;
		int startingGlobalTileY = camera.getY() / tileHeight;
		
		//buffer in the positive directions. Negative directions will be
		//buffered because of integer division rounding.
		if(startingGlobalTileX + columns + 1 < mapWidth) columns++;
		if(startingGlobalTileY + rows + 2 < mapHeight) rows+=2;
		
		
		for(Layer l : layers)
		{
			for(int row = 0; row < rows; row ++)
			{
				for (int col = 0; col < columns; col++)
				{
					int globalTileX = startingGlobalTileX + col;
					int globalTileY = startingGlobalTileY + row;
					
					int currentGID = l.getGid(globalTileX, globalTileY);
					
					//no tile at this location
					if(currentGID == 0) continue;
					
					int pixelY = row * tileHeight;
					int pixelX = col * tileWidth;
					
					
					glColor3f(1.0f, 1.0f, 1.0f);
					//draw this
					TextureCoord t = tileMap.getTileByGID(currentGID);
					

					glBindTexture(GL_TEXTURE_RECTANGLE_ARB, t.glTextureID);

					
					glBegin(GL_QUADS);
			        glTexCoord2f(t.X, t.Y);
			        glVertex2f(pixelX - offSet.getX(), pixelY - offSet.getY());
			        glTexCoord2f(t.X, t.Y2);
			        glVertex2f(pixelX - offSet.getX(), pixelY + tileHeight - offSet.getY());
			        glTexCoord2f(t.X2, t.Y2);
			        glVertex2f(pixelX + tileWidth - offSet.getX(), pixelY + tileHeight - offSet.getY());
			        glTexCoord2f(t.X2, t.Y);
			        glVertex2f(pixelX + tileWidth - offSet.getX(), pixelY - offSet.getY());
			        glEnd();
			        

			        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);
			        

			        //draw entities at current tile and layer
			        //entityCollection.drawInArea(camera, new Rectangle(globalX * tileWidth,globalY * tileHeight,tileWidth,tileHeight), l.getValue());
					
				}
				//Rectangle currentRow = new Rectangle(camera.getX(), (startingGlobalTileY + row) * tileHeight ,camera.getWidth(), tileHeight);

				//entityCollection.drawInArea(camera, currentRow, l.getValue());
				
				
			}
			entityCollection.drawVisible(camera,l.getValue());
		}
		
	}

}
