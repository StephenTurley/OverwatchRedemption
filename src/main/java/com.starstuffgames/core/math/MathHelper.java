package com.starstuffgames.core.math;

import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Vector2f;

public class MathHelper {
	
	public static double angleInDegrees(Point origin, Point target)
	{
		double deltaY = (double)(target.getY() - origin.getY());
		double deltaX = (double)(target.getX() - origin.getY());
		
		return Math.atan2(deltaY, deltaX) * (180 / Math.PI);
	}
	
	public static Vector2f angleAsNormalVector(Point origin, Point target)
	{
		Vector2f direction = new Vector2f(target.getX() - origin.getX(), target.getY() - origin.getY());
		direction.normalise();
		
		return direction;
	}
	
	public static double distance(Point a, Point b)
	{
		return Math.sqrt(
				Math.pow(a.getX() - b.getX(), 2) + 
				Math.pow(a.getY() - b.getY(), 2));
	}
	
}
