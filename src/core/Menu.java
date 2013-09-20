package core;


import java.util.LinkedList;


public class Menu implements DrawableComponent {

	private LinkedList<MenuItem> selectedQueue;
	
	private float fontSize, SelectedFontSize;

	private int verticalGap,x,y;
	
	public void addItem(MenuItem item)
	{
		item.setLocX(this.x);
		item.setLocY(this.y + (this.verticalGap * selectedQueue.size()));
		item.setSelectedFontSize(this.SelectedFontSize);
		item.setFontSize(this.fontSize);
		selectedQueue.addFirst(item);
		item.loadFonts();

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
	public MenuItem getSelected()
	{
		for(MenuItem m : selectedQueue)
		{
			if (m.isSelected())
				return m;
		}
		return null;
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

	public Menu(int x, int y, int verticleGap, float selectedFontSize, float fontSize) {
		this.selectedQueue = new LinkedList<MenuItem>();
		this.fontSize = fontSize;
		this.SelectedFontSize = selectedFontSize;
		this.x = x;
		this.y = y;
		this.verticalGap = verticleGap;
	}

	@Override
	public void draw() {
		for(MenuItem m : selectedQueue)
		{
			m.draw();
		}
	}

	@Override
	public void update(int delta) {
		int selected = 0;
		for(MenuItem m : selectedQueue)
		{
			if(m.isSelected())
				selected++;
				
			m.update(delta);
		}
		if(selected == 0)	//set first item to selected if none are
			selectedQueue.getLast().setSelected(true);
	}

}