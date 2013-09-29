package gameStates;

import static org.lwjgl.opengl.GL11.*;
import gui.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import core.Debug;
import core.Game;
import core.Menu;
import core.MenuItem;
import core.stateManager.GameState;
import core.stateManager.StateManager;

public class MainMenu extends GameState {
	
	private Menu mainMenu;
	
	

	public MainMenu(StateManager sm) {
		super(sm);
		mainMenu = new Menu(100,100,100, 48f, 24f);
		mainMenu.addItem(new HostGameMenuItem());
		mainMenu.addItem(new JoinGameMenuItem());
		mainMenu.addItem(new OptionsMenuItem());
		mainMenu.addItem(new QuitGameMenuItem());
		
	}

	@Override
	public void update(int delta) {
		mainMenu.update(delta);
		handleInput();
	}

	@Override
	public void draw() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		mainMenu.draw();
		Display.update();
	}

	@Override
	public void resume() {
		Keyboard.enableRepeatEvents(true);
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
		Debug.Trace("Main Menu State has been entered!");

		Keyboard.enableRepeatEvents(true);
		init();

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
					MenuItem m = mainMenu.getSelected();
					GameState gs = m.execute();
					gs.setSm(sm);
					sm.push(gs);
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_DOWN)
				{
					mainMenu.selectPrevious();
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_UP)
				{
					mainMenu.selectNext();
				}
			}
		}
	}

}
