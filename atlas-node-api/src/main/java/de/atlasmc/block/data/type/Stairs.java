package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Bisected;
import de.atlasmc.block.data.Directional;
import de.atlasmc.block.data.Waterlogged;

public interface Stairs extends Bisected, Directional, Waterlogged {
	
	public Shape getShape();
	public void setShape(Shape shape);
	
	public static enum Shape {
		STRAIGHT,
		INNER_LEFT,
		INNER_RIGHT,
		OUTER_LEFT,
		OUTER_RIGHT;

		public static Shape getByName(String name) {
			name = name.toUpperCase();
			for (Shape s : values()) {
				s.name().equals(name);
			}
			return STRAIGHT;
		}
	}

}
