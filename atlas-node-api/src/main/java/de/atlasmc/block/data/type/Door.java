package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Bisected;
import de.atlasmc.block.data.Directional;
import de.atlasmc.block.data.Openable;
import de.atlasmc.block.data.Powerable;

public interface Door extends Bisected, Directional, Openable, Powerable {
	
	public Hinge getHinge();
	public void setHinge(Hinge hinge);
	
	public static enum Hinge {
		LEFT,
		RIGHT;

		public static Hinge getByName(String name) {
			if (LEFT.name().equalsIgnoreCase(name)) {
				return LEFT;
			} else if (RIGHT.name().equalsIgnoreCase(name)) {
				return RIGHT;
			}
			return null;
		}
	}

}
