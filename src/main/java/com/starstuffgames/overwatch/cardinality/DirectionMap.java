package com.starstuffgames.overwatch.cardinality;

import java.util.Collection;
import java.util.HashMap;

import com.starstuffgames.core.entity.AnimationMap;
import com.starstuffgames.core.entity.EntityState;
import com.starstuffgames.core.graphics.AnimatedSprite;
import com.starstuffgames.core.graphics.StaticSprite;
import com.starstuffgames.core.graphics.StaticSpriteSheet;

/*
 * Class that maps Direction to Animation Map
 */
public class DirectionMap {
	
	private final int FRAME_RATE = 5;
	
	private final HashMap<Direction, AnimationMap> animations;
	
	public DirectionMap(StaticSpriteSheet spriteSheet, EntityState stateEnum) throws Exception
	{
		animations = new HashMap<>();
		
		parseSpriteSheet(spriteSheet, stateEnum);
	}
	
	public AnimatedSprite getAnimation(Direction direction, EntityState entityState)
	{
		AnimationMap am = animations.get(direction);
		
		return am.get(entityState);
	}
	
	public void update(int delta, EntityState currentState)
	{
		//update all animations of current state to keep them in step
		//might need to just keep track of current frame and have the current
		//animation play from that frame ..cause this will be slow
		for(AnimationMap am : animations.values())
		{
			am.get(currentState).update(delta);
		}
	}

	private void parseSpriteSheet(StaticSpriteSheet spriteSheet, EntityState stateEnum) throws Exception {
		Collection<StaticSprite> sprites = spriteSheet.getStaticSprites();
		
		for(StaticSprite s : sprites)
		{
			String[] name = s.getName().split("_");
			
			if(!(name.length == 3 || name.length == 2))
			{
				throw new Exception("Sprite names must be in the correct format: e.g. N_IDLE_! or OPENING_2");
			}
			
			EntityState e;
			int frameIdx;
			
			if(name.length == 3)
			{
				Direction d = Direction.valueOf(name[0]);
				e = stateEnum.getState(name[1]);
				frameIdx = Integer.parseInt(name[2]);
				addAnimation(d, e, frameIdx, s, e.toString());
			}
			else
			{
				e = stateEnum.getState(name[0]);
				frameIdx = Integer.parseInt(name[1]);
				for(Direction d : Direction.values())
				{
					addAnimation(d, e, frameIdx, s, e.toString());
				}
			}
		}
	}

	private void addAnimation(Direction d, EntityState e, int frameIdx, StaticSprite s, String name)
	{
		if(animations.containsKey(d))
		{
			AnimationMap a = animations.get(d);
			
			if(a.containsState(e))
			{
				AnimatedSprite aSprite = a.get(e);
				aSprite.addFrame(frameIdx, s);
			}
			else
			{
				AnimatedSprite aSprite = new AnimatedSprite(name, FRAME_RATE);
				aSprite.addFrame(frameIdx, s);
				a.put(e, aSprite);
			}
			
		}
		else
		{
			AnimationMap a = new AnimationMap();
			
			AnimatedSprite aSprite = new AnimatedSprite(name, FRAME_RATE);
			aSprite.addFrame(frameIdx, s);
			a.put(e, aSprite);
			
			animations.put(d, a);
		}
	}
}
