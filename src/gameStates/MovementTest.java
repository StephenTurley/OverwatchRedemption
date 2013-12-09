/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package gameStates;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Vector2f;

import com.esotericsoftware.kryonet.Connection;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import core.Debug;
import core.Game;
import core.stateManager.GameState;
import core.stateManager.StateManager;
import core.level.ClientLevel;
import core.level.TileCoord;
//import net.java.games.input.*;
import core.network.*;
import core.network.Network.MovePlayer;
import core.network.Network.PlayersPacket;


public class MovementTest extends GameState {


	//private float deadZone;
	private Vector2f movementVector;
	//private Controller gamepad;
	
	private ClientLevel currentLevel;
	
	private Player thisPlayer; 
	private Player thatPlayer;
	
	public MovementTest(StateManager sm, ClientLevel currentLevel) {
		super(sm);
		this.currentLevel = currentLevel;
	}

	
	
	public void update(int delta) {
		
		handleInput(delta);
		MovePlayer movePkt = new MovePlayer();
		movePkt.movementVector = movementVector;
		Game.clientSendUDP(movePkt);
	}


	public void draw() {
		
		Point cameraPos = new Point(0,0);
		glColor3f(1.0f, 1.0f, 1.0f);
		//lets try to draw layer one just for the hell of it
		int displayWidth = Display.getWidth();
		int displayHeight = Display.getHeight();
		
		int tileWidth = currentLevel.getTileWidth();
		int tileHeight = currentLevel.getTileHeight();
		
		int[][] gids = currentLevel.getGidsInCamera(0, cameraPos.getX(), cameraPos.getY(), displayWidth, displayHeight);
		
		for(int y = 0;y <= displayHeight;y += tileHeight )
		{
			for (int x = 0;x <= displayWidth; x += tileWidth)
			{
				TileCoord t = currentLevel.getTileMap().getTileByGID(gids[y/tileHeight][x/tileWidth]);
				
				glBindTexture(GL_TEXTURE_RECTANGLE_ARB, t.glTextureID);
				
				glBegin(GL_QUADS);
		        glTexCoord2f(t.X, t.Y);
		        glVertex2f(x, y);
		        glTexCoord2f(t.X, t.Y2);
		        glVertex2f(x, y + tileHeight);
		        glTexCoord2f(t.X2, t.Y2);
		        glVertex2f(x + tileWidth, y + tileHeight);
		        glTexCoord2f(t.X2, t.Y);
		        glVertex2f(x + tileWidth, y);
		        glEnd();
		        
		        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);
			}
		}
		
		
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
		glEnable(GL_TEXTURE_RECTANGLE_ARB);
	}


	public void pause() {
		Game.removeClientListener(this);
		glDisable(GL_TEXTURE_RECTANGLE_ARB);
	}


	public void save() {
		// TODO Auto-generated method stub

	}
	
	public void enter()
	{
		//deadZone = Game.getGameConfig().getJoyStickDeadZone();
		
		movementVector = new Vector2f(0,0);
		
		//gamepad = Game.getGamePad();
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0,Game.getGameConfig().getDisplayWidth(), Game.getGameConfig().getDisplayHeight(), 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		glEnable(GL_TEXTURE_RECTANGLE_ARB);

		Game.addClientListener(this);
		
		Game.clientSendTCP(new Network.PlayerReady(true));
		
		Debug.Trace("Movement Test Started!");
		
	}
	
	public void exit() {
		glDisable(GL_TEXTURE_RECTANGLE_ARB);

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
