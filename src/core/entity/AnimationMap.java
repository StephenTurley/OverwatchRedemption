package core.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import core.graphics.AnimatedSprite;

public class AnimationMap {
	
	private HashMap<EntityState,AnimatedSprite> animations;

	public AnimationMap()
	{
		animations = new HashMap<EntityState, AnimatedSprite>();
	}
	public void clear() {
		animations.clear();
	}

	public boolean containsKey(EntityState entityState) {
		return animations.containsKey(entityState);
	}

	public boolean containsValue(AnimatedSprite animatedSprite) {
		return animations.containsValue(animatedSprite);
	}

	public Set<Entry<EntityState,AnimatedSprite>> entrySet() {
		return animations.entrySet();
	}

	public AnimatedSprite get(EntityState entityState) {
		return animations.get(entityState);
	}
	public boolean isEmpty() {
		return animations.isEmpty();
	}
	
	public Set<EntityState> keySet() {
		return animations.keySet();
	}
	
	public AnimatedSprite remove(EntityState entityState) {
		return animations.remove(entityState);
	}
	
	public int size() {
		return animations.size();
	}

	public Collection<AnimatedSprite> values() {
		return animations.values();
	}

	public AnimatedSprite put(EntityState entityState, AnimatedSprite animatedSprite) {
		
		return animations.put(entityState, animatedSprite);
	}
}
