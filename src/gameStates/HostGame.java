package gameStates;

import com.esotericsoftware.kryonet.Client;

import core.Debug;
import core.Game;
import core.network.GameClient;
import core.network.GameServer;

import core.stateManager.GameState;
import core.stateManager.StateManager;

public class HostGame extends GameState {
	

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
		GameServer.init();
		GameServer.start();
		GameClient.init(5000,"localhost",Game.getGameConfig().getServerTCP(),Game.getGameConfig().getServerUDP());
		GameClient.Login("Player 1");
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}
