package core;


import java.util.LinkedList;
import java.util.TreeMap;


public class Menu implements DrawableComponent {

	private TreeMap<String,MenuItem> menu;
	private LinkedList<MenuItem> selectedQueue;

	private int verticalGap,x,y;
	
	public void addItem(String key, MenuItem item)
	{
		item.setLocX(this.x);
		item.setLocY(this.y + (this.verticalGap * selectedQueue.size()));
		menu.put(key, item);
		selectedQueue.addFirst(item);

	}
	
	public void selectNext()
	{
		selectedQueue.getLast().setSelected(false);
		MenuItem item = selectedQueue.removeFirst();
		item.setSelected(true);
		selectedQueue.addLast(item);
	}
	public void selectPrevious()
	{
		
		MenuItem item = selectedQueue.removeLast();
		item.setSelected(false);
		selectedQueue.getLast().setSelected(true);
		selectedQueue.addFirst(item);
		
	}
	public String getSelectedKey()
	{
		return "";
	}
	public int getVerticalGap() {
		return verticalGap;
	}

	public void setVerticalGap(int verticalGap) {
		this.verticalGap = verticalGap;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Menu(int x, int y, int verticleGap) {
		menu = new TreeMap<String, MenuItem>();
		selectedQueue = new LinkedList<MenuItem>();
		this.x = x;
		this.y = y;
		this.verticalGap = verticleGap;
	}

	@Override
	public void draw() {
		for(MenuItem m : menu.values())
		{
			m.draw();
		}
	}

	@Override
	public void update(int delta) {
		int selected = 0;
		for(MenuItem m : menu.values())
		{
			if(m.isSelected())
				selected++;
				
			m.update(delta);
		}
		if(selected == 0)	//set first item to selected if none are
			selectedQueue.getLast().setSelected(true);
	}

}
