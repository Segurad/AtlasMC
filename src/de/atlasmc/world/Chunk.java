package de.atlasmc.world;

import java.util.List;
import java.util.Set;

import de.atlasmc.block.Block;
import de.atlasmc.block.tile.TileEntity;

public interface Chunk {

	/**
	 * 
	 * @return a set of {@link ChunkSection}s ordered from the highest to the lowest
	 */
	public Set<ChunkSection> getSections();
	public ChunkSection getSection(int hight);
	public Block getBlockAt(int x, int y, int z);
	public World getWorld();
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
	
}
