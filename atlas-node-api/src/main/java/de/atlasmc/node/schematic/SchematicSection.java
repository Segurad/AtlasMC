package de.atlasmc.node.schematic;

import de.atlasmc.node.block.data.BlockData;

/**
 * Represents a 16x16x16 storage-cube of a Schematic
 */
public interface SchematicSection {
	
	short[] getMappings();
	
	short[] getMappings(short[] buffer);
	
	short[] getMappings(short[] buffer, int offset);
	
	void setMappings(short[] mappings);
	
	/**
	 * 
	 * @param x 0-15
	 * @param y 0-15
	 * @param z 0-15
	 * @return the value
	 */
	short getValue(int x, int y, int z);
	
	/**
	 * 
	 * @param value;
	 * @param x 0-15
	 * @param y 0-15
	 * @param z 0-15
	 * @return the previous value
	 */
	short setValue(short value, int x, int y, int z);
	
	void setBlock(BlockData data, int x, int y, int z);
	
	BlockData getBlock(int x, int y, int z);
	
	void set(BlockData data);

}
