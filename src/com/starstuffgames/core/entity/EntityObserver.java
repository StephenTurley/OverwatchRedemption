/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.starstuffgames.core.entity;

import java.util.ArrayList;

/**
 *
 * @author stephen
 */
public interface EntityObserver
{
	public void update(int delta, ArrayList<Entity> entities);
}
