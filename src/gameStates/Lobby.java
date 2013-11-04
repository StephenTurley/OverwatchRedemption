package gameStates;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.esotericsoftware.kryonet.Connection;

import core.Debug;
import core.Game;
import core.network.Network.SimpleMessage;
import core.stateManager.GameState;
import core.stateManager.StateManager;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.ToggleButton;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;

public class Lobby extends GameState {
	
	private LWJGLRenderer renderer; 
    private UI uiWidget;
    private GUI gui;
	
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

			clientNameLbl = new Label("Not Connected");
			clientNameLbl.setTheme("label");
			add(clientNameLbl);
			
			otherNameLbl = new Label("Not Connected");
			otherNameLbl.setTheme("label");
			add(otherNameLbl);
			
			readyLbl = new Label("Not Ready");
			readyLbl.setTheme("label");
			add(readyLbl);
		}
		@Override
		protected void layout() {
			int width = Display.getWidth();
			
			int leftColumn = 150;
			int rightColumn = width - 200;
			
			int rowCoord = 200;
			
			clientNameLbl.adjustSize();
			clientNameLbl.setPosition(leftColumn, rowCoord);
			
			otherNameLbl.adjustSize();
			otherNameLbl.setPosition(rightColumn,rowCoord);
			
			rowCoord += 100;
			
			readyBtn.adjustSize();
			readyBtn.setPosition(leftColumn, rowCoord);
			
			readyLbl.adjustSize();
			readyLbl.setPosition(rightColumn, rowCoord);
		
		}
	}

	public Lobby(StateManager sm) {
		super(sm);
		
	}

	public Lobby() {
		
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
		

	}

	@Override
	public void enter() {
		try
		{
			Game.addClientListener(this);
			//set up gui
			renderer = new LWJGLRenderer();
			uiWidget = new UI();
			gui = new GUI(uiWidget, renderer);
			ThemeManager theme = ThemeManager.createThemeManager(
	                UI.class.getResource("/gui/HostGameTheme.xml"), renderer);
	        gui.applyTheme(theme);
			
			Debug.Trace("Lobby State has been entered!");
			
		}catch(Exception e)
		{
			e.printStackTrace();
			Debug.Trace(e.getMessage());
		}
	}

	@Override
	public void exit() {
		Game.removeClientListener(this);
		gui.destroy();
		uiWidget.destroy();
	}
	
	public void received (Connection c, Object object)
	{
		if(object instanceof SimpleMessage)
		{
			SimpleMessage msgPacket = (SimpleMessage)object;
			System.out.println(msgPacket.msg);
		}
	}
	
	private void handleInput()
	{
		while(Keyboard.next())
		{
			if(Keyboard.getEventKeyState() || Keyboard.isRepeatEvent())
			{
				
				if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE)
				{
					Game.disconnectClient();
					Game.killServer();
					super.sm.pop();
				}
				gui.handleKey(Keyboard.getEventKey(), Keyboard.getEventCharacter(), Keyboard.getEventKeyState());
				gui.handleKeyRepeat();
				gui.clearKeyboardState();
			}
		}
		
	}
	

}
