package de.atlasmc.world;

import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.entity.Entity;
import de.atlasmc.tick.Tickable;
import de.atlasmc.util.annotation.NotNull;

/**
 * A Chunk of a World
 */
public interface Chunk extends Tickable {

	/**
	 * Returns a list of all section in this chunk.<br>
	 * Sections that do not exist are null.
	 * @return a list of sections 
	 */
	public List<ChunkSection> getSections();
	
	/**
	 * Returns the ChunkSection at the given height<br>
	 * If there is no section at this height a new one will be created
	 * @param height between 0 and 256
	 * @return the chunk section at this height
	 */
	@NotNull
	public ChunkSection getSection(int height);
	
	public boolean hasSection(int height);
	
	public boolean isLoaded();
	
	/**
	 * Returns the World of this Chunk
	 * @return the World
	 */
	public World getWorld();
	
	/**
	 * 
	 * @return a copy of the chunks hightmap
	 */
	public long[] getHightMap();
	
	/**
	 * 
	 * @return a copy of the chunks biomes
	 */
	public short[] getBiomes();
	
	/**
	 * Returns the Biome at
	 * @param x world coordinate
	 * @param y world coordinate
	 * @param z world coordinate
	 * @return the Biome
	 */
	public EnumBiome getBiome(int x, int y, int z);
	
	public void setBiome(EnumBiome biome, int x, int y, int z);
	
	public List<TileEntity> getTileEntities();
	
	public int getHighestBlockYAt(int x, int z);
	
	public List<Entity> getEntities();
	
	public List<Entity> getEntities(List<Entity> entities);
	
	public <T extends Entity> List<T> getEntitiesByClass(Class<T> clazz);
	
	public <T extends Entity> List<T> getEntitiesByClass(List<T> entities, Class<T> clazz);
	
	/**
	 * Returns a copy of the BlockData at the position
	 * @param x
	 * @param y
	 * @param z
	 * @return a copy of the BlockData 
	 */
	public BlockData getBlockDataAt(int x, int y, int z);
	
	/**
	 * Returns the BlockData at the position <b>NOT</b> a copy<br>
	 * If this BlockData is modified all Blocks with this data in the section will be modified!
	 * @param x
	 * @param y
	 * @param z
	 * @return BlockData
	 */
	public BlockData getBlockDataAtUnsafe(int x, int y, int z);
	
	public Material getBlockType(int x, int y, int z);
	
	/**
	 * Sets the Material at the position<br>
	 * Does not update changes for clients
	 * @param material
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setBlockType(Material material, int x, int y, int z);
	
	/**
	 * Sets the BlockData at the position<br>
	 * Does not update changes for clients if you want so use {@link #sendUpdate(Chunk, int, int, int)} or use the {@link Block} interface
	 * @param data
	 * @param x world coordinate
	 * @param y world coordinate
	 * @param z world coordinate
	 */
	public void setBlockDataAt(BlockData data, int x, int y, int z);
	
	public List<Entity> getEntitiesByClasses(Class<? extends Entity>[] classes);
	
	public List<Entity> getEntitiesByClasses(List<Entity> entities, Class<? extends Entity>[] classes);
	
	/**
	 * @param x
	 * @param y
	 * @param z
	 * @see {@link World#sendUpdate(Chunk, int, int, int)}
	 */
	public void sendUpdate(int x, int y, int z);
	
	/**
	 * Returns the current status of this Chunk
	 * @return {@link ChunkStatus}
	 */
	public ChunkStatus getStatus();
	
	/**
	 * Returns a status info text for the current status e.g. the current generator info
	 * @return status text
	 */
	public String getStatusInfo();
	
	public void addListener(ChunkListener listener);
	
	public void removeListener(ChunkListener listener);
	
}
