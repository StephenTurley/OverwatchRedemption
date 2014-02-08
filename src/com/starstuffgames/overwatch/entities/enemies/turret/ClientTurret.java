/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.starstuffgames.overwatch.entities.enemies.turret;

import com.starstuffgames.core.entity.ClientEntity;
import com.starstuffgames.core.graphics.Camera;
import java.util.UUID;
import org.lwjgl.util.Point;

/**
 *
 * @author stephen
 */
public class ClientTurret extends ClientEntity
{

	public ClientTurret(UUID uuid, Point location, int width, int height, int layer, String templateClassString)
	{
		super(uuid, location, width, height, layer, templateClassString);
	}

	@Override
	public void update(int delta)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void draw(Camera camera)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void loadAssets()
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
