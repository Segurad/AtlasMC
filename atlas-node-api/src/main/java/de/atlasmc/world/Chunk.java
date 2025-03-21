package de.atlasmc.world;

import java.util.Collection;

import de.atlasmc.block.Block;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.entity.Entity;
import de.atlasmc.tick.Tickable;
import de.atlasmc.util.VariableValueArray;
import de.atlasmc.util.ViewerSet;
import de.atlasmc.util.annotation.NotNull;

/**
 * A Chunk of a World
 */
public interface Chunk extends Tickable {

	/**
	 * Returns a array of ordered sections bottom to top.
	 * @return sections
	 */
	 ChunkSection[] getSections();
	
	/**
	 * Returns the ChunkSection at the given height.
	 * If there is no section at this height a new one will be created.
	 * @param height
	 * @return the chunk section at this height
	 */
	@NotNull
	ChunkSection getSection(int height);
	
	ChunkSection getSectionByIndex(int index);
	
	boolean hasSection(int height);
	
	boolean hasSectionIndex(int index);
	
	/**
	 * Returns if this Chunk is fully loaded or generated
	 * @return loaded
	 */
	boolean isLoaded();
	
	/**
	 * Sets this chunk as loaded. 
	 * Should only be used by the {@link ChunkGenerator} or {@link ChunkLoader} that handles the Chunk
	 * @param loaded
	 */
	void setLoaded(boolean loaded);
	
	/**
	 * Returns the World of this Chunk
	 * @return the World
	 */
	World getWorld();
	
	/**
	 * 
	 * @return hightmap
	 */
	VariableValueArray getHightMap();
	
	/**
	 * Returns the Biome at
	 * @param x in this chunk
	 * @param y in this chunk
	 * @param z in this chunk
	 * @return the Biome
	 */
	Biome getBiome(int x, int y, int z);
	
	void setBiome(Biome biome, int x, int y, int z);
	
	/**
	 * Returns all tiles of this chunk
	 * @return tiles
	 */
	Collection<TileEntity> getTileEntities();
	
	/**
	 * Returns a copy of the TileEntity at the given position or null if no tile is present
	 * @param x in this chunk
	 * @param y in this chunk
	 * @param z in this chunk
	 * @return tile or null
	 */
	TileEntity getTileEntity(int x, int y, int z);
	
	/**
	 * Returns the BlockData at the position <b>NOT</b> a copy<br> or null if no tile is present
	 * @param x in this chunk
	 * @param y in this chunk
	 * @param z in this chunk
	 * @return tile or null
	 */
	TileEntity getTileEntityUnsafe(int x, int y, int z);
	
	/**
	 * Sets a copy of the given TileEntity at the given position and returns the previous value
	 * @param tile
	 * @param x in this chunk
	 * @param y in this chunk
	 * @param z in this chunk
	 * @return tile or null
	 */
	TileEntity setTileEntity(TileEntity tile, int x, int y, int z);
	
	TileEntity setTileEntity(BlockType type, int x, int y, int z);
	
	int getHighestBlockYAt(int x, int z);
	
	Collection<Entity> getEntities();
	
	<C extends Collection<Entity>> C getEntities(C entities);
	
	<T extends Entity> Collection<T> getEntitiesByClass(Class<T> clazz);
	
	/**
	 * Returns all entities by the given class and stores them in the given list
	 * @param <T>
	 * @param <C>
	 * @param entities the collection to store the entities in
	 * @param clazz the class a entity must be a instance of
	 * @return the given list
	 */
	<T extends Entity, C extends Collection<T>> C getEntitiesByClass(Class<T> clazz, C entities);
	
	/**
	 * Returns a copy of the BlockData at the position or null if {@link #hasSection(int)} is false
	 * @param x in this chunk
	 * @param y in this chunk
	 * @param z in this chunk
	 * @return a copy of the BlockData 
	 */
	BlockData getBlockDataAt(int x, int y, int z);
	
	/**
	 * Returns the BlockData at the position <b>NOT</b> a copy<br> or null if {@link #hasSection(int)} is false
	 * If this BlockData is modified all Blocks with this data in the section will be modified!
	 * @param x in this chunk
	 * @param y in this chunk
	 * @param z in this chunk
	 * @return BlockData
	 */
	BlockData getBlockDataAtUnsafe(int x, int y, int z);
	
	/**
	 * Return the BlockState at the position
	 * @param x in this chunk
	 * @param y in this chunk
	 * @param z in this chunk
	 * @return BlockState
	 */
	int getBlockState(int x, int y, int z);
	
	BlockType getBlockType(int x, int y, int z);
	
	/**
	 * Sets the Material at the position<br>
	 * Does not update changes for clients
	 * @param type
	 * @param x in this chunk
	 * @param y in this chunk
	 * @param z in this chunk
	 */
	void setBlockType(BlockType type, int x, int y, int z);
	
	/**
	 * Sets the BlockData at the position<br>
	 * Does not update changes for clients if you want so use {@link #updateBlock(int, int, int)} or use the {@link Block} interface
	 * @param data
	 * @param x in this chunk
	 * @param y in this chunk
	 * @param z in this chunk
	 */
	void setBlockDataAt(BlockData data, int x, int y, int z);
	
	/**
	 * Returns the current status of this Chunk
	 * @return {@link ChunkStatus}
	 */
	ChunkStatus getStatus();
	
	void addListener(ChunkViewer listener);
	
	void removeListener(ChunkViewer listener);
	
	/**
	 * Sends a block update to all {@link ChunkViewer}s 
	 * @param x in this chunk
	 * @param y in this chunk
	 * @param z in this chunk
	 */
	void updateBlock(int x, int y, int z);
	
	ViewerSet<Chunk, ChunkViewer> getViewers();
	
	int getX();
	
	int getZ();

	boolean hasTileEntity(int x, int y, int z);

	Entity getEntity(int entityID);
	
}
