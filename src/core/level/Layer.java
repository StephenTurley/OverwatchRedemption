/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.level;

import core.exception.LevelFormatException;

public class Layer {
	private int[][] layerGids;
	private int value;
	
	public Layer(String gidCsv, int width, int height, int value) throws LevelFormatException
	{
		layerGids = parseGidData(gidCsv, width, height);
		this.value = value;
	}
	public int getGid(int indexX, int indexY)
	{
		return layerGids[indexX][indexY];
	}
	
	private int[][] parseGidData(String gidCsv, int width, int height) throws LevelFormatException
	{
		int[][] gids = new int[width][height];
		String[] layerData = gidCsv.split(",");
		int i = 0;
		
		if((width * height) != layerData.length)
		{
			throw new LevelFormatException("Level size does not equal layer data size");
		}
		try
		{
			for(int row = 0; row < height; row++)
			{
				for(int col = 0; col < width; col++)
				{
					gids[col][row] = Integer.parseInt(layerData[i]);
					i++;
				}
			}
		}
		catch(NumberFormatException e)
		{
			throw new LevelFormatException("Invalid character in layer data. Must be integer values");
		}
		return gids;
	}
	
	public int getValue()
	{
		return value;
	}
	
}
