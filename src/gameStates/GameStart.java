/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package gameStates;

import java.io.InputStream;

import core.Debug;
import core.Game;
import core.stateManager.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.*;
import org.newdawn.slick.util.ResourceLoader;
import java.awt.Font;


public class GameStart extends GameState {
	
	private TrueTypeFont titleFont;
	private String titleText = "OverWatch: Redemption";
	private TrueTypeFont pressStartFont;
	private String pressStartText = "Press start or enter to continue";
	
	private boolean showStartText = true;
	private int timeStartTextShown = 0;
	
	public GameStart(StateManager sm)
	{
		super(sm);
	}

	@Override
	public void update(int delta) {
		
		handleInput();
		
		timeStartTextShown += delta;
		
		if(timeStartTextShown >= 250)
		{
			timeStartTextShown = 0;
			if(showStartText)
			{
				showStartText = false;
			}
			else
				showStartText = true;
		}
		
	}

	@Override
	public void draw() {
		
		
		titleFont.drawString((Game.getGameConfig().getDisplayWidth()/2)-(titleFont.getWidth(titleText)/2),
				(Game.getGameConfig().getDisplayHeight()/2) - (titleFont.getHeight()/2) ,
				titleText,Color.orange);
		
		if(showStartText)
		{
			pressStartFont.drawString((Game.getGameConfig().getDisplayWidth()/2)-(pressStartFont.getWidth(pressStartText)/2),
					(Game.getGameConfig().getDisplayHeight()/2) - (titleFont.getHeight() - 100),
					pressStartText, Color.orange);
		}
	}

	@Override
	public void resume() {
		init();
		
	}

	@Override
	public void pause() {
		glDisable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enter() {
		Debug.Trace("Start State has been entered!");
	
		init();
		
		try{
			InputStream is = ResourceLoader.getResourceAsStream("fonts/unispace rg.ttf");
			
			Font unispace = Font.createFont(Font.TRUETYPE_FONT, is);
			unispace = unispace.deriveFont(48f);
			titleFont = new TrueTypeFont(unispace, true);
			unispace = unispace.deriveFont(24f);
			pressStartFont = new TrueTypeFont(unispace, true);
		}catch(Exception e)
		{
			Debug.Trace(e.getMessage());
			Display.destroy();
			Game.exit(1);
		}
		
	}

	@Override
	public void exit() {
		glDisable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
	}
	private void init()
	{
		glEnable(GL_TEXTURE_2D);
		glShadeModel(GL_SMOOTH);       
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_LIGHTING);                   

		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);               
		glClearDepth(1);                                      

		 
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		 
		glViewport(0,0,Game.getGameConfig().getDisplayWidth(),Game.getGameConfig().getDisplayHeight());
		glMatrixMode(GL_MODELVIEW);

		 
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0,Game.getGameConfig().getDisplayWidth(), Game.getGameConfig().getDisplayHeight(), 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
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
					super.sm.push(new MainMenu(super.sm));
				}
			}
		}
	}
	

}
