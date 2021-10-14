package de.atlasmc.schematic;

import de.atlasmc.block.data.BlockData;

/**
 * Represents a 16x16x16 storage-cube of a Schematic
 */
public interface SchematicSection {
	
	public short[] getMappings();
	public short[] getMappings(short[] buffer);
	public short[] getMappings(short[] buffer, int offset);
	public void setMappings(short[] mappings);
	
	/**
	 * 
	 * @param x 0-15
	 * @param y 0-15
	 * @param z 0-15
	 * @return the value
	 */
	public short getValue(int x, int y, int z);
	
	/**
	 * 
	 * @param value;
	 * @param x 0-15
	 * @param y 0-15
	 * @param z 0-15
	 * @return the previous value
	 */
	public short setValue(short value, int x, int y, int z);
	public void setBlock(BlockData data, int x, int y, int z);
	public BlockData getBlock(int x, int y, int z);
	public void set(BlockData data);
	

}
