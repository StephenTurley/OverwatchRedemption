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
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 
		
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
		System.out.println(Display.getHeight()/2);
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

		 
		glViewport(0,0,Game.getGameConfig().getDisplayWidth(),Game.getGameConfig().getDisplayHeight());
		glMatrixMode(GL_MODELVIEW);

		 
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0,Game.getGameConfig().getDisplayWidth(), Game.getGameConfig().getDisplayHeight(), 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		try{
			InputStream is = ResourceLoader.getResourceAsStream("res/fonts/unispace rg.ttf");
			
			Font unispace = Font.createFont(Font.TRUETYPE_FONT, is);
			unispace = unispace.deriveFont(48f);
			titleFont = new TrueTypeFont(unispace, true);
			unispace = unispace.deriveFont(24f);
			pressStartFont = new TrueTypeFont(unispace, true);
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
