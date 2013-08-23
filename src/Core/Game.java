package Core;
import Core.StateManager.*;

public class Game {
	
	final boolean DEBUG_MODE;
	private static StateManager sm;
	
	public Game()
	{
		DEBUG_MODE = true;
		Trace("Game is running!");
		sm = new StateManager();
	}
	
	
	public StateManager getStateManager()
	{
		return sm;
	}
	
	private void gameLoop()
	{
		while(true)
		{
			sm.draw();
			sm.update();
		}
	}
	
	public void Trace(String msg)
	{
		if(DEBUG_MODE)
		{
			System.out.println(msg);
		}
	}

}
