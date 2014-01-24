/*******************************************************************************
 * Copyright 2013 Stephen Turley
 * 
 * This software is part of the Overwatch-Redemption and is not licensed for redistribution. 
 * You may not reproduce any part of this work unless otherwise stated.
 ******************************************************************************/
package core.gui;

import core.Debug;
import core.Game;
import core.graphics.DrawableComponent;
import core.stateManager.*;

import java.io.InputStream;

import org.newdawn.slick.*;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.Font;

public abstract class MenuItem implements DrawableComponent {
	
	private final String FONT_PATH = "fonts/unispace rg.ttf";
	private TrueTypeFont item;
	private TrueTypeFont itemSelected;
	private float selectedFontSize;
	private float fontSize;
	protected String text;
	private int locX, locY;
	
	public float getFontSize() {
		return fontSize;
	}

	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

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
	
	public int getLocX() {
		return locX;
	}

	public void setLocX(int locX) {
		this.locX = locX;
	}

	public int getLocY() {
		return locY;
	}

	public void setLocY(int locY) {
		this.locY = locY;
	}
	
	public MenuItem()
	{

	}
	public MenuItem( int locX, int locY) {
		this.locX = locX;
		this.locY = locY;
	}

	public TrueTypeFont getItemSelected() {
		return itemSelected;
	}

	public void setItemSelected(TrueTypeFont itemSelected) {
		this.itemSelected = itemSelected;
	}

	public float getSelectedFontSize() {
		return selectedFontSize;
	}

	public void setSelectedFontSize(float selectedFontSize) {
		this.selectedFontSize = selectedFontSize;
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
	
	public abstract GameState execute();
	
	private void renderFont(TrueTypeFont font)
	{
		font.drawString(locX, locY, text, Color.orange);
	}
	
	public void loadFonts()
	{
		try{
			InputStream is = ResourceLoader.getResourceAsStream(FONT_PATH);
			
			Font unispace = Font.createFont(Font.TRUETYPE_FONT, is);
			unispace = unispace.deriveFont(this.selectedFontSize);
			itemSelected = new TrueTypeFont(unispace, true);
			unispace = unispace.deriveFont(this.fontSize);
			item = new TrueTypeFont(unispace, true);
		}catch(Exception e)
		{
			Debug.Trace(e.getMessage());
			Game.exit(1);
		}
	}

}
