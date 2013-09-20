package core.network;

import com.esotericsoftware.kryonet.*;

import core.Debug;
import core.Game;

public class GameServer {
	
	private static Server server;
	private static Thread serverThread;
	
	public GameServer()
	{
		server = new Server();
		serverThread = new Thread(server);
	}
	
	public static void start()
	{

		if(server != null)
		{
			serverThread.start();
		}
		else
		{
			if(Game.getGameConfig().isDebugLogging())
			{
				Debug.Trace("Server not created!");
			}
		}
	}
	
	public static void bind(int tcp, int udp)
	{
		if(server != null)
		{
			try
			{
				server.bind(tcp, udp);
			}catch(Exception e)
			{
				if(Game.getGameConfig().isDebugLogging())
				{
					Debug.Trace(e.getMessage());
				}
			}
		}
	}
	
}
