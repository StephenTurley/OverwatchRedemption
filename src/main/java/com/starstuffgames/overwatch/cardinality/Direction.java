package com.starstuffgames.overwatch.cardinality;

import org.lwjgl.util.vector.Vector2f;

public enum Direction{
	 E , NE ,
	 N , NW ,
     W , SW ,
     S , SE ;
	 
	 
	 /**
	  * 
	  * @param directionIdx
	  * @return returns the direction enum starting from E and going counter clockwise to SE (e.g. E = 0, NE = 1, N = 2, ..
	  * 
	  */
	 public static Direction fromInteger(int directionIdx)
	 {
		 switch(directionIdx)
		 {
			 case 0:
				 return E;
			 case 1:
				 return NE;
			 case 2:
				 return N;
			 case 3:
				 return NW;
			 case 4:
				 return W;
			 case 5:
				 return SW;
			 case 6:
				 return S;
			 case 7:
				 return SE;
			 default:
				 throw new IndexOutOfBoundsException("direction index must be 0 - 7 inclusive");
				 
		 }
	 }
	 /**
	  * 
	  * @param vector 
	  * @return returns the cardinal direction of the vector
	  */
	 public static Direction fromVector2f(Vector2f vector)
	 {
		 float angle = (float)Math.atan2(-vector.getY(), vector.getX());
		 
		 return Direction.fromRadian(angle);
	 }
	 public static Direction fromRadian(float angle) {
		
		int octant = Math.round(8 * angle / (2 * (float)Math.PI) + 8) % 8;
		 
		return Direction.fromInteger(octant);
	 }
	 

}

