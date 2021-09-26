package de.atlascore.block;

import de.atlasmc.Location;
import de.atlasmc.Material;
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
	}
	
	public CoreBlock(Location loc, Material type) {
		this(loc, type.createBlockData());
	}

	@Override
	public Material getType() {
		return data.getMaterial();
	}
	
	@Override
	public BlockData getBlockData() {
		return data;
	}
	
	@Override
	public void setType(Material material) {
		if (data.getMaterial() == material) return;
		if (!material.isBlock()) throw new IllegalArgumentException("Material must be a Block: " + material.getName());
		data = material.createBlockData();
	}
	
	@Override
	public void setBlockData(BlockData data) {
		this.data = data;
	}

}
