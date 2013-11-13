package serverStates;

import com.esotericsoftware.kryonet.Connection;

import core.network.GameServer;
import core.stateManager.ServerState;

public class ServerLoadLevelState extends ServerState {

	public ServerLoadLevelState(GameServer gameServer) {
		super(gameServer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enter() {
		gameServer.changeState(new ServerGamePlayState(gameServer));
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
		// TODO Auto-generated method stub

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
