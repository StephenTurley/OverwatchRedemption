package overwatch.entities.player;

import java.util.UUID;

import org.lwjgl.util.Point;

import core.entity.ClientEntity;
import core.entity.EntityState;
import core.entity.EntityTemplate;
import core.entity.ServerEntity;

public class Player implements EntityTemplate {
	
	private static final int WIDTH = 32;
	private static final int HEIGHT = 32;
	
	
	public static enum State implements EntityState
	{
		IDLE,
		WALKING,
		RUNNING,
		DYING,
		DEAD;
		

		@Override
		public int getStateValue() {
			return ordinal();
		}

		@Override
		public EntityState getState(String string) {
			return State.valueOf(string);
		}
		
		@Override
		public EntityState getState(int ordinalValue)
		{
			return State.values()[ordinalValue];
		}
		
	}

	@Override
	public ServerEntity createServerEntity(UUID uuid, Point location, int layer) {
		return new ServerPlayer(uuid,location, WIDTH, HEIGHT, layer);
	}

	@Override
	public ClientEntity createClientEntity(UUID uuid, Point location, int layer) {
		return new ClientPlayer(uuid, location, WIDTH, HEIGHT, layer);
	}

}
