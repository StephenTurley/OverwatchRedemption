/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package com.starstuffgames.core.level;

import java.io.InputStream;

import com.starstuffgames.core.exception.LevelNotFoundException;

public class LevelManager {
	
	public static ServerLevel loadServerLevel(int stage, int level) throws LevelNotFoundException
	{
		ServerLevel	sl = new ServerLevel(getFileStream(stage,level));
		return sl;
	}
	
	public static ClientLevel loadClientLevel(int stage, int level) throws LevelNotFoundException
	{
		ClientLevel cl = new ClientLevel(getFileStream(stage,level));
		return cl;
	}
	
	private static InputStream getFileStream(int stage, int level) throws LevelNotFoundException
	{
		String filePath = String.format("/levels/Level%d-%d.tmx",stage,level);
		InputStream levelStream = LevelManager.class.getResourceAsStream(filePath);
		
		if(levelStream == null)
		{
			throw new LevelNotFoundException();
		}
		
		return levelStream;
		
	}
	
}
