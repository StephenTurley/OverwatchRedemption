package gameStates;

import org.lwjgl.opengl.Display;

import core.stateManager.GameState;
import core.stateManager.StateManager;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.ToggleButton;
import de.matthiasmann.twl.Widget;

public class Lobby extends GameState {
	
	private class UI extends Widget
	{
		private ToggleButton readyBtn;
		private Label systemMsgLbl;
		private Label clientNameLbl;
		private Label otherNameLbl;
		private Label readyLbl;
		
		public UI()
		{
			readyBtn = new ToggleButton();
			readyBtn.setTheme("button");
			readyBtn.setText("Start Game");
			add(readyBtn);
			
			systemMsgLbl = new Label("");
			systemMsgLbl.setTheme("label");
			add(systemMsgLbl);

			clientNameLbl = new Label("Player's Name:");
			clientNameLbl.setTheme("label");
			add(clientNameLbl);
			
			otherNameLbl = new Label("");
			otherNameLbl.setTheme("label");
			add(otherNameLbl);
			
			readyLbl = new Label("");
			readyLbl.setTheme("label");
			add(readyLbl);
		}
		@Override
		protected void layout() {
			int width = Display.getWidth();
			int height = Display.getHeight();
			
			readyBtn.adjustSize();
			readyBtn.setPosition((width - readyBtn.getWidth()) - 50, height - 150);
			
			clientNameLbl.adjustSize();
			clientNameLbl.setPosition(50, 100);
		
		}
	}

	public Lobby(StateManager sm) {
		super(sm);
		
	}

	public Lobby() {
		
	}

	@Override
	public void update(int delta) {
		

	}

	@Override
	public void draw() {
		

	}

	@Override
	public void resume() {
		

	}

	@Override
	public void pause() {
		

	}

	@Override
	public void save() {
		

	}

	@Override
	public void enter() {
		

	}

	@Override
	public void exit() {
		

	}

}
