/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package com.starstuffgames.core.configurationManager;

/**
 * @author Stephen Turley
 *
 */

public class GameConfig {
	
	/**
	 * Display mode width
	 */
	private int displayWidth;
	
	/**
	 * Display mode height
	 */
	private int displayHeight;
	
	/**
	 * Name of the Display
	 */
	private String displayName;
	
	/**
	 * Display full screen mode?
	 */
	private boolean fullScreen;
	
	/**
	 * Generate Debug Log?
	 */
	private boolean debugLogging;
	
	private float joyStickDeadZone;
	
	private int serverUDP;
	private int serverTCP;
	private int frameRate;
	
	
	public int getFrameRate() {
		if(frameRate == 0) return 60;
		return frameRate;
	}

	public void setFrameRate(int frameRate) {
		this.frameRate = frameRate;
	}

	public int getServerUDP() {
		return serverUDP;
	}

	public void setServerUDP(int serverUDP) {
		this.serverUDP = serverUDP;
	}

	public int getServerTCP() {
		return serverTCP;
	}

	public void setServerTCP(int serverTCP) {
		this.serverTCP = serverTCP;
	}

	public float getJoyStickDeadZone() {
		return joyStickDeadZone;
	}

	public void setJoyStickDeadZone(float joyStickDeadZone) {
		this.joyStickDeadZone = joyStickDeadZone;
	}

	public GameConfig()
	{
	}

	public int getDisplayWidth() {
		return displayWidth;
	}

	public void setDisplayWidth(int width) {
		this.displayWidth = width;
	}

	public int getDisplayHeight() {
		return displayHeight;
	}

	public void setDisplayHeight(int height) {
		this.displayHeight = height;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isFullScreen() {
		return fullScreen;
	}

	public void setFullScreen(boolean fullScreen) {
		this.fullScreen = fullScreen;
	}

	public boolean isDebugLogging() {
		return debugLogging;
	}

	public void setDebugLogging(boolean debugLogging) {
		this.debugLogging = debugLogging;
	}
	
	
	
	
}
