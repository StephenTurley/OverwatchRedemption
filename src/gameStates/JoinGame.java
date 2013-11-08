package gameStates;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.esotericsoftware.kryonet.Connection;

import core.Debug;
import core.Game;
import core.network.Network;
import core.network.Network.ServerMessage;
import core.stateManager.GameState;
import core.stateManager.StateManager;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;

public class JoinGame extends GameState {
	private LWJGLRenderer renderer; 
    private UI uiWidget;
    private GUI gui;
	
	private class UI extends Widget
	{
		private Button joinGameBtn;
		
		private EditField playerNameEdf;
		private EditField hostNameEdf;
		private EditField hostPortEdf;
		
		private Label playerNameLbl;
		private Label hostNameLbl;
		
		
		public UI()
		{
			joinGameBtn = new Button();
			joinGameBtn.setTheme("button");
			joinGameBtn.setText("Join Game");
			joinGameBtn.addCallback(new Runnable(){
				public void run()
				{
					joinServer(hostNameEdf.getText(), Integer.parseInt(hostPortEdf.getText()));
				}
			});
			add(joinGameBtn);
			
			playerNameEdf = new EditField();
			playerNameEdf.setTheme("editField");
			playerNameEdf.setText("Player 1");
			add(playerNameEdf);
			
			hostNameEdf = new EditField();
			hostNameEdf.setTheme("editField");
			add(hostNameEdf);
			
			hostPortEdf = new EditField();
			hostPortEdf.setTheme("editField");
			hostPortEdf.setText(String.valueOf(Game.getGameConfig().getServerTCP()));
			add(hostPortEdf);
			
			playerNameLbl = new Label("Player's Name:");
			playerNameLbl.setTheme("label");
			add(playerNameLbl);
			
			hostNameLbl = new Label("Hostname:Port :");
			hostNameLbl.setTheme("label");
			add(hostNameLbl);
					
		}
		@Override
		protected void layout() {
			int width = Display.getWidth();
			int height = Display.getHeight();
			
			final int gap = 25;
			
			joinGameBtn.adjustSize();
			joinGameBtn.setPosition((width - joinGameBtn.getWidth()) - 50, height - 150);
			
			playerNameLbl.adjustSize();
			playerNameLbl.setPosition(50, 100);
			
			playerNameEdf.setSize(125, 25);
			playerNameEdf.setPosition(playerNameLbl.getX() + playerNameLbl.getWidth() + gap, 100);
			
			hostNameLbl.adjustSize();
			hostNameLbl.setPosition(playerNameEdf.getX() + playerNameEdf.getWidth() + gap, 100);
			
			hostNameEdf.setSize(300, 25);
			hostNameEdf.setPosition(hostNameLbl.getX() + hostNameLbl.getWidth() + gap, 100);
			
			hostPortEdf.setSize(50, 25);
			hostPortEdf.setPosition(hostNameEdf.getX() + hostNameEdf.getWidth() + 10, 100);
			
		}
		public String getPlayerName()
		{
			return playerNameEdf.getText();
		}
	}

	public JoinGame(StateManager sm) {
		super(sm);
		// TODO Auto-generated constructor stub
	}
	
	public JoinGame()
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
		gui.destroy();
		uiWidget.destroy();
	}
	
	private void joinServer(String host, int port)
	{
		Game.bindClient(5000, host, port, port);
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
					joinServer(uiWidget.hostNameEdf.getText(),Integer.parseInt(uiWidget.hostPortEdf.getText()));
				}
				gui.handleKey(Keyboard.getEventKey(), Keyboard.getEventCharacter(), Keyboard.getEventKeyState());
				gui.handleKeyRepeat();
				gui.clearKeyboardState();
			}
		}
		
	}
	
}
