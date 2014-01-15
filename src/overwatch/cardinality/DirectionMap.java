package overwatch.cardinality;

public class DirectionMap {
	
	public static enum Direction{
		N	( Math.PI / 3, 2 * Math.PI / 3),
		S 	(4 * Math.PI / 3, 5 * Math.PI / 3),
		E 	(11 * Math.PI / 6, Math.PI / 6),
		W	(5 * Math.PI /6, 7 * Math.PI / 6),
		NE	(Math.PI / 6, Math.PI / 4),
		NW	(2 * Math.PI / 3, 5 * Math.PI / 6),
		SE	(5 * Math.PI / 3, 11 * Math.PI / 6),
		SW	(7 * Math.PI / 6, 4 * Math.PI / 3);
		
		private final double minRad;
		private final double maxRad;
		
		
		Direction(Double minRad, Double maxRad)
		{
			this.minRad = minRad;
			this.maxRad = maxRad;
		}
	}

}
