package core.graphics;
import java.util.HashMap;

public class StaticSpriteSheet {

	private HashMap<String, StaticSprite> sprites;
	
	public StaticSpriteSheet()
	{
		sprites = new HashMap<String, StaticSprite>();
	}
	
	public void addStaticSprite(StaticSprite staticSprite)
	{
		sprites.put(staticSprite.getName(), staticSprite);
	}
	
	public StaticSprite getStaticSprite(String name)
	{
		return sprites.get(name);
	}
	
	public StaticSprite[] getStaticSprites()
	{
		return (StaticSprite[]) sprites.values().toArray();
	}

}
