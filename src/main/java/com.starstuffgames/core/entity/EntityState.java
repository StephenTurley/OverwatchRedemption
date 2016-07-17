/*******************************************************************************
 * Copyright 2014 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package com.starstuffgames.core.entity;

public interface EntityState {

	int getStateValue();
	EntityState getState(String string);
	EntityState getState(int ordinalValue);
}
