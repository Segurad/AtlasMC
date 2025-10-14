package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Directional;
import de.atlasmc.node.block.data.Waterlogged;
import de.atlasmc.util.EnumName;

public interface Chest extends Directional, Waterlogged {
	
	Type getChestType();
	
	void setChestType(Type type);
	
	public static enum Type implements EnumName {
		
		SINGLE,
		LEFT,
		RIGHT;

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
