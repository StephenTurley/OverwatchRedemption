package com.starstuffgames.core.graphics;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.starstuffgames.core.entity.EntityState;

public class AnimationMap {
	
	private HashMap<EntityState,AnimatedSprite> animations;

	public AnimationMap()
	{
		animations = new HashMap<>();
	}
	public void clear() {
		animations.clear();
	}

	public boolean containsState(EntityState entityState) {
		return animations.containsKey(entityState);
	}

	public boolean containsAnimation(AnimatedSprite animatedSprite) {
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

	public Collection<AnimatedSprite> animations() {
		return animations.values();
	}

	public AnimatedSprite put(EntityState entityState, AnimatedSprite animatedSprite) {
		
		return animations.put(entityState, animatedSprite);
	}
}
