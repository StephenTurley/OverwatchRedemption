/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package com.starstuffgames.overwatch.gameStates;


import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.esotericsoftware.kryonet.Connection;

import com.starstuffgames.core.Debug;
import com.starstuffgames.core.Game;
import com.starstuffgames.core.network.Network;
import com.starstuffgames.core.network.Network.ServerMessage;
import com.starstuffgames.core.stateManager.GameState;
import com.starstuffgames.core.stateManager.StateManager;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;

public class HostGame extends GameState{
	
	private LWJGLRenderer renderer; 
    private UI uiWidget;
    private GUI gui;
	
	private class UI extends Widget
	{
		private Button startGameBtn;
		private EditField playerNameEdf;
		private Label nameLbl;
		
		public UI()
		{
			startGameBtn = new Button();
			startGameBtn.setTheme("button");
			startGameBtn.setText("Start Game");
			startGameBtn.addCallback(new Runnable(){
				public void run()
				{
					startServer();
				}
			});
			add(startGameBtn);
			
			playerNameEdf = new EditField();
			playerNameEdf.setTheme("editField");
			playerNameEdf.setText("Player 1");
			add(playerNameEdf);
			
			nameLbl = new Label("Player's Name:");
			nameLbl.setTheme("label");
			add(nameLbl);
			
		}
		@Override
		protected void layout() {
			int width = Display.getWidth();
			int height = Display.getHeight();
			
			startGameBtn.adjustSize();
			startGameBtn.setPosition((width - startGameBtn.getWidth()) - 50, height - 150);
			
			nameLbl.adjustSize();
			nameLbl.setPosition(50, 100);
			
			playerNameEdf.setSize(125, 25);
			playerNameEdf.setPosition(nameLbl.getX() + nameLbl.getWidth() + 25, 100);
		}
		public String getPlayerName()
		{
			return playerNameEdf.getText();
		}
	}
	public HostGame(StateManager sm) {
		super(sm);
		// TODO Auto-generated constructor stub
	}

	public HostGame()
	{
		
	}
	
	public void received (Connection c, Object object)
	{
		if(object instanceof ServerMessage)
		{
			ServerMessage msgPacket = (ServerMessage)object;
			System.out.println(msgPacket.msg);
		}
	}
	
	@Override
	public void update(int delta) {
		handleInput();
	}

	@Override
	public void draw() {
		gui.update();
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
		try
		{
			//set up gui
			renderer = new LWJGLRenderer();
			uiWidget = new UI();
			gui = new GUI(uiWidget, renderer);
			uiWidget.playerNameEdf.requestKeyboardFocus();
			ThemeManager theme = ThemeManager.createThemeManager(

					UI.class.getResource("/gui/HostGameTheme.xml"), renderer);
	        gui.applyTheme(theme);
			
			Debug.Trace("Host Game State has been entered!");
			
		}catch(Exception e)
		{
			e.printStackTrace();
			Debug.Trace(e.getMessage());
		}
		
	}

	@Override
	public void exit() {
		Game.removeClientListener(this);
		if(Game.isServer) Game.killServer();
		gui.destroy();
		uiWidget.destroy();
	}
	public void connected(Connection c)
	{
		
	}
	private void startServer()
	{
		Game.startServer();

		Game.bindClient(500000,"localhost",Game.getGameConfig().getServerTCP(),Game.getGameConfig().getServerUDP());
		Game.addClientListener(this);
		
		Game.clientSendTCP(new Network.Login(uiWidget.getPlayerName()));
		
		sm.push(new Lobby(sm));
		
	}
	private void handleInput()
	{
		while(Keyboard.next())
		{
			if(Keyboard.getEventKeyState() || Keyboard.isRepeatEvent())
			{
				if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE)
				{
					super.sm.pop();
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_RETURN)
				{
					startServer();
				}
			}
			gui.handleKey(Keyboard.getEventKey(), Keyboard.getEventCharacter(), Keyboard.getEventKeyState());
			gui.handleKeyRepeat();
			gui.clearKeyboardState();
		}
	}
	

}
