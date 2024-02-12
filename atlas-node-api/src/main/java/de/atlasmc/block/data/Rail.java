package de.atlasmc.block.data;

import java.util.Set;

public interface Rail extends Waterlogged {
	
	public Shape getShape();
	public void setShape(Shape shape);
	public Set<Shape> getShapes();
	
	public static enum Shape {
		NORTH_SOUTH,
		EAST_WEST,
		ASCENDING_EAST,
		ASCENDING_WEST,
		ASCENDING_NORTH,
		ASCENTING_SOUTH,
		SOUT_EAST,
		SOUTH_WEST,
		NORTH_WEST,
		NORTH_EAST;

		/**
		 * Returns the Shape represented by the name or {@link #NORTH_SOUTH} if no matching Shape has been found
		 * @param name the name of the Shape
		 * @return the Shape or {@link #NORTH_SOUTH}
		 */
		public static Shape getByName(String name) {
			name = name.toUpperCase();
			for (Shape s : values()) {
				if (s.name().equals(name)) return s;
			}
			return NORTH_SOUTH;
		}
	}

}
