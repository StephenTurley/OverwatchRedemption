/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package com.starstuffgames.core.level;

import java.io.InputStream;
import java.util.UUID;

import com.starstuffgames.core.Debug;
import com.starstuffgames.core.Game;
import com.starstuffgames.core.entity.EntityCollection;
import com.starstuffgames.core.entity.ServerEntity;
import com.starstuffgames.core.exception.LevelComponentsNotSatisfiedException;
import java.util.ArrayList;

public class ServerLevel {
	
	private int mapWidth ,mapHeight ,tileWidth ,tileHeight;
	private EntityCollection<ServerEntity> entityCollection;
	


	public ServerLevel(InputStream fileStream) {
		try
		{
			MapParser mp = new MapParser(fileStream);
			mapWidth = mp.getWidth();
			mapHeight = mp.getHeight();
			tileWidth = mp.getTileWidth();
			tileHeight = mp.getTileHeight();
			entityCollection = new EntityCollection<>(mp.getServerEntities());
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

	public EntityCollection<ServerEntity> getEntityCollection()
	{
		return entityCollection;
	}

	public ServerEntity getEntity(UUID uuid) {
		return entityCollection.getEntity(uuid);
	}

	public void update(int delta) {
		
		//update the entities then check collsion
		entityCollection.update(delta);
		
		ArrayList<ServerEntity> entities = entityCollection.getEntities();
		
		for (int i = 0; i < entities.size(); i++)
		{
			for (int j =  i + 1; j < entities.size(); j++)
			{
				ServerEntity a = entities.get(i);
				ServerEntity b = entities.get(j);
				if(a.checkCollision(b))
				{
					a.collideWith(b);
					b.collideWith(a);
					//update entities
					entityCollection.putEntity(a.getID(), a);
					entityCollection.putEntity(b.getID(), b);
				}
			}
		}
	}
}
