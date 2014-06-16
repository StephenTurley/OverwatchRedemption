package com.starstuffgames.core.math;

import java.awt.Point;

public class MathHelper {
	
	public static double angleInDegrees(Point origin, Point target)
	{
		double deltaY = (double)(target.y - origin.y);
		double deltaX = (double)(target.x - origin.x);
		
		return Math.atan2(deltaY, deltaX) * (180 / Math.PI);
	}
}
