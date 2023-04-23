package de.atlascore.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.entity.Entity;
import de.atlasmc.util.VariableValueArray;
import de.atlasmc.util.ViewerSet;
import de.atlasmc.util.map.Int2ObjectHashMap;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkListener;
import de.atlasmc.world.ChunkSection;
import de.atlasmc.world.ChunkStatus;
import de.atlasmc.world.EnumBiome;
import de.atlasmc.world.World;

public class CoreChunk implements Chunk {
	
	private static final BiConsumer<Chunk, ChunkListener>
		VIEWER_ADD_FUNCTION = (holder, viewer) -> {
			viewer.added(holder);
		},
		VIEWER_REMOVE_FUNCTION = (holder, viewer) -> {
			viewer.removed(holder);
		};
	
	private final ChunkSection[] sections;
	private List<Entity> entities;
	private Int2ObjectHashMap<TileEntity> tiles;
	private final ViewerSet<Chunk, ChunkListener> viewers;
	private final short[] biomes;
	private final World world;
	private final int x;
	private final int z;
	private final VariableValueArray hightmap;
	private boolean loaded;
	
	private ChunkStatus status;
	
	public CoreChunk(World world, int x, int z) {
		this.x = x;
		this.z = z;
		sections = new ChunkSection[16];
		entities = new ArrayList<>();
		viewers = new ViewerSet<>(this, VIEWER_ADD_FUNCTION, VIEWER_REMOVE_FUNCTION);
		biomes = new short[1024];
		hightmap = new VariableValueArray(256, 9);
		this.world = world;
	}

	@Override
	public List<ChunkSection> getSections() {
		return List.of(sections);
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
		return loaded;
	}
	
	@Override
	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	@Override
	public VariableValueArray getHightMap() {
		return hightmap;
	}

	@Override
	public short[] getBiomes() {
		return biomes;
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
	public Collection<TileEntity> getTileEntities() {
		if (tiles == null)
			return List.of();
		return tiles.values();
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
		return getEntitiesByClass(new ArrayList<>(), clazz);
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
	public BlockData getBlockDataAtUnsafe(int x, int y, int z) {
		ChunkSection section = getSection(y);
		if (section == null) return null;
		return section.getBlockDataUnsafe(x, y, z);
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
		return getEntitiesByClasses(new ArrayList<>(), classes);
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
	public ChunkStatus getStatus() {
		return status;
	}

	@Override
	public String getStatusInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addListener(ChunkListener listener) {
		viewers.add(listener);
	}

	@Override
	public void removeListener(ChunkListener listener) {
		viewers.remove(listener);
	}

	@Override
	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	@Override
	public void removeEntity(Entity entity) {
		entities.remove(entity);
	}

	@Override
	public void updateBlock(int x, int y, int z) {
		int state = getBlockState(x, y, z);
		for (ChunkListener listener : viewers) {
			listener.updateBlock(this, state, x, y, z);
		}
	}

	@Override
	public int getBlockState(int x, int y, int z) {
		return getBlockDataAtUnsafe(x, y, z).getStateID();
	}

	@Override
	public ViewerSet<Chunk, ChunkListener> getViewers() {
		return viewers;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getZ() {
		return z;
	}

}
