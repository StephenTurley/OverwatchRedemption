package core;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Debug {

	public static void Trace(String msg)
	{
		if(Game.getGameConfig().isDebugLogging())
		{
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			System.out.println("("+timeStamp+") Debug: "+msg);
		}
	}
}
