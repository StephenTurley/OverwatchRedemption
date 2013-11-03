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
	 	
	 	public Player getPlayer1() {
			return player1;
		}
		public void setPlayer1(Player player1) {
			this.player1 = player1;
		}
		public Player getPlayer2() {
			return player2;
		}
		public void setPlayer2(Player player2) {
			this.player2 = player2;
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

}
