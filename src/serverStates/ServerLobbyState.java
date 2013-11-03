package serverStates;

import com.esotericsoftware.kryonet.Connection;

import core.Debug;
import core.network.GameServer;
import core.network.Player;
import core.network.PlayerConnection;
import core.network.Network.Login;
import core.network.Network.SimpleMessage;
import core.stateManager.ServerState;

public class ServerLobbyState extends ServerState {

	public ServerLobbyState(GameServer server) {
		super(server);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enter() {
		Debug.Trace("ServerLobbyState entered!");
	}

	@Override
	public void exit() {
		Debug.Trace("ServerLobbyState exited!");

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
			//ignore if already logged in
			if(player != null) return;
			
			String name =  ((Login)object).name;
			player = new Player(name);
			pc.setName(name);
			
			if (gameServer.getPlayer1() == null)
			{
				gameServer.setPlayer1(player);
			}
			else if (gameServer.getPlayer2() == null) gameServer.setPlayer2(player);
			else return; //all players logged in
			
			//send connection message to clients
			gameServer.getServer().sendToAllTCP(new SimpleMessage(player.name+" has connected"));
			
			if(gameServer.getPlayer1() != null && gameServer.getPlayer2() != null)
			{
				gameServer.getServer().sendToAllTCP(new SimpleMessage("All players have connected, are you ready?"));
				gameServer.changeState(new ServerLobbyState(gameServer));
			}
		}
	}
	@Override
	public void disconnected(Connection c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void idle(Connection c) {
		// TODO Auto-generated method stub

	}

}
