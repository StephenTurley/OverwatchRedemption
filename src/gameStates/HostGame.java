package gameStates;

import com.esotericsoftware.kryonet.Client;

import core.Debug;
import core.Game;
import core.stateManager.GameState;
import core.stateManager.StateManager;

public class HostGame extends GameState {
	
	private Client client;

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
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void enter() {
		Game.initializeServer();
		Game.startServer();
		client = new Client();
		client.start();
		try{
			client.connect(5000,"localhost",Game.getGameConfig().getServerTCP(),Game.getGameConfig().getServerUDP());
		}catch(Exception e)
		{
			if(Game.getGameConfig().isDebugLogging())
			{
				Debug.Trace(e.getMessage());
			}
			Game.exit(-1);
		}
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}
