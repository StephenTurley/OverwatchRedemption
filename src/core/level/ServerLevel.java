/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.level;

import java.io.InputStream;

public class ServerLevel {
	
	private int mapWidth;
	private int mapHeight;
	private int tileWidth;
	private int tileHeight;
	
	


	public ServerLevel(InputStream fileStream) {
		MapParser mp = new MapParser(fileStream);
		mapWidth = mp.getWidth();
		mapHeight = mp.getHeight();
		tileWidth = mp.getTileWidth();
		tileHeight = mp.getTileHeight();
	
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




}
