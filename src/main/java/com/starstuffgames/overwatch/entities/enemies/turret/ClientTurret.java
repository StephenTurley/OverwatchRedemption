/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.starstuffgames.overwatch.entities.enemies.turret;

import com.starstuffgames.core.Debug;
import com.starstuffgames.core.Game;
import com.starstuffgames.core.entity.ClientEntity;
import com.starstuffgames.core.graphics.Camera;
import com.starstuffgames.core.graphics.cardinality.Direction;

import java.util.UUID;

import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;

/**
 *
 * @author stephen
 */
public class ClientTurret extends ClientEntity
{

    private TurretAssets assets;
    
    public ClientTurret(UUID uuid, Point location, int width, int height, int layer, String templateClassString)
    {
        super(uuid, location, width, height, layer, templateClassString);
        currentState = Turret.State.SUPPRESSED;
    }

    @Override
    public void update(int delta)
    {
        if(assets!= null)
        {
            assets.update(delta);

            if(!(super.direction.x == 0 && super.direction.y == 0))
            {
                    assets.setDirection(Direction.fromVector2f(super.direction));
            }
            assets.setState(super.currentState);
        }
    }

    @Override
    public void draw(Camera camera)
    {
        if (assets != null && camera.isVisible(new Rectangle(location.getX(), location.getY(), width, height)))
        {
            assets.draw(camera, this.location);			
        }
    }

    @Override
    public void loadAssets()
    {
        try
        {
            assets = new TurretAssets(super.currentState);
        }
        catch (Exception e)
        {
            Debug.Trace("Turret assets failed to load");
            Game.exit(1);
        }
    }
	
}
