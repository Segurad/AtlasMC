package de.atlasmc.node.block;

import de.atlasmc.node.WorldLocation;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.tile.TileEntity;
import de.atlasmc.node.world.Biome;
import de.atlasmc.node.world.Chunk;
import de.atlasmc.node.world.ChunkSection;
import de.atlasmc.node.world.World;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.annotation.UnsafeAPI;

public interface Block {

	@NotNull
	BlockType getType();
	
	void setBlockData(BlockData data);
	
	void setType(BlockType material);
	
	/**
	 * Returns a copy of the {@link BlockData} at the Blocks position
	 * @return BlockData
	 */
	@NotNull
	BlockData getBlockData();
	
	/**
	 * Returns the {@link BlockData} at the Block position<br>
	 * The returned BlockData is <b>NOT</b> a copy and changes will modify the palette of the {@link ChunkSection}
	 * @return BlockData
	 */
	@UnsafeAPI
	@NotNull
	BlockData getBlockDataUnsafe();
	
	@Nullable
	TileEntity getTileEntity();
	
	@UnsafeAPI
	@Nullable
	TileEntity getTileEntityUnsafe();
	
	boolean hasTileEntity();
	
	int getY();
	
	int getX();
	
	int getZ();
	
	@NotNull
	Chunk getChunk();
	
	@NotNull
	Biome getBiome();
	
	@NotNull
	World getWorld();

	/**
	 * Returns a copy of the Blocks location
	 * @return location
	 */
	@NotNull
	WorldLocation getLocation();
	
	@NotNull
	WorldLocation getLocation(WorldLocation loc);
	
}
