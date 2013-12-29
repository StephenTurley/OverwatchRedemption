/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.network;

import com.esotericsoftware.kryonet.Connection;

import entities.Player;

public class PlayerConnection extends Connection {
	
	public Player player;
}
