/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.level;

import java.io.InputStream;
import java.util.UUID;

import core.Debug;
import core.Game;
import core.entity.Entity;
import core.entity.EntityCollection;
import core.exception.LevelComponentsNotSatisfiedException;

public class ServerLevel {
	
	private int mapWidth ,mapHeight ,tileWidth ,tileHeight;
	private EntityCollection entityCollection;
	


	public ServerLevel(InputStream fileStream) {
		try
		{
			MapParser mp = new MapParser(fileStream);
			mapWidth = mp.getWidth();
			mapHeight = mp.getHeight();
			tileWidth = mp.getTileWidth();
			tileHeight = mp.getTileHeight();
			entityCollection = new EntityCollection(mp.getEntities());
		}
		catch(LevelComponentsNotSatisfiedException e)
		{
			Debug.Trace(e.getMessage());
			Game.exit(-1);
		}
		catch(Exception e)
		{
			Debug.Trace(e.getMessage());
			Game.exit(-1);
		}
	
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

	public EntityCollection getEntityCollection()
	{
		return entityCollection;
	}

	public Entity getEntity(UUID uuid) {
		return entityCollection.getEntity(uuid);
	}

	public void update(int delta) {
		entityCollection.serverUpdate(delta);
	}
}
