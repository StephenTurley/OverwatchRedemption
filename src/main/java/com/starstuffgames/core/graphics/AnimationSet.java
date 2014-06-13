package com.starstuffgames.core.graphics;

import java.util.HashMap;

import org.lwjgl.util.Point;

import com.starstuffgames.core.entity.EntityState;
import com.starstuffgames.core.graphics.cardinality.Direction;

public class AnimationSet {
	
	private SpriteSheet sprites;
	
	//this will replace direction map
	private HashMap<Direction, AnimationMap> animations;
	
	public AnimationSet(String spriteSheet, String animationDefinition, EntityState stateEnum)
	{
		sprites = SpriteLoader.load(spriteSheet);
		animations = new HashMap<>();
		parseAnimations(stateEnum);
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
	private void parseAnimations(EntityState stateEnum)
	{
		//TODO : parse animation data
	}

}
