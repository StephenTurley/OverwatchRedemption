package Core;

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
