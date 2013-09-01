package gameStates;

import java.io.InputStream;

import core.Debug;
import core.Game;
import core.stateManager.*;

import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.*;
import org.newdawn.slick.util.ResourceLoader;
import java.awt.Font;


public class GameStart extends GameState {
	
	private TrueTypeFont font;
	
	public GameStart(StateManager sm)
	{
		super(sm);
	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 
		font.drawString(100, 100, "This is test test",Color.blue);
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
		if(Game.getGameConfig().isDebugLogging()){
			Debug.Trace("Start State has been entered!");
		}
		glEnable(GL_TEXTURE_2D);
		glShadeModel(GL_SMOOTH);       
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_LIGHTING);                   

		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);               
		glClearDepth(1);                                      

		 
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		 
		glViewport(0,0,800,600);
		glMatrixMode(GL_MODELVIEW);

		 
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0,800,600, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		try{
			InputStream is = ResourceLoader.getResourceAsStream("res/fonts/CaviarDreams.ttf");
			
			Font unispace = Font.createFont(Font.TRUETYPE_FONT, is);
			unispace = unispace.deriveFont(48f);
			font = new TrueTypeFont(unispace, true);
		}catch(Exception e)
		{
			Debug.Trace(e.getMessage());
			Display.destroy();
			System.exit(1);
		}
		
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}

	

}
