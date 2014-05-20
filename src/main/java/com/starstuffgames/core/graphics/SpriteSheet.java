package com.starstuffgames.core.graphics;
import java.util.Collection;
import java.util.HashMap;

public class SpriteSheet {

	private HashMap<String, StaticSprite> sprites;
	private String filePath;

	public HashMap<String, StaticSprite> getSprites() {
		return sprites;
	}

	public String getFilePath() {
		return filePath;
	}

	public SpriteSheet(String filePath)
	{
		sprites = new HashMap<>();
		this.filePath = filePath;
	}
	
	public void addStaticSprite(StaticSprite staticSprite)
	{
		sprites.put(staticSprite.getName(), staticSprite);
	}
	
	public StaticSprite getStaticSprite(String name)
	{
		return sprites.get(name);
	}
	
	public Collection<StaticSprite> getStaticSprites()
	{
		return sprites.values();
	}

}
