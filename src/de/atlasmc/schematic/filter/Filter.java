package de.atlasmc.schematic.filter;

import de.atlasmc.schematic.SchematicObject;

public interface Filter {
	public SchematicObject[][][] apply(SchematicObject[][][] objects);
}