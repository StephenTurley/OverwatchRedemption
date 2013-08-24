package Core;

import Core.StateManager.StateManager;
import java.awt.*;

public class GameLoop implements Runnable {
	
	private StateManager sm;
	private Graphics2D g2;
	
	public GameLoop(StateManager sm, Graphics2D g2)
	{
		super();
		this.sm = sm;
		this.g2 = g2;
	}

	@Override
	public void run() {
		while(true)
		{
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sm.draw(g2);
			sm.update();
		}
	}
	
	

}
