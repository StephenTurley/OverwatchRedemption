package serverStates;

import com.esotericsoftware.kryonet.Connection;
import core.Debug;
import core.network.GameServer;
import core.network.Player;
import core.network.PlayerConnection;
import core.network.Network.Login;
import core.network.Network.ServerMessage;
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
			player.setId(pc.getID());
			pc.player = player;
			
			if (gameServer.getPlayer(player.getId()) == null)
			{
				gameServer.addPlayer(player);
			}
			else if (gameServer.getPlayer(player.getId()) == null)
			{
				gameServer.addPlayer(player);
			}
			else return; //all players logged in
			
			//send connection message to clients
			gameServer.getServer().sendToAllTCP(new ServerMessage(player.getName()+" has connected"));
			
			if(gameServer.getPlayerCount() == 2)
			{
				gameServer.getServer().sendToAllTCP(new ServerMessage("All players have connected, are you ready?"));
			}
		}

	}
	@Override
	public void disconnected(Connection c) {
		PlayerConnection pc = (PlayerConnection)c;
		
		Player player = gameServer.getPlayer(pc.getID());
		gameServer.removePlayer(player.getId());
		
		Debug.Trace(player.getName() +" has disconnectd");
	}

	@Override
	public void idle(Connection c) {
		// TODO Auto-generated method stub
		
	}

}
