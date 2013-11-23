package gameStates;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import com.esotericsoftware.kryonet.Connection;

import static org.lwjgl.opengl.GL11.*;

import core.Debug;
import core.Game;
import core.stateManager.GameState;
import core.stateManager.StateManager;
import net.java.games.input.*;
import core.network.*;
import core.network.Network.MovePlayer;
import core.network.Network.PlayersPacket;


public class MovementTest extends GameState {


	private float deadZone;
	private Vector2f movementVector;
	private Controller gamepad;
	
	private Player thisPlayer; 
	private Player thatPlayer;
	
	public MovementTest(StateManager sm) {
		super(sm);
	}

	
	
	public void update(int delta) {
		
		handleInput(delta);
		MovePlayer movePkt = new MovePlayer();
		movePkt.movementVector = movementVector;
		Game.clientSendUDP(movePkt);
	}


	public void draw() {
		if(thisPlayer != null)
		{
			thisPlayer.draw();
		}
		if(thatPlayer != null)
		{
			thatPlayer.draw();
		}
	}


	public void resume() {
		Game.addClientListener(this);
	}


	public void pause() {
		Game.removeClientListener(this);

	}


	public void save() {
		// TODO Auto-generated method stub

	}
	
	public void enter()
	{
		deadZone = Game.getGameConfig().getJoyStickDeadZone();
		
		movementVector = new Vector2f(0,0);
		
		gamepad = Game.getGamePad();
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0,Game.getGameConfig().getDisplayWidth(), Game.getGameConfig().getDisplayHeight(), 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		Game.addClientListener(this);
		
		Game.clientSendTCP(new Network.PlayerReady(true));
		
		Debug.Trace("Movement Test Started!");
		
	}
	
	public void exit() {
		// TODO Auto-generated method stub

	}
	

	private void handleInput(int delta){
		/*if(gamepad != null)
		{
			gamepad.poll();
			float x = 0;
			float y = 0;
			for(Component c : gamepad.getComponents())
			{
				if(c.getName().equals("x"))
				{	
					x = c.getPollData();
				}
				if(c.getName().equals("y"))
				{
					y = c.getPollData();
				}
				if(c.getName().equals("Select")||c.getName().equals("Unknown"))
				{
					if(c.getPollData() == 1.0)
					{
						super.sm.pop();
					}
				}
			}
			Vector2f leftStickInput = new Vector2f(x,y);
			if(leftStickInput.lengthSquared() > deadZone)
			{
				tempVector.x = leftStickInput.x;
				tempVector.y = leftStickInput.y;
			}
			else
			{
				tempVector.x = 0;
				tempVector.y = 0;
			}
		}*/
		
		while(Keyboard.next())
		{
			if(Keyboard.getEventKeyState() || Keyboard.isRepeatEvent())
			{
				if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE)
				{
					super.sm.pop();
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_A)
				{
					movementVector.x = -1.0f;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_D)
				{
					movementVector.x = 1.0f;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_W)
				{
					movementVector.y = -1.0f;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_S)
				{
					movementVector.y = 1.0f;
				}
			}else 
			{
				if(Keyboard.getEventKey() == Keyboard.KEY_A)
				{
					movementVector.x = 0;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_D)
				{
					movementVector.x = 0;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_W)
				{
					movementVector.y = 0;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_S)
				{
					movementVector.y = 0;
				}
			}
		}
	}
	@Override
	public void disconnected(Connection c) {
		
	}
	@Override
	public void received (Connection c, Object object)
	{
		if(object instanceof PlayersPacket)
		{
			PlayersPacket playerPacket = (PlayersPacket)object;
			thisPlayer = playerPacket.getThisPlayer();
			thatPlayer = playerPacket.getThatPlayer();
		}
	}
}
