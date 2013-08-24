package Core;
import Core.StateManager.*;
import java.awt.*;
import javax.swing.*;



public class Game {
	
	final boolean DEBUG_MODE;
	private static StateManager sm;
	private JFrame gameWindow;
	private Graphics2D g2;
	private GameLoop loop;
	
	public Game()
	{
		DEBUG_MODE = true;
		Debug.Trace("Game is running!");
		
	}
	
	
	public StateManager getStateManager()
	{
		return sm;
	}
	public void start(GameState startingState)
	{
		sm = new StateManager(startingState);
		gameWindow = new JFrame();
		gameWindow.setTitle("PixelFighter");
		gameWindow.setSize(1440,900);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setVisible(true);
		
		g2 = (Graphics2D)gameWindow.getGraphics();
		
		loop = new GameLoop(sm, g2);
		
		Thread t = new Thread(loop);
		t.start();
		
	}

}
