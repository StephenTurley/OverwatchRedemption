package core.network;

import serverStates.ServerStartState;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import core.Debug;
import core.Game;
import core.stateManager.ServerState;

public class GameServer{
	
		private Server server;
		private Player player1;
		private Player player2;
		private ServerState currentState;

		public void init()
	 	{
	 		server = new Server(){
	 			protected Connection newConnection(){
	 				return new PlayerConnection();
	 			}
	 		};
	 		Network.register(server);
	 		currentState = new ServerStartState(this, player1, player2);
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
	 		server.removeListener(currentState);
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

}
