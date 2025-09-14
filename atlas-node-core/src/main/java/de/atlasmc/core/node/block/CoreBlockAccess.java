package de.atlasmc.core.node.block;

import de.atlasmc.node.Location;
import de.atlasmc.node.block.Block;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.tile.TileEntity;
import de.atlasmc.node.world.Biome;
import de.atlasmc.node.world.Chunk;
import de.atlasmc.node.world.World;

/**
 * Class for modifying and receiving information of a Block in a Chunk
 */
public class CoreBlockAccess implements Block {
	
	private final Location loc;
	private final Chunk chunk;
	
	public CoreBlockAccess(Location loc, Chunk chunk) {
		this.loc = loc;
		this.chunk = chunk;
	}
	
	public CoreBlockAccess(Location location) {
		this.loc = location;
		chunk = loc.getWorld().getChunk(loc);
	}

	@Override
	public BlockType getType() {
		return chunk.getBlockType(getX(), getY(), getZ());
	}

	@Override
	public void setBlockData(BlockData data) {
		this.chunk.setBlockDataAt(data, getX(), getY(), getZ());
	}

	@Override
	public void setType(BlockType type) {
		chunk.setBlockType(type, getX(), getY(), getZ());
	}

	@Override
	public BlockData getBlockData() {
		return chunk.getBlockDataAt(getX(), getY(), getZ());
	}
	
	@Override
	public BlockData getBlockDataUnsafe() {
		return chunk.getBlockDataAtUnsafe(getX(), getY(), getZ());
	}

	@Override
	public int getY() {
		return loc.getBlockY();
	}

	@Override
	public Biome getBiome() {
		return chunk.getBiome(getX(), getY(), getZ());
	}

	@Override
	public World getWorld() {
		return loc.getWorld();
	}

	@Override
	public int getX() {
		return loc.getBlockX();
	}

	@Override
	public int getZ() {
		return loc.getBlockZ();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof Block))
			return false;
		Block block = (Block) obj;
		if (block.getWorld() != getWorld() ||
			block.getX() != getX() ||
			block.getY() != getY() ||
			block.getZ() != getZ())
			return false;
		if (getType() != block.getType())
			return false;
		return getBlockData().equals(block.getBlockData());
	}

	@Override
	public Location getLocation() {
		return loc.clone();
	}

	@Override
	public Location getLocation(Location loc) {
		return this.loc.copyTo(loc);
	}

	@Override
	public TileEntity getTileEntity() {
		return chunk.getTileEntity(getX(), getY(), getZ());
	}

	@Override
	public TileEntity getTileEntityUnsafe() {
		return chunk.getTileEntityUnsafe(getX(), getY(), getZ());
	}

	@Override
	public boolean hasTileEntity() {
		return chunk.hasTileEntity(getX(), getY(), getZ());
	}

}
