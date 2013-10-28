package gameStates;


import com.esotericsoftware.kryonet.Connection;

import core.Debug;
import core.Game;
import core.network.Network;
import core.network.Network.SimpleMessage;
import core.stateManager.GameState;
import core.stateManager.StateManager;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.GUI;
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
		
		public UI()
		{
			startGameBtn = new Button();
			startGameBtn.setTheme("button");
			startGameBtn.setText("Start Game");
			add(startGameBtn);
		}
		@Override
		protected void layout() {
			startGameBtn.adjustSize();
			startGameBtn.setPosition(100, 100);
		}
	}
	public HostGame(StateManager sm) {
		super(sm);
		// TODO Auto-generated constructor stub
	}

	public HostGame()
	{
		
	}
	@Override
	public void update(int delta) {
		
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
			ThemeManager theme = ThemeManager.createThemeManager(
	                UI.class.getResource("/gui/HostGameTheme.xml"), renderer);
	        gui.applyTheme(theme);
			
			Debug.Trace("Host Game State has been entered!");
			Game.startServer();
			Game.bindClient(5000,"localhost",Game.getGameConfig().getServerTCP(),Game.getGameConfig().getServerUDP());
			Game.addClientListener(this);
		}catch(Exception e)
		{
			e.printStackTrace();
			Debug.Trace(e.getMessage());
		}
		//TODO: login in when when player hits start.
		Game.clientSendTCP(new Network.Login("Player 1"));
	}

	@Override
	public void exit() {
		Game.removeClientListener(this);
	}
	public void connected(Connection c)
	{
		
	}
	public void received (Connection c, Object object)
	{
		if(object instanceof SimpleMessage)
		{
			SimpleMessage msgPacket = (SimpleMessage)object;
			System.out.println(msgPacket.msg);
		}
	}

}
