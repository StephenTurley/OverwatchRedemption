/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package overwatch.entities.turret;

import core.entity.Entity;
import core.entity.ServerEntity;
import java.util.UUID;
import org.lwjgl.util.Point;

/**
 *
 * @author stephen
 */
public class ServerTurret extends ServerEntity
{

	public ServerTurret(UUID uuid, Point location, int width, int height, int layer, String templateClassString)
	{
		super(uuid, location, width, height, layer, templateClassString);
	}

	@Override
	public void update(int delta)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void collideWith(Entity entity)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
