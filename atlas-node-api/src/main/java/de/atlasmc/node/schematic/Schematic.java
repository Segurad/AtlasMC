package de.atlasmc.node.schematic;

import java.util.List;

import de.atlasmc.node.Location;

public interface Schematic {
	
	public SchematicSection getSection(int x, int y, int z);
	
	public int getHight();
	
	public int getLengthX();
	
	public int getLengthZ();

	public Location getOffset();
	
	public Location getOffset(Location loc);
	
	public void setOffset(Location loc);
	
	/**
	 * Returns a List containing all Schematics linked in this Schematic
	 * @return list of schematic accesses
	 */
	public List<SchematicAccess> getSchematics();
	
	public SchematicAccess addSchematic(Schematic schematic, int offX, int offY, int offZ, float rotation, boolean visible);

}
