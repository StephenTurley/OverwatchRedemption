package gameStates;

import com.esotericsoftware.kryonet.Connection;

import core.Debug;
import core.Game;
import core.network.Network;
import core.network.Network.SimpleMessage;
import core.stateManager.GameState;
import core.stateManager.StateManager;

public class HostGame extends GameState{
	

	public HostGame(StateManager sm) {
		super(sm);
		// TODO Auto-generated constructor stub
	}

	public HostGame()
	{
		
	}
	@Override
	public void update(int delta) {
		
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		Game.addClientListener(this);

	}

	@Override
	public void pause() {
		Game.removeClientListener(this);

	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void enter() {
		Debug.Trace("Host Game State has been entered!");
		Game.startServer();
		Game.bindClient(5000,"localhost",Game.getGameConfig().getServerTCP(),Game.getGameConfig().getServerUDP());
		Game.addClientListener(this);
		//login
		Game.clientSendTCP(new Network.Login("Player 1"));
	}

	@Override
	public void exit() {
		Game.removeClientListener(this);
	}
	public void connected(Connection c)
	{
		
	}
	public void received (Connection c, Object object)
	{
		if(object instanceof SimpleMessage)
		{
			SimpleMessage msgPacket = (SimpleMessage)object;
			System.out.println(msgPacket.msg);
		}
	}

}
