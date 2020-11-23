package de.atlasmc.schematic;

import de.atlasmc.Location;

public interface SchematicObject {

	public void place(Location loc);
	public boolean isAir();
}
