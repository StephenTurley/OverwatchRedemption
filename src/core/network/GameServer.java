package core.network;

import java.util.HashMap;

import serverStates.ServerStartState;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import core.Debug;
import core.Game;
import core.network.Network.PlayersPacket;
import core.stateManager.ServerState;

public class GameServer{
	
		private Server server;
		private HashMap<Integer, Player> players;
		private ServerState currentState;
		private boolean playersReady;

		
		public void init()
	 	{
			players = new HashMap<Integer, Player>();
	 		server = new Server(){
	 			protected Connection newConnection(){
	 				return new PlayerConnection();
	 			}
	 		};
	 		Network.register(server);
	 		currentState = new ServerStartState(this);
	 		currentState.enter();
	 		server.addListener(currentState);
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
	 		currentState.update(delta);
	 		
	 		if(players.size() >=2 )
	 		{
		 		boolean tempReady = true;
		 		for(Player p : players.values())
		 		{
		 			p.update(delta);
		 			if(!p.isReady())
		 			{
		 				tempReady = false;
		 				break;
		 			}
		 		}
		 		playersReady = tempReady;
	 		}
	 	}
	 	
		public void changeState(ServerState state)
	 	{
			//ServerStartState initializes listeners that are common for all states. They shouldn't be removed. 
			if(!(currentState instanceof ServerStartState)) server.removeListener(currentState);
	 		currentState.exit();
	 		currentState = state;
	 		currentState.enter();
	 		server.addListener(state);
	 	}
	 	
	 	public void kill()
	 	{
	 		server.close();
	 	}
	 	public Server getServer()
	 	{
	 		return server;
	 	}
	 	
	 	public void addPlayer(Player player)
	 	{
	 		players.put(player.getId(), player);
	 	}
	 	public Player removePlayer(int id)
	 	{
	 		return players.remove(id);
	 	}
	 	public Player getPlayer(int id)
	 	{
	 		return players.get(id);
	 	}
	 	public HashMap<Integer, Player> getPlayers()
	 	{
	 		return players;
	 	}
	 	public int getPlayerCount()
	 	{
	 		return players.size();
	 	}
	 	public boolean isPlayersReady() {
			return playersReady;
		}
		public void setPlayersReady(boolean playersReady) {
			
			for(Player p : players.values())
			{
				p.setReady(playersReady);
			}
			this.playersReady = playersReady;
		}
		public void setPlayerReady(int playerID, boolean playerReady)
		{
			Player player =  players.get(playerID);
			player.setReady(playerReady);
			String msg;
			if(playerReady)
			{
				msg = " is ready!";
			}
			else 
			{
				msg = " is not ready!";
			}
			server.sendToAllTCP(new Network.ServerMessage(player.getName() + msg) );
		}
		
		public boolean isPlayerAuthenticated(PlayerConnection playerConnection)
		{
			return !(players.get(playerConnection.getID()) == null);
		}
		public void sendToAuthenticatedTCP(Object object)
		{
			for(int id : players.keySet())
			{
				server.sendToTCP(id, object);
			}
		}
		public void sendToAuthenticatedUDP(Object object)
		{
			for(int id : players.keySet())
			{
				server.sendToUDP(id, object);
			}
		}
		public void sendPlayersPacket()
		{
			for(Connection c : server.getConnections())
			{
				PlayerConnection pc = (PlayerConnection)c;
				if(isPlayerAuthenticated(pc))
				{
					PlayersPacket playersPacket = new PlayersPacket();
					
					for(Player p : players.values())
					{
						if(p.getId() == pc.getID())
						{
							playersPacket.setThisPlayer(p.clone());
						}
						else
						{
							playersPacket.setThatPlayer(p.clone());
						}
					}
					server.sendToUDP(pc.getID(),playersPacket);
				}
				
			}
		}

}
