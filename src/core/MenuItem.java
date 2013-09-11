package core;

import java.io.InputStream;

import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.*;
import org.newdawn.slick.util.ResourceLoader;
import java.awt.Font;

public class MenuItem implements DrawableComponent {
	
	private final String FONT_PATH = "res/fonts/unispace rg.ttf";
	private TrueTypeFont item;
	private TrueTypeFont itemSelected;
	private String text;
	private int locX, locY;
	
	private boolean selected;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public MenuItem(String text, int locX, int locY) {
		this.text = text;
		this.locX = locX;
		this.locY = locY;
		loadFonts();
	}

	@Override
	public void draw() {
		if(selected)
		{
			renderFont(itemSelected);
		}
		else
		{
			renderFont(item);
		}
	}

	@Override
	public void update(int delta) {
		
	}
	
	private void renderFont(TrueTypeFont font)
	{
		font.drawString(locX, locY, text, Color.orange);
	}
	
	private void loadFonts()
	{
		try{
			InputStream is = ResourceLoader.getResourceAsStream(FONT_PATH);
			
			Font unispace = Font.createFont(Font.TRUETYPE_FONT, is);
			unispace = unispace.deriveFont(48f);
			itemSelected = new TrueTypeFont(unispace, true);
			unispace = unispace.deriveFont(24f);
			item = new TrueTypeFont(unispace, true);
		}catch(Exception e)
		{
			if(Game.getGameConfig().isDebugLogging())
			{
				Debug.Trace(e.getMessage());
			}
			Display.destroy();
			System.exit(1);
		}
	}

}
