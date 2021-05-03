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
import de.atlasmc.world.ChunkSection;
import de.atlasmc.world.EnumBiome;
import de.atlasmc.world.World;

public class CoreChunk implements Chunk {
	
	private final ChunkSection[] sections;
	private final List<Entity> entities;
	private final int[] biomes;
	
	public CoreChunk(World world) {
		sections = new ChunkSection[16];
		entities = new ArrayList<Entity>();
		biomes = new int[1024];
	}

	@Override
	public Set<ChunkSection> getSections() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChunkSection getSection(int hight) {
		ChunkSection section = sections[(hight & 0xFF) >>4];
		if (section == null) {
			section = new CoreChunkSection();
			sections[(hight & 0xFF) >>4] = section;
		}
		return section;
	}
	
	@Override
	public boolean hasSection(int hight) {
		ChunkSection section = sections[(hight & 0xFF) >>4];
		return section != null;
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
		return Arrays.copyOf(biomes, biomes.length);
	}

	@Override
	public EnumBiome getBiome(int x, int y, int z) {
		int id = biomes[(((y & 0xF) >> 2) & 63) << 4 | (((z & 0xFF) >> 2) & 3) << 2 | (((x & 0xF) >> 2) & 3)];
		return EnumBiome.getByID(id);
	}

	@Override
	public void setBiome(EnumBiome biome, int x, int y, int z) {
		biomes[(((y & 0xF) >> 2) & 63) << 4 | (((z & 0xFF) >> 2) & 3) << 2 | (((x & 0xF) >> 2) & 3)] = biome.getID();
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
	public List<Entity> getEntites() {
		return entities;
	}

	@Override
	public List<Entity> getEntites(List<Entity> entities) {
		entities.addAll(this.entities);
		return entities;
	}

	@Override
	public <T extends Entity> List<T> getEntitesByClass(Class<T> clazz) {
		return getEntitesByClass(new ArrayList<T>(), clazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity> List<T> getEntitesByClass(List<T> entities, Class<T> clazz) {
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
		if (section == null) {
			
		};
		
	}

	@Override
	public List<Entity> getEntitesByClasses(Class<? extends Entity>[] classes) {
		return getEntitesByClasses(new ArrayList<Entity>(), classes);
	}

	@Override
	public List<Entity> getEntitesByClasses(List<Entity> entities, Class<? extends Entity>[] classes) {
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

}
