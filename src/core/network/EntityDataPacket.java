/*******************************************************************************
 * Copyright 2014 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.network;

import java.util.UUID;

import org.lwjgl.util.Point;

public class EntityDataPacket {
	public String className;
	public UUID uuid;
	public Point position;
	public int layer;
	public float rotation;
}
