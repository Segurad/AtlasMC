package de.atlascore.world;

import java.util.List;
import java.util.Set;

import de.atlasmc.block.Block;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkSection;
import de.atlasmc.world.EnumBiome;
import de.atlasmc.world.World;

public class CoreChunk implements Chunk {
	
	private final ChunkSection[] sections;
	
	public CoreChunk() {
		sections = new ChunkSection[16];
	}

	@Override
	public Set<ChunkSection> getSections() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChunkSection getSection(int hight) {
		return sections[(hight & 0xFF) >>4];
	}

	@Override
	public Block getBlockAt(int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public World getWorld() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long[] getHightMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getBiomes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnumBiome getBiome(int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBiome(EnumBiome biome, int x, int y, int z) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<TileEntity> getTileEntities() {
		// TODO Auto-generated method stub
		return null;
	}

}
