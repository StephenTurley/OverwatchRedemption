/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.starstuffgames.overwatch.entities.enemies.turret;

import com.starstuffgames.core.entity.ClientEntity;
import com.starstuffgames.core.entity.EntityState;
import com.starstuffgames.core.entity.EntityTemplate;
import com.starstuffgames.core.entity.ServerEntity;
import java.util.UUID;
import org.lwjgl.util.Point;

/**
 *
 * @author stephen
 */
public class Turret implements EntityTemplate
{

	private final int WIDTH = 101;
	private final int HEIGHT = 110;
	
	public static enum State implements EntityState
	{
		ARMED,
		OPENING,
		CLOSING,
		SUPPRESSED;
		

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
	public ServerEntity createServerEntity(UUID uuid, Point location, int layer)
	{
		return new ServerTurret(uuid, location, WIDTH, HEIGHT, layer, "Turret");
	}

	@Override
	public ClientEntity createClientEntity(UUID uuid, Point location, int layer)
	{
		return new ClientTurret(uuid, location, WIDTH, HEIGHT, layer, "Turret");
	}
	
}
