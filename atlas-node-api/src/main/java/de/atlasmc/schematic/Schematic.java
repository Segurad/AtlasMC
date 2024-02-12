package de.atlasmc.schematic;

import java.util.List;

import de.atlasmc.SimpleLocation;

public interface Schematic {
	
	public SchematicSection getSection(int x, int y, int z);
	
	public int getHight();
	
	public int getLengthX();
	
	public int getLengthZ();

	public SimpleLocation getOffset();
	
	public SimpleLocation getOffset(SimpleLocation loc);
	
	public void setOffset(SimpleLocation loc);
	
	/**
	 * Returns a List containing all Schematics linked in this Schematic
	 * @return list of schematic accesses
	 */
	public List<SchematicAccess> getSchematics();
	
	public SchematicAccess addSchematic(Schematic schematic, int offX, int offY, int offZ, float rotation, boolean visible);

}
