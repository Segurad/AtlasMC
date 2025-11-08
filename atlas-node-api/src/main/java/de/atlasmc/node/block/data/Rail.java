package de.atlasmc.node.block.data;

import java.util.Set;

import de.atlasmc.util.enums.EnumName;

public interface Rail extends Waterlogged {
	
	Shape getShape();
	
	void setShape(Shape shape);
	
	Set<Shape> getShapes();
	
	public static enum Shape implements EnumName {
		
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

		private String name;
		
		private Shape() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
	}

}
