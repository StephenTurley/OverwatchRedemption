package serverStates;

import java.util.HashMap;

import com.esotericsoftware.kryonet.Connection;

import core.Debug;
import core.network.GameServer;
import core.network.Network.PlayerReady;
import core.network.Network.PlayersPacket;
import core.network.Player;
import core.stateManager.ServerState;

public class ServerLobbyState extends ServerState {

	public ServerLobbyState(GameServer gameServer) {
		super(gameServer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(int delta) {

		for(Connection c : gameServer.getServer().getConnections())
		{
			PlayersPacket playersPacket = new PlayersPacket();

			HashMap<Integer, Player> players = gameServer.getPlayers();
			
			for(Player p : players.values())
			{
				if(p.getId() == c.getID())
				{
					playersPacket.setThisPlayer(p.clone());
				}
				else
				{
					playersPacket.setThatPlayer(p.clone());
				}
			}
			
			gameServer.getServer().sendToTCP(c.getID(),playersPacket);
		}
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
		if(object instanceof PlayerReady)
		{
			PlayerReady readyPacket = (PlayerReady)object;
			gameServer.getPlayer(c.getID()).setReady(readyPacket.isReady);
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
