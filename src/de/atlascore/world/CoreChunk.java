package de.atlascore.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.entity.Entity;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkListener;
import de.atlasmc.world.ChunkSection;
import de.atlasmc.world.ChunkStatus;
import de.atlasmc.world.EnumBiome;
import de.atlasmc.world.World;

public class CoreChunk implements Chunk {
	
	private final ChunkSection[] sections;
	private final List<Entity> entities;
	private final short[] biomes;
	private final World world;
	
	public CoreChunk(World world) {
		sections = new ChunkSection[16];
		entities = new ArrayList<Entity>();
		biomes = new short[1024];
		this.world = world;
	}

	@Override
	public Set<ChunkSection> getSections() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChunkSection getSection(int height) {
		final int sIndex = getSectionIndex(height);
		ChunkSection section = sections[sIndex];
		if (section == null) {
			section = new CoreChunkSection();
			sections[sIndex] = section;
		}
		return section;
	}
	
	@Override
	public boolean hasSection(int height) {
		ChunkSection section = sections[getSectionIndex(height)];
		return section != null;
	}
	
	protected int getSectionIndex(int height) {
		return (height & 0xFF) >>4;
	}

	@Override
	public boolean isLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long[] getHightMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public short[] getBiomes() {
		return Arrays.copyOf(biomes, biomes.length);
	}

	@Override
	public EnumBiome getBiome(int x, int y, int z) {
		int id = biomes[getBiomeIndex(x, y, z)];
		return EnumBiome.getByID(id);
	}

	@Override
	public void setBiome(EnumBiome biome, int x, int y, int z) {
		biomes[getBiomeIndex(x, y, z)] = (short) biome.getID();
	}
	
	/**
	 * This Method return the index of a Biome at the given coordinates
	 * @param x
	 * @param y
	 * @param z
	 * @return 
	 */
	protected int getBiomeIndex(int x, int y, int z) {
		return (((y & 0xF) >> 2) & 63) << 4 | (((z & 0xFF) >> 2) & 3) << 2 | (((x & 0xF) >> 2) & 3);
	}

	@Override
	public List<TileEntity> getTileEntities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHighestBlockYAt(int x, int z) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Entity> getEntities() {
		return entities;
	}

	@Override
	public List<Entity> getEntities(List<Entity> entities) {
		entities.addAll(this.entities);
		return entities;
	}

	@Override
	public <T extends Entity> List<T> getEntitiesByClass(Class<T> clazz) {
		return getEntitiesByClass(new ArrayList<T>(), clazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity> List<T> getEntitiesByClass(List<T> entities, Class<T> clazz) {
		for (Entity ent : this.entities) {
			if (clazz.isInstance(ent)) {
				entities.add((T) ent);
			}
		}
		return entities;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BlockData getBlockDataAt(int x, int y, int z) {
		ChunkSection section = getSection(y);
		if (section == null) return null;
		return section.getBlockData(x, y, z);
	}

	@Override
	public Material getBlockType(int x, int y, int z) {
		ChunkSection section = getSection(y);
		if (section == null) return Material.AIR;
		return section.getBlockType(x, y, z);
	}

	@Override
	public void setBlockType(Material material, int x, int y, int z) {
		setBlockDataAt(material.createBlockData(), x, y, z);
	}

	@Override
	public void setBlockDataAt(BlockData data, int x, int y, int z) {
		ChunkSection section = getSection(y);
		section.setBlockData(data, x, y, z);
	}

	@Override
	public List<Entity> getEntitiesByClasses(Class<? extends Entity>[] classes) {
		return getEntitiesByClasses(new ArrayList<Entity>(), classes);
	}

	@Override
	public List<Entity> getEntitiesByClasses(List<Entity> entities, Class<? extends Entity>[] classes) {
		for (Entity ent : this.entities) {
			for (Class<?> clazz : classes) {
				if (clazz.isInstance(ent)) {
					entities.add(ent);
					break;
				}
			}
		}
		return entities;
	}

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public void sendUpdate(int x, int y, int z) {
		world.sendUpdate(this, x, y, z);
	}

	@Override
	public ChunkStatus getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStatusInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addListener(ChunkListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListener(ChunkListener listener) {
		// TODO Auto-generated method stub
		
	}

}
