package overwatch.entities.player;

import java.util.UUID;

import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Vector2f;

import core.entity.Entity;
import core.entity.ServerEntity;
import core.graphics.Camera;

public class ServerPlayer extends ServerEntity {
	
	private Vector2f movementVector;
	
	private final float VELOCITY = 0.25f;

	public ServerPlayer(UUID uuid, Point location, int width, int height, int layer) 
	{
		super(uuid, location, width, height, layer);
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
	}

	@Override
	public void collideWith(Entity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Camera camera) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadAssets() {
		// TODO Auto-generated method stub

	}

}
