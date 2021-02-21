package de.atlasmc.block.data;

import java.util.Set;

public interface Rail extends BlockData {
	
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
		NORTH_EAST
	}

}
