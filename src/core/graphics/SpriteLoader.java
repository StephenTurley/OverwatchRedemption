package core.graphics;

import java.io.InputStream;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import core.Debug;
import core.Game;

/**
 * 
 * Loads in Sprite sheet textures to GPU and data into a SpriteSheet object. 
 */

public class SpriteLoader {
	
	public static StaticSpriteSheet load(String filePath)
	{
		InputStream inputStream = SpriteLoader.class.getResourceAsStream(filePath);
		
		return parseSpriteXML(inputStream);
	}
	
	private static StaticSpriteSheet parseSpriteXML(InputStream inputStream)
	{
		StaticSpriteSheet staticSpriteSheet =  new StaticSpriteSheet();
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
				
				staticSpriteSheet.addStaticSprite(s);
			}
			
			
		} catch (Exception e) {

			Debug.Trace(e.getMessage());
			Game.exit(-1);
		}
		
		
		return staticSpriteSheet;
	}

}
