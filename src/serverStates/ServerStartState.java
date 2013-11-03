package serverStates;

import com.esotericsoftware.kryonet.Connection;
import core.Debug;
import core.network.GameServer;
import core.network.Player;
import core.network.PlayerConnection;
import core.network.Network.Login;
import core.network.Network.SimpleMessage;
import core.stateManager.ServerState;


public class ServerStartState extends ServerState {
	


	public ServerStartState(GameServer gameServer) {
		super(gameServer);
	}

	@Override
	public void update(int delta) {
		gameServer.changeState(new ServerLobbyState(gameServer));
	}

	@Override
	public void enter() {
		Debug.Trace("ServerStartState entered!");	
	}

	@Override
	public void exit() {
		Debug.Trace("ServerStartState exited!");
		
	}

	@Override
	public void connected(Connection c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void received(Connection c, Object object) {
		PlayerConnection pc = (PlayerConnection)c;
		Player player = pc.player;
		
		if(object instanceof Login)
		{
			//ignore if already logged in or two players are logged in. 
			if(player != null || gameServer.getPlayerCount() == 2) return;
			
			String name =  ((Login)object).name;
			player = new Player(name);
			player.id = pc.getID();
			pc.player = player;
			
			if (gameServer.getPlayer(player.id) == null)
			{
				gameServer.addPlayer(player.id, player);
			}
			else if (gameServer.getPlayer(player.id) == null)
			{
				gameServer.addPlayer(player.id,player);
			}
			else return; //all players logged in
			
			//send connection message to clients
			gameServer.getServer().sendToAllTCP(new SimpleMessage(player.name+" has connected"));
			
			if(gameServer.getPlayerCount() == 2)
			{
				gameServer.getServer().sendToAllTCP(new SimpleMessage("All players have connected, are you ready?"));
			}
		}

	}
	@Override
	public void disconnected(Connection c) {
		PlayerConnection pc = (PlayerConnection)c;
		
		Player player = gameServer.getPlayer(pc.getID());
		gameServer.removePlayer(player.id);
		
		Debug.Trace(player.name +" has disconnectd");
	}

	@Override
	public void idle(Connection c) {
		// TODO Auto-generated method stub
		
	}

}
