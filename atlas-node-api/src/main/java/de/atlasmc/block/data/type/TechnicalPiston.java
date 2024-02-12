package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Directional;

public interface TechnicalPiston extends Directional {
	
	public Type getType();
	public void setType(Type type);
	
	public static enum Type {
		NORMAL,
		STICKY;

		public static Type getByName(String name) {
			return STICKY.name().equalsIgnoreCase(name) ? STICKY : NORMAL;
		}
	}

}
