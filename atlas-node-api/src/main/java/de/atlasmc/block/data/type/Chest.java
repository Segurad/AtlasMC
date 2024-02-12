package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Directional;
import de.atlasmc.block.data.Waterlogged;

public interface Chest extends Directional, Waterlogged {
	
	public Type getType();
	public void setType(Type type);
	
	public static enum Type {
		SINGLE,
		LEFT,
		RIGHT;

		public static Type getByName(String name) {
			name = name.toUpperCase();
			for (Type t : values()) {
				if (t.name().equals(name)) return t;
			}
			return SINGLE;
		}
	}

}
