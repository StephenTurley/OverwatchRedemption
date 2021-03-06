/*******************************************************************************
 * Copyright 2014 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package com.starstuffgames.core.network;

import java.util.UUID;

import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Vector2f;

public class EntityDataPacket {
	public String className;
	public UUID uuid;
	public Point location;
	public int layer, state;
	public float rotation;
	public Vector2f direction;
}
