/*******************************************************************************
 * Copyright 2014 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.exception;

public class EntityNotConstructedException extends Exception 
{

	private static final long serialVersionUID = 8405761791265761154L;
	
	public EntityNotConstructedException(String msg)
	{
		super(msg);
	}

}
