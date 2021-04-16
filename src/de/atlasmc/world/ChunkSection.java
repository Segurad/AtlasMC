package de.atlasmc.world;

import java.util.List;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.tile.TileEntity;

public interface ChunkSection {
	
	public short[] getMappings();
	public default short[] getMappings(short[] buffer) {
		return getMappings(buffer, 0);
	}
	public short[] getMappings(short[] buffer, int offset);
	public void setMappings(short[] mappings);
	public short getValue(int x, int y, int z);
	public short setValue(short value, int x, int y, int z);
	public List<BlockData> getPallet();
	public List<TileEntity> getTileEntities();
	public Chunk getChunk();

}
