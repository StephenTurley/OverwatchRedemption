package com.starstuffgames.overwatch.entities.player;

import java.util.UUID;

import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Vector2f;

import com.starstuffgames.core.entity.Entity;
import com.starstuffgames.core.entity.ServerEntity;

public class ServerPlayer extends ServerEntity {
	
	private Vector2f movementVector;
	
	private final float VELOCITY = 0.25f;

	public ServerPlayer(UUID uuid, Point location, int width, int height, int layer) 
	{
		super(uuid, location, width, height, layer, "Player");
		super.currentState = Player.State.IDLE;
		
		movementVector = new Vector2f(0,0);
	}

	public void setMovementVector(Vector2f movementVector)
	{ 
		this.movementVector = movementVector;
	}
	
	@Override
	public void update(int delta) {
		super.direction = movementVector;
		location.translate((int)(movementVector.x * VELOCITY * delta),(int)(movementVector.y * VELOCITY * delta));
		
		if(this.movementVector.x == 0 && this.movementVector.y == 0)
		{
			this.currentState = Player.State.IDLE;
		}
		else
		{
			this.currentState = Player.State.WALKING;
		}
	}

	@Override
	public void collideWith(Entity entity) {

	}


}
