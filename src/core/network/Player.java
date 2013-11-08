package core.network;

public class Player {

	private String name;
	private boolean ready;
	private int id;
	
	public Player() {
		this.ready = false;
	}
	public Player(String name)
	{
		this.name = name;
		this.ready = false;
	}
	public Player clone()
	{
		Player p = new Player(this.name);
		
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
