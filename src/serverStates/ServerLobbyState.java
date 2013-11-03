package serverStates;

import com.esotericsoftware.kryonet.Connection;

import core.Debug;
import core.network.GameServer;
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
