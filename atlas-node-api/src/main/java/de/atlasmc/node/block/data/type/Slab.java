package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Waterlogged;
import de.atlasmc.util.enums.EnumName;

public interface Slab extends Waterlogged {
	
	Type getSlabType();
	
	void setSlabType(Type tpye);
	
	public static enum Type implements EnumName {
		
		TOP,
		BOTTOM,
		DOUBLE;

		private String name;
		
		private Type() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
	}
}
