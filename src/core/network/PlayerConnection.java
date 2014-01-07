/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.network;

import java.util.UUID;

import com.esotericsoftware.kryonet.Connection;

public class PlayerConnection extends Connection {
	
	
	public String name;
	public boolean isReady;
	public UUID uuid;
	
	public PlayerConnection()
	{
		super();
	}
	
	public PlayerConnectionDataPacket getPlayerData()
	{
		PlayerConnectionDataPacket pcd = new PlayerConnectionDataPacket();
		pcd.name = this.name;
		pcd.connectionID = this.getID();
		pcd.isReady = this.isReady;
		pcd.uuid = this.uuid;
		
		return pcd;
	}
}
