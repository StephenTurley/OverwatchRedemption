package gui;

import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.Button;

public class StartJoinInterface extends Widget {
	
	private Button startGameBtn;
	private Button joinGameBtn;
	private LWJGLRenderer renderer;
	private GUI gui;
	
	public StartJoinInterface() {
		
		try{
			 renderer = new LWJGLRenderer();
		}catch(Exception e)
		{
			
		}
		gui = new GUI(this, renderer);
		
		startGameBtn = new Button();
		joinGameBtn = new Button();
		add(startGameBtn);
		add(joinGameBtn);
	}
	
	public void update()
	{
		gui.update();
	}

}
