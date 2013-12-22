/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/

/*
 * I feel like this file should have a disclaimer. The code here is used soley for the purpose of hacking out ideas
 * This code will never go into a game in current form. ...don't judge me.
 */
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
import core.graphics.Camera;
import core.graphics.TextureCoord;
import core.level.ClientLevel;
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
	
	private Camera camera;
	
	public MovementTest(StateManager sm, ClientLevel currentLevel) {
		super(sm);
		this.currentLevel = currentLevel;
		camera = new Camera(Display.getWidth(),Display.getHeight()	);
	}

	
	
	public void update(int delta) {
		
		handleInput(delta);
		MovePlayer movePkt = new MovePlayer();
		movePkt.movementVector = movementVector;
		Game.clientSendUDP(movePkt);
		if(thisPlayer != null)
		{
			camera.setPosition(thisPlayer, currentLevel.getPixelWidth(), currentLevel.getPixelHeight());
		}
	}

	public void draw() {
		//move all this to the ClientLevel class so it can draw itself.
		//I can also do the draw loop a the same time I build the layer gids. 
		//But, for now, I'll continue to hack this shitty code until it works.
		//Don't judge me Seth.
		glColor3f(1.0f, 1.0f, 1.0f);
		
		int tileWidth = currentLevel.getTileWidth();
		int tileHeight = currentLevel.getTileHeight();
		
		int[][] gids = currentLevel.getGidsInCamera(0, camera);
		
		Point offSet = camera.getTileOffset(tileWidth, tileHeight);
		int xOffSet = offSet.getX();
		int yOffSet = offSet.getY();
		
		for(int tileY = 0;tileY < gids[0].length; tileY++ )
		{
			for (int tileX = 0;tileX < gids.length; tileX++)
			{	
				
				TextureCoord t = currentLevel.getTileMap().getTileByGID(gids[tileX][tileY]);
				
				int x = tileX * tileWidth;
				int y = tileY * tileHeight;
				
				glBindTexture(GL_TEXTURE_RECTANGLE_ARB, t.glTextureID);
				
				glBegin(GL_QUADS);
		        glTexCoord2f(t.X, t.Y);
		        glVertex2f(x - xOffSet, y - yOffSet);
		        glTexCoord2f(t.X, t.Y2);
		        glVertex2f(x - xOffSet, y + tileHeight - yOffSet);
		        glTexCoord2f(t.X2, t.Y2);
		        glVertex2f(x + tileWidth - xOffSet, y + tileHeight - yOffSet);
		        glTexCoord2f(t.X2, t.Y);
		        glVertex2f(x + tileWidth - xOffSet, y - yOffSet);
		        glEnd();
		        
		        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);
			}
		}
		
		
		if(thisPlayer != null)
		{
			thisPlayer.draw(camera);
		}
		if(thatPlayer != null)
		{
			thatPlayer.draw(camera);
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
