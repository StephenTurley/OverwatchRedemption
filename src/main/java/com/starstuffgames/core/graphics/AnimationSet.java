package com.starstuffgames.core.graphics;

import java.io.InputStream;
import java.util.HashMap;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.lwjgl.util.Point;

import com.starstuffgames.core.Debug;
import com.starstuffgames.core.Game;
import com.starstuffgames.core.entity.EntityState;
import com.starstuffgames.core.graphics.cardinality.Direction;

public class AnimationSet {
	
	private SpriteSheet sprites;
	
	//this will replace direction map
	private HashMap<Direction, AnimationMap> animations;
	
	
	public AnimationSet(String spriteFilePath, String animationFilePath, EntityState stateEnum)
	{
		sprites = new SpriteSheet(spriteFilePath);
		animations = new HashMap<>();
		parseAnimations(animationFilePath, stateEnum);
	}
	
	
	public void draw(Direction direction, EntityState entityState, Camera camera, Point position)
	{
		AnimationMap map = animations.get(direction);
		
		//if animation is omni-directional
		if(!map.containsState(entityState))
		{
			map = animations.get(Direction.OMNI);
		}
		
		map.get(entityState).draw(camera, position);
	}
	
	public void update(int delta)
	{
		for(AnimationMap amp : animations.values())
		{
			for(AnimatedSprite ams : amp.animations())
			{
				ams.update(delta);
			}
		}
	}
	
	public void resetAnimation()
	{
		for(AnimationMap amp : animations.values())
		{
			for(AnimatedSprite ams : amp.animations())
			{
				ams.reset();
			}
		}
	}
	private void parseAnimations(String filePath, EntityState stateEnum)
	{
		InputStream inputStream = AnimationSet.class.getResourceAsStream(filePath);
		
		Document spriteDoc;
		SAXBuilder sb = new SAXBuilder();
		
		try {
			spriteDoc = sb.build(inputStream);
			
			Element animationsElem = spriteDoc.getRootElement();
			
			for(Element direction : animationsElem.getChildren())
			{
				Direction currentDir = Direction.valueOf(direction.getAttributeValue("name"));
				
				AnimationMap map;
				
				if(animations.containsValue(currentDir))
				{
					map = animations.get(currentDir);
				}
				else
				{
					map = new AnimationMap();
					animations.put(currentDir, map);
				}
				
				for(Element animation : direction.getChildren())
				{
					EntityState currentState = stateEnum.getState(animation.getAttributeValue("name"));
					
					AnimatedSprite sprite = new AnimatedSprite(currentState.toString(), Integer.parseInt(animation.getAttributeValue("fps"))
							, Integer.parseInt(animation.getAttributeValue("frameCount")), Boolean.parseBoolean(animation.getAttributeValue("looping")));
					
					for(Element frame : animation.getChildren())
					{
						sprite.addFrame(Integer.parseInt(frame.getAttributeValue("frameIdx")), sprites.getStaticSprite(frame.getAttributeValue("n")));
					}
					
					map.put(currentState, sprite);
				
				}
			}
			
			
		} catch (Exception e) {

			Debug.Trace(e.getMessage());
			Game.exit(-1);
		}
		
	}

}
