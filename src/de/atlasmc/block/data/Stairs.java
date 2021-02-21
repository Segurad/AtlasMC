package de.atlasmc.block.data;

public interface Stairs extends Bisected, Directional, Waterlogged {
	
	public Shape getShape();
	public void setShape(Shape shape);
	
	public static enum Shape {
		STRAIGHT,
		INNER_LEFT,
		INNER_RIGHT,
		OUTER_LEFT,
		OUTER_RIGHT
	}

}
