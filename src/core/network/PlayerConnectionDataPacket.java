/*******************************************************************************
 * Copyright 2014 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.network;

import java.util.UUID;

public class PlayerConnectionDataPacket
{
	public String name;
	public int connectionID;
	public UUID uuid;
	public boolean isReady;
}
