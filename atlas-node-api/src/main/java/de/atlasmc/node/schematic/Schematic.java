package de.atlasmc.node.schematic;

import java.util.List;

import org.joml.Vector3i;

import de.atlasmc.util.annotation.NotNull;

public interface Schematic {
	
	SchematicSection getSection(int x, int y, int z);
	
	int getHight();
	
	int getLengthX();
	
	int getLengthZ();

	@NotNull
	Vector3i getOffset();
	
	@NotNull
	Vector3i getOffset(Vector3i loc);
	
	void setOffset(Vector3i loc);
	
	/**
	 * Returns a List containing all Schematics linked in this Schematic
	 * @return list of schematic accesses
	 */
	List<SchematicAccess> getSchematics();
	
	SchematicAccess addSchematic(Schematic schematic, int offX, int offY, int offZ, float rotation, boolean visible);

}
