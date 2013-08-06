package Core;
import Core.StateManager.*;

public class Game {
	
	final boolean DEBUG_MODE = true;
	private static StateManager sm;
	
	public Game()
	{
		Trace("Game is running!");
		sm = new StateManager();
	}
	
	public StateManager getStateManager()
	{
		return sm;
	}
	
	public void Trace(String msg)
	{
		if(DEBUG_MODE)
		{
			System.out.println(msg);
		}
	}

}
