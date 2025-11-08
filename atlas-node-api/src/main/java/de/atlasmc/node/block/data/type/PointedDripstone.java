package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Waterlogged;
import de.atlasmc.util.enums.EnumName;

public interface PointedDripstone extends Waterlogged {
	
	Thickness getThickness();
	
	void setThickness(Thickness thickness);
	
	VerticalDirection getDirection();
	
	void setDirection(VerticalDirection direction);
	
	PointedDripstone clone();
	
	public static enum Thickness implements EnumName {
		
		TIP_MERGE,
		TIP,
		FRUSTUM,
		MIDDLE,
		BASE;

		private final String name;
		
		private Thickness() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
	}
	
	public static enum VerticalDirection implements EnumName {
		
		UP,
		DOWN;
		
		private final String name;
		
		private VerticalDirection() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
	}

}
