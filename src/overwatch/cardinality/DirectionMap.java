package overwatch.cardinality;

import java.util.Collection;
import java.util.HashMap;

import core.entity.AnimationMap;
import core.entity.EntityState;
import core.graphics.AnimatedSprite;
import core.graphics.StaticSprite;
import core.graphics.StaticSpriteSheet;

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
			
			if(name.length != 3)
			{
				throw new Exception("Sprites must have names in this format 'Direction_EntityState_Frame' (e.g. 'N_WALKING_2')");
			}
			
			Direction d = Direction.valueOf(name[0]);
			EntityState e = stateEnum.getState(name[1]);
			int frameIdx = Integer.parseInt(name[2]);
			
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
					AnimatedSprite aSprite = new AnimatedSprite(name[1], FRAME_RATE);
					aSprite.addFrame(frameIdx, s);
					a.put(e, aSprite);
				}
				
			}
			else
			{
				AnimationMap a = new AnimationMap();
				
				AnimatedSprite aSprite = new AnimatedSprite(name[1], FRAME_RATE);
				aSprite.addFrame(frameIdx, s);
				a.put(e, aSprite);
				
				animations.put(d, a);
			}
		}
	}
}
