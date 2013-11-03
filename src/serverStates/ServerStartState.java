package serverStates;

import com.esotericsoftware.kryonet.Connection;
import core.Debug;
import core.network.GameServer;
import core.network.Player;
import core.network.PlayerConnection;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void disconnected(Connection c) {
		PlayerConnection pc = (PlayerConnection)c;
		
		Debug.Trace(pc.toString() +" has disconnectd");
	}

	@Override
	public void idle(Connection c) {
		// TODO Auto-generated method stub
		
	}

}
