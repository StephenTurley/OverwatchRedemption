package core.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import core.network.Network.SimpleMessage;
import core.Debug;
import core.Game;

public class GameServer {
	
		private static Server server;

		public static void init()
	 	{
	 		server = new Server();
	 		Network.register(server);
	 		server.addListener(new Listener(){
	 			public void connected(Connection connection)
	 			{
	 				SimpleMessage msgPacket = new SimpleMessage();
	 				msgPacket.msg = "Client Connected!";
	 				server.sendToAllTCP(msgPacket);
	 			}
	 		});
	 	}
	 	public static void start()
	 	{
	 		server.start();
	 		try
	 		{
	 			server.bind(Game.getGameConfig().getServerTCP(),Game.getGameConfig().getServerUDP());
	 		}catch(Exception e)
	 		{
				Debug.Trace(e.getMessage());
	 			Game.exit(-1);
	 		}
	 	}
	 	public static void kill()
	 	{
	 		server.close();
	 	}
	 	public static Server getServer()
	 	{
	 		return server;
	 	}

}
