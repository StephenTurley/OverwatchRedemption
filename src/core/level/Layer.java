/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.level;

public class Layer {
	private int[] gids;
	
	public Layer(String gidCsv)
	{
		String[] layerData = gidCsv.split(",");
		
		gids = new int[layerData.length];
		
		for(int i = 0; i < layerData.length; i++)
		{
			gids[i] = Integer.parseInt(layerData[i]);
		}
	}
	public int getGid(int index)
	{
		return gids[index];
	}
	
}
