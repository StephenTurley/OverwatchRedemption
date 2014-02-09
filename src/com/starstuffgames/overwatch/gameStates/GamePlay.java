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
package com.starstuffgames.overwatch.gameStates;

import com.starstuffgames.core.network.Network;
import com.starstuffgames.core.network.EntityDataPacket;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import com.esotericsoftware.kryonet.Connection;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import com.starstuffgames.core.Debug;
import com.starstuffgames.core.Game;
import com.starstuffgames.core.audio.MusicMap;
import com.starstuffgames.core.stateManager.GameState;
import com.starstuffgames.core.stateManager.StateManager;
import com.starstuffgames.core.graphics.Camera;
import com.starstuffgames.core.level.ClientLevel;
//import net.java.games.input.*;
import com.starstuffgames.core.network.Network.EntitiesPacket;
import com.starstuffgames.core.network.Network.FocusOn;
import com.starstuffgames.core.network.Network.MovePlayer;




public class GamePlay extends GameState {


	//private float deadZone;
	private Vector2f movementVector;
	//private Controller gamepad;
	
	private final ClientLevel currentLevel;
	
	private volatile UUID focusedEntityID; 
	
	private final Camera camera;
	
	private final ConcurrentLinkedQueue<EntityDataPacket[]> entityBuffer;
	
	public GamePlay(StateManager sm, ClientLevel currentLevel) {
		super(sm);
		this.currentLevel = currentLevel;
		entityBuffer = new ConcurrentLinkedQueue<>();
		camera = new Camera(Display.getWidth(),Display.getHeight()	);
	}

	
	
	@Override
	public void update(int delta) {
		
		if(entityBuffer.size() >= 1) currentLevel.addUpdateEntity(entityBuffer.poll());
		
		currentLevel.update(delta);
		
		handleInput(delta);
		MovePlayer movePkt = new MovePlayer();
		movePkt.movementVector = movementVector;
		Game.clientSendUDP(movePkt);
	}

	public void draw() {
		if(focusedEntityID != null)
		{
			camera.setPosition(currentLevel.getEntity(focusedEntityID), currentLevel.getPixelWidth(), currentLevel.getPixelHeight());
		}
		
		currentLevel.draw(camera);
	}


	@Override
	public void resume() {
		Game.addClientListener(this);
		glEnable(GL_TEXTURE_RECTANGLE_ARB);
		glEnable( GL_BLEND );
	}


	@Override
	public void pause() {
		Game.removeClientListener(this);
		glDisable(GL_TEXTURE_RECTANGLE_ARB);
		glDisable(GL_BLEND);
	}


	@Override
	public void save() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void enter()
	{
		//deadZone = Game.getGameConfig().getJoyStickDeadZone();

		MusicMap.add("GamePlay", "bgm/ARPCrazy.ogg");
		MusicMap.play("GamePlay");
		MusicMap.setVolume(0.1f);

		
		movementVector = new Vector2f(0,0);
		
		//gamepad = Game.getGamePad();
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0,Game.getGameConfig().getDisplayWidth(), Game.getGameConfig().getDisplayHeight(), 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		glEnable(GL_TEXTURE_RECTANGLE_ARB);
		
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable( GL_BLEND );

		Game.addClientListener(this);
		
		Game.clientSendTCP(new Network.PlayerReady(true));
		
		Debug.Trace("Movement Test Started!");
		
	}
	
	public void exit() {
		glDisable(GL_TEXTURE_RECTANGLE_ARB);
		glDisable(GL_BLEND);

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
		if(object instanceof FocusOn)
		{
			
			FocusOn pkt = (FocusOn)object;
			
			focusedEntityID = pkt.uuid;

		}
		if(object instanceof EntitiesPacket)
		{
			EntitiesPacket ep = (EntitiesPacket)object;
			
			entityBuffer.offer(ep.entities);
			
		}
	}
}
