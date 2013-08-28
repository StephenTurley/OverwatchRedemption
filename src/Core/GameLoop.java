package Core;

import Core.StateManager.StateManager;


public class GameLoop implements Runnable {
	
	private StateManager sm;

	
	public GameLoop(StateManager sm)
	{
		super();
		this.sm = sm;
	}

	@Override
	public void run() {
		while(true)
		{
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sm.draw();
			sm.update();
		}
	}
	
	

}
