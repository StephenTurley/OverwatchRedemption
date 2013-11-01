package serverStates;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import core.network.Player;
import core.network.Network.Login;
import core.network.Network.SimpleMessage;
import core.network.PlayerConnection;
import core.stateManager.ServerState;


public class ServerStartState extends ServerState {
	
	private Player player1;
	private Player player2;

	public ServerStartState(Server server, Player player1, Player player2) {
		super(server);
		this.player1 = player1;
		this.player2 = player2;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
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
			
			if (player1 == null) player1 = player;
			else if (player2 == null) player2 = player;
			else return; //all players logged in
			
			//send connection message to clients
			server.sendToAllTCP(new SimpleMessage(player.name+" has connected"));
			
			if(player1 != null && player2 != null)
			{
				server.sendToAllTCP(new SimpleMessage("All players have connected, are you ready?"));
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
