/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package com.starstuffgames.core.level;

import com.starstuffgames.core.graphics.TextureCoord;
import com.starstuffgames.core.graphics.TextureLoader;

/**
 * 
 * A TileSet defines a TileAtlas stored on an OpenGL device
 *
 */
public class TileSet {
	
	private int tileWidth, tileHeight, width, height, firstGID, glTextureID, lastGID;
	
	public TileSet(String filePath, int tileWidth, int tileHeight, int width, int height,int firstGID)
	{
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.width = width;
		this.height = height;
		this.firstGID = firstGID;
			
		glTextureID = TextureLoader.loadTexture(filePath);
		
		computeLastGID();
		
	}
	
	public int getLastGID() {
		return lastGID;
	}

	public void setLastGID(int lastGID) {
		this.lastGID = lastGID;
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getFirstGID() {
		return firstGID;
	}

	public void setFirstGID(int firstGID) {
		this.firstGID = firstGID;
	}

	public int getGlTextureID() {
		return glTextureID;
	}

	public void setGlTextureID(int glTextureID) {
		this.glTextureID = glTextureID;
	}
	
	public TextureCoord getTileCoord(int GID)
	{
		int localID = GID - firstGID;
		
		int row = (localID * tileWidth) / width;
		
		int x = (localID * tileWidth) - (row * width);
		int y = row * tileHeight;
		int x2 = x + tileWidth;
		int y2 = y + tileHeight;
		
		return new TextureCoord(glTextureID, x, y, x2, y2);	
	}
	
	private void computeLastGID()
	{
		int col = width/tileWidth;
		int row = height/tileHeight;
		
		int numTiles = col * row;
		
		this.lastGID = firstGID + numTiles;
	}

}
