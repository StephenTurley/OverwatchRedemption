package com.starstuffgames.core.graphics;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import com.starstuffgames.core.Debug;
import com.starstuffgames.core.Game;

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
		parseSpriteXML(filePath);
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
	
	private void parseSpriteXML(String filePath)
	{
		InputStream inputStream = SpriteSheet.class.getResourceAsStream(filePath);
		
		Document spriteDoc;
		SAXBuilder sb = new SAXBuilder();
		
		try {
			spriteDoc = sb.build(inputStream);
			
			Element textureAtlas = spriteDoc.getRootElement();
			
			String imagePath = textureAtlas.getAttributeValue("imagePath");
			
			int textureID = TextureLoader.loadTexture(imagePath);
			
			for(Element sprite : textureAtlas.getChildren("sprite") )
			{
				String name = sprite.getAttributeValue("n");
				int x = Integer.parseInt(sprite.getAttributeValue("x"));
				int y = Integer.parseInt(sprite.getAttributeValue("y"));
				int w = Integer.parseInt(sprite.getAttributeValue("w"));
				int h = Integer.parseInt(sprite.getAttributeValue("h"));
				int oX = Integer.parseInt(sprite.getAttributeValue("oX"));
				int oY = Integer.parseInt(sprite.getAttributeValue("oY"));
				//int oW = Integer.parseInt(sprite.getAttributeValue("oW"));
				//int oH = Integer.parseInt(sprite.getAttributeValue("oH"));
				
				TextureCoord texCoord = new TextureCoord(textureID, x,y,x + w, y + h);
				
				StaticSprite s = new StaticSprite(name, texCoord, oX, oY);
				
				sprites.put(s.getName(), s);

			}
			
			
		} catch (Exception e) {

			Debug.Trace(e.getMessage());
			Game.exit(-1);
		}
		
		
   }

}
