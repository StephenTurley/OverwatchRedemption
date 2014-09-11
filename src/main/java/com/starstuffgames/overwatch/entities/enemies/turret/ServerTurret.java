/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.starstuffgames.overwatch.entities.enemies.turret;

import com.starstuffgames.core.entity.Entity;
import com.starstuffgames.core.entity.EntityCollection;
import com.starstuffgames.core.entity.ServerEntity;
import com.starstuffgames.core.math.MathHelper;
import com.starstuffgames.overwatch.entities.player.ServerPlayer;

import java.util.UUID;

import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author stephen
 */
public class ServerTurret extends ServerEntity
{

	private final int ARM_RADIUS = 500;
	private final int ARM_TIME = 2000;
	private int openingElapsed;
	private ServerEntity target;

	
	public ServerTurret(UUID uuid, Point location, int width, int height, int layer, String templateClassString)
	{
		super(uuid, location, width, height, layer, templateClassString);
		super.currentState = Turret.State.SUPPRESSED;
		openingElapsed = 0;

	}

	@Override
	public void update(int delta)
	{
		direction = getTargetDirection();
		armTurret(delta);
	}

	@Override
	public void collideWith(Entity entity)
	{
		
	}

	@Override
	public void observe(EntityCollection<ServerEntity> entities)
	{
	
		boolean visibleTarget = false;
		
		for(ServerEntity entity: entities.getEntities())
		{
			
			if(entity instanceof ServerPlayer)
			{
				double distance = MathHelper.distance(this.location, entity.getLocation());
				
				if( distance < ARM_RADIUS)
				{
					visibleTarget = true;
					
					if(target == null)
					{
						setTarget(entity);
					}
					else if (distance < getTargetDistance())
					{
						setTarget(entity);
					}
				}
			}
		}
		if(!visibleTarget) removeTarget();
	}
	private void armTurret(int delta)
	{
		if(target != null)
		{
			openingElapsed += delta;
		}
		else
		{
			openingElapsed = 0;
		}
		if(openingElapsed >= ARM_TIME)
		{
			openingElapsed = 0;
			currentState = Turret.State.ARMED;
		}
	}
	private void setTarget(ServerEntity entity)
	{
		target = entity;
		if(currentState != Turret.State.ARMED)
		{
			currentState = Turret.State.OPENING;
		}
	}
	
	private void removeTarget()
	{
		if(target != null)
		{	
			currentState = Turret.State.CLOSING;
			target = null;
		}
	}
	
	private double getTargetDistance()
	{
		if(target !=null)
		{
			return MathHelper.distance(this.location, target.getLocation());
		}
		else return -1.0;
	}
	
	private Vector2f getTargetDirection()
	{
		if(target!=null)
		{
			return MathHelper.angleAsNormalVector(this.location, target.getLocation());
		}
		else
		{
			return new Vector2f();
		}
	}
	
	
}
