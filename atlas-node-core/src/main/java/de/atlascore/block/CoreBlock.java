package de.atlascore.block;

import de.atlasmc.Location;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.world.Chunk;

/**
 * Block information storage but does not modify a chunk
 */
public class CoreBlock extends CoreBlockAccess {
	
	private BlockData data;
	
	public CoreBlock(Location loc, BlockData data) {
		this(loc, loc.getWorld().getChunk(loc), data);
	}
	
	public CoreBlock(Location loc, Chunk chunk, BlockData data) {
		super(loc, chunk);
		if (data == null)
			throw new IllegalArgumentException("BlockData can not be null!");
		this.data = data;
	}
	
	public CoreBlock(Location loc, BlockType type) {
		this(loc, type.createBlockData());
	}

	@Override
	public BlockType getType() {
		return data.getType();
	}
	
	@Override
	public BlockData getBlockData() {
		return data;
	}
	
	@Override
	public BlockData getBlockDataUnsafe() {
		return data;
	}
	
	@Override
	public void setType(BlockType type) {
		if (data.getType() == type) 
			return;
		data = type.createBlockData();
	}
	
	@Override
	public void setBlockData(BlockData data) {
		this.data = data;
	}

}
