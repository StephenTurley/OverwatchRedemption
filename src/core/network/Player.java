package core.network;

public class Player {

	public String name;
	public boolean ready;
	
	public Player() {
		this.ready = false;
	}
	public Player(String name)
	{
		this.name = name;
		this.ready = false;
	}

}
