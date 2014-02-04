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

import core.Debug;
import core.Game;
import core.entity.ClientEntity;
import core.entity.EntityCollection;
import core.entity.EntityFactory;
import core.graphics.Camera;
import core.graphics.TextureCoord;
import core.graphics.TextureLoader;
import core.network.EntityDataPacket;
import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;

public class ClientLevel {
	
	private TileMap tileMap;
	private int mapWidth ,mapHeight ,tileWidth ,tileHeight;
	private ArrayList<Layer> layers;
	private EntityCollection<ClientEntity> entityCollection;

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
			entityCollection = new EntityCollection<>(mp.getClientEntities());
			
			for(ClientEntity e : entityCollection.getEntities())
			{
				e.loadAssets();
			}
			
		}
		catch (Exception e)
		{
			Debug.Trace(e.getMessage());
			Game.exit(1);
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
		TextureLoader.deleteTextures();
	}

	public ArrayList<Layer> getLayers() {
		return layers;
	}

	public void setLayers(ArrayList<Layer> layers) {
		this.layers = layers;
	}
	
	public ClientEntity getEntity(UUID uuid)
	{
		return entityCollection.getEntity(uuid);
	}
	
	public void addUpdateEntity(EntityDataPacket[] entities)
	{
		for(EntityDataPacket pkt : entities)
		{
			try
			{
				
				ClientEntity entity = entityCollection.getEntity(pkt.uuid);
				
				if(entity == null)
				{
					entity = EntityFactory.createClientEntity(pkt.className, pkt.uuid, pkt.location, pkt.layer);
					entity.loadAssets();

				}
				else
				{
					entity.setLayer(pkt.layer);
					entity.setLocation(pkt.location);
				}
				entity.setDirection(pkt.direction);
				entity.setState(entity.getCurrentState().getState(pkt.state));
				entityCollection.addEntity(entity.getID(), entity);
			}
			catch(Exception e)
			{
				Debug.Trace(e.getMessage());
				Game.exit(1);
			}
			
		}
	}
	public void update(int delta)
	{
		entityCollection.update(delta);
	}
	public void draw(Camera camera)
	{
		Point offSet = camera.getTileOffset(tileWidth, tileHeight);
		
		int columns = (camera.getWidth() / tileWidth);
		int rows = (camera.getHeight() / tileHeight);
		
		int startingGlobalTileX = camera.getX() / tileWidth;
		int startingGlobalTileY = camera.getY() / tileHeight;
		
		//buffer in the positive directions.
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
					
					TextureCoord t = tileMap.getTileByGID(currentGID);
				
					if(glGetInteger(GL_TEXTURE_BINDING_2D) != t.glTextureID)
					{
						glBindTexture(GL_TEXTURE_RECTANGLE_ARB, t.glTextureID);
					}

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
				}

			}
			for(ClientEntity e : entityCollection.getEntities())
			{
				camera.drawVisible(e, l.getValue());
			}
		}
		
	}

}
