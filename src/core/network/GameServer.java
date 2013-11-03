package core.network;

import java.util.HashMap;

import serverStates.ServerStartState;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import core.Debug;
import core.Game;
import core.stateManager.ServerState;

public class GameServer{
	
		private Server server;
		private HashMap<Integer, Player> players;
		private ServerState currentState;

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
	 	
	 	public void addPlayer(int id,Player player)
	 	{
	 		players.put(id, player);
	 	}
	 	public Player removePlayer(int id)
	 	{
	 		return players.remove(id);
	 	}
	 	public Player getPlayer(int id)
	 	{
	 		return players.get(id);
	 	}
	 	public int getPlayerCount()
	 	{
	 		return players.size();
	 	}

}
