package core;

public class Debug {

	public static void Trace(String msg)
	{
		if(Game.getGameConfig().isDebugLogging())
		{	
			System.out.println(msg);
		}
	}
}
