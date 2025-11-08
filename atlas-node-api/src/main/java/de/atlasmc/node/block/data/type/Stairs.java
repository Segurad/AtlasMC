package de.atlasmc.node.block.data.type;

import de.atlasmc.IDHolder;
import de.atlasmc.node.block.data.Bisected;
import de.atlasmc.node.block.data.Directional;
import de.atlasmc.node.block.data.Waterlogged;
import de.atlasmc.util.enums.EnumName;

public interface Stairs extends Bisected, Directional, Waterlogged {
	
	Shape getShape();
	
	void setShape(Shape shape);
	
	Stairs clone();
	
	public static enum Shape implements IDHolder, EnumName {

		STRAIGHT,
		INNER_LEFT,
		INNER_RIGHT,
		OUTER_LEFT,
		OUTER_RIGHT;

		private String name;
		
		private Shape() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
	}

}
