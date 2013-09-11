package gui;

import core.Debug;
import core.Game;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;
import de.matthiasmann.twl.Label;

public class MainMenuUI extends Widget {
	
	private Label hostGamelbl;
	private Label joinGamelbl;
	private LWJGLRenderer renderer;
	private GUI gui;
	private ThemeManager theme;
	
	public MainMenuUI() 
	{
		
		try
		{
			 renderer = new LWJGLRenderer();
			 gui = new GUI(this, renderer);
			 theme = ThemeManager.createThemeManager(
					 MainMenuUI.class.getResource("MainMenuUI.xml"), renderer);
			 gui.applyTheme(theme);
				
		}catch(Exception e)
		{
			if(Game.getGameConfig().isDebugLogging())
			{
				Debug.Trace(e.getMessage());
			}
		}
		
		
		hostGamelbl =  new Label();
		joinGamelbl = new Label();
		
		hostGamelbl.setText("Host Game");
		joinGamelbl.setText("Join Game");
		
		
		add(hostGamelbl);
		add(joinGamelbl);
		
		
		joinGamelbl.adjustSize();
		
		hostGamelbl.setSize(200, 200);
		hostGamelbl.adjustSize();
		
		hostGamelbl.setPosition(100, 100);
		joinGamelbl.setPosition(300, 100);
	}
	
	public void update()
	{
		gui.update();
	}

}
