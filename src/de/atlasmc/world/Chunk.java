package de.atlasmc.world;

import java.util.List;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.entity.Entity;
import de.atlasmc.tick.Tickable;

public interface Chunk extends Tickable {

	/**
	 * 
	 * @return a set of {@link ChunkSection}s ordered from the highest to the lowest
	 */
	public Set<ChunkSection> getSections();
	/**
	 * 
	 * @param height between 0 and 256
	 * @return the chunk section at this height
	 */
	public ChunkSection getSection(int height);
	public boolean hasSection(int height);
	public boolean isLoaded();
	public void load();
	/**
	 * 
	 * @return a copy of the chunks hightmap
	 */
	public long[] getHightMap();
	
	/**
	 * 
	 * @return a copy of the chunks biomes
	 */
	public int[] getBiomes();
	
	public EnumBiome getBiome(int x, int y, int z);
	public void setBiome(EnumBiome biome, int x, int y, int z);
	public List<TileEntity> getTileEntities();
	public int getHighestBlockYAt(int x, int z);
	public List<Entity> getEntites();
	public List<Entity> getEntites(List<Entity> entities);
	public <T extends Entity> List<T> getEntitesByClass(Class<T> clazz);
	public <T extends Entity> List<T> getEntitesByClass(List<T> entities, Class<T> clazz);
	public BlockData getBlockDataAt(int x, int y, int z);
	public Material getBlockType(int x, int y, int z);
	public void setBlockType(Material material, int x, int y, int z);
	public void setBlockDataAt(BlockData data, int x, int y, int z);
	public List<Entity> getEntitesByClasses(Class<? extends Entity>[] classes);
	public List<Entity> getEntitesByClasses(List<Entity> entities, Class<? extends Entity>[] classes);
	
}
