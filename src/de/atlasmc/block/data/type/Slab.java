package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Waterlogged;

public interface Slab extends Waterlogged {
	
	public Type getType();
	public void setType(Type tpye);
	
	public static enum Type {
		TOP,
		BOTTOM,
		DOUBLE;

		public static Type getByName(String name) {
			name = name.toLowerCase();
			for (Type t : values()) {
				if (t.name().equals(name)) return t;
			}
			return BOTTOM;
		}
	}
}
