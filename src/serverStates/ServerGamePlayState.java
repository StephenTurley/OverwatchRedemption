package serverStates;


import com.esotericsoftware.kryonet.Connection;

import core.Debug;
import core.network.GameServer;
import core.network.Network;
import core.network.Player;
import core.network.PlayerConnection;
import core.stateManager.ServerState;

public class ServerGamePlayState extends ServerState {

	public ServerGamePlayState(GameServer gameServer) {
		super(gameServer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(int delta) {
		gameServer.sendPlayersPacket();
	}

	@Override
	public void enter() {
		Debug.Trace("ServerGamePlayState entered!");
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
		if(gameServer.isPlayerAuthenticated(pc))
		{
			if(object instanceof Network.MovePlayer)
			{
				Player player = gameServer.getPlayer(pc.getID());
				
				player.setMovementVector(((Network.MovePlayer)object).movementVector);
				
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
