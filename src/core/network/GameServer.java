package core.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import core.network.Network.Login;
import core.network.Network.SimpleMessage;
import core.Debug;
import core.Game;

public class GameServer extends Listener{
	
		private static Server server;
		private static Player[] players = new Player[2];

		public void init()
	 	{
	 		server = new Server(){
	 			protected Connection newConnection(){
	 				return new PlayerConnection();
	 			}
	 		};
	 		Network.register(server);
	 		server.addListener(this);
	 	}
		public void connected(Connection c)
		{
			
		}
		public void received (Connection c, Object object)
		{
			PlayerConnection pc = (PlayerConnection)c;
			Player player = pc.player;
			
			if(object instanceof Login)
			{
				//ignore if already logged in
				if(player != null) return;
				
				String name =  ((Login)object).name;
				player = new Player(name);
				
				if(players.length == 0)
					players[0] = player;
				else
					players[1] = player;
				
				//send connection message to clients
				server.sendToAllTCP(new SimpleMessage(player.name+" has connected"));
				
				if(players.length == 2)
				{
					server.sendToAllTCP(new SimpleMessage("All players have connected, are you ready?"));
				}
			}
		}
	 	public void start()
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
	 	public void update(int delta)
	 	{
	 		
	 	}
	 	static class PlayerConnection extends Connection
	 	{
	 		public Player player; 
	 	}
	 	public void kill()
	 	{
	 		server.close();
	 	}
	 	public Server getServer()
	 	{
	 		return server;
	 	}

}
