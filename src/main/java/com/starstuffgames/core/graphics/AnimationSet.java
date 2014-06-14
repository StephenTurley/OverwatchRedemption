package com.starstuffgames.core.graphics;

import java.io.InputStream;
import java.util.HashMap;

import org.lwjgl.util.Point;

import com.starstuffgames.core.entity.EntityState;
import com.starstuffgames.core.graphics.cardinality.Direction;

public class AnimationSet {
	
	private SpriteSheet sprites;
	
	//this will replace direction map
	private HashMap<Direction, AnimationMap> animations;
	
	private class Animation
	{
		public Direction direction;
		public EntityState state;
		public int frameCount;
		public AnimatedSprite animatedSprite;
	}
	
	public AnimationSet(String spriteFilePath, String animationFilePath, EntityState stateEnum)
	{
		sprites = new SpriteSheet(spriteFilePath);
		animations = new HashMap<>();
		parseAnimations(animationFilePath, stateEnum);
	}
	
	public void draw(Direction direction, EntityState entityState, Camera camera, Point position)
	{
		animations.get(direction).get(entityState).draw(camera, position);
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
	private void parseAnimations(String filePath, EntityState stateEnum)
	{
		InputStream inputStream = AnimationSet.class.getResourceAsStream(filePath);
		
		
	}

}
