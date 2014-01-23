package overwatch.entities.player;

import java.util.UUID;

import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;

import overwatch.cardinality.Direction;
import core.Debug;
import core.Game;
import core.entity.ClientEntity;
import core.entity.Entity;
import core.graphics.Camera;

public class ClientPlayer extends ClientEntity {

	private PlayerAssets assets;
	
	public ClientPlayer(UUID uuid, Point location,int width, int height, int layer) {
		super(uuid, location, width, height, layer);
		super.currentState = Player.State.IDLE;
	}

	@Override
	public void update(int delta) 
	{
		if(assets!= null)
		{
			assets.update(delta);
			assets.setDirection(Direction.fromVector2f(super.direction));
			assets.setState(Player.State.IDLE);
		}
	}

	@Override
	public void draw(Camera camera) {
		if (assets != null && camera.isVisible(new Rectangle(location.getX(), location.getY(), width, height)))
		{
			assets.draw(camera, this.location);			
		}
	}

	
	@Override
	public void loadAssets() {
		try
		{
			assets = new PlayerAssets(super.currentState);
		}
		catch (Exception e)
		{
			Debug.Trace("Player assets failed to load");
			Game.exit(1);
		}
	}

	@Override
	public void collideWith(Entity entity) {
		// TODO Auto-generated method stub

	}

}
