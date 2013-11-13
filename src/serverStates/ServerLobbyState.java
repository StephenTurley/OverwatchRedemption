package serverStates;

import java.util.HashMap;

import com.esotericsoftware.kryonet.Connection;

import core.Debug;
import core.network.GameServer;
import core.network.Network;
import core.network.Network.PlayerReady;
import core.network.Network.PlayersPacket;
import core.network.Player;
import core.stateManager.ServerState;

public class ServerLobbyState extends ServerState {
	
	private int countdownRemaining;
	private final int TIME_TO_START = 5000; //seconds

	public ServerLobbyState(GameServer gameServer) {
		super(gameServer);
		countdownRemaining = TIME_TO_START;
	}

	@Override
	public void update(int delta) {
		
		if(gameServer.getPlayerCount() == 2 && gameServer.isPlayersReady())
		{
			System.out.println(gameServer.getPlayerCount() + "   "+ gameServer.isPlayersReady()+"  "+delta);
			countdownRemaining -= delta;
			int seconds = countdownRemaining / 1000;
			gameServer.getServer().sendToAllTCP(new Network.ServerMessage("The game will start in "+seconds+" seconds."));
		}else
		{
			countdownRemaining = TIME_TO_START;
		}
		if( countdownRemaining <= 0)
		{
			gameServer.getServer().sendToAllTCP(new Network.StartGame());
		}

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
			gameServer.setPlayerReady(c.getID(), readyPacket.isReady);
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
