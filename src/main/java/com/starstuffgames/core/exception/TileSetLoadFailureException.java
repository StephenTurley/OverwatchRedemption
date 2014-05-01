/*******************************************************************************
 * Copyright 2014 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package com.starstuffgames.core.exception;

public class TileSetLoadFailureException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -619338841217687736L;
	
	public TileSetLoadFailureException(String msg)
	{
		super(msg);
	}

}
