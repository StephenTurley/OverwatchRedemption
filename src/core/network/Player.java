package core.network;

import core.Entity;

public class Player extends Entity{

	private String name;
	private boolean ready;
	private int id;
	
	private static final int WIDTH = 50;
	private static final int HEIGHT = 50;
	
	public Player()
	{
	
	}
	
	public Player(int startX, int startY) {
		super(startX, startY, WIDTH, HEIGHT, 1.0f, 0.0f);
		this.ready = false;
	}
	public Player(int startX, int startY, String name)
	{
		super(startX, startY, WIDTH, HEIGHT, 1.0f, 0.0f);
		this.name = name;
		this.ready = false;
	}
	public Player clone()
	{
		Player p = new Player(super.locX, super.locY, this.name);
		
		p.setReady(this.isReady());
		p.setId(this.getId());
		
		return p;
	
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isReady() {
		return ready;
	}
	public void setReady(boolean ready) {
		this.ready = ready;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
