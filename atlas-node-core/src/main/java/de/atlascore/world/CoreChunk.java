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
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.map.Int2ObjectMap;
import de.atlasmc.world.Biome;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkListener;
import de.atlasmc.world.ChunkSection;
import de.atlasmc.world.ChunkStatus;
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
	private Int2ObjectMap<TileEntity> tiles;
	private final ViewerSet<Chunk, ChunkListener> viewers;
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
		hightmap = new VariableValueArray(256, 9);
		this.world = world;
	}

	@Override
	public ChunkSection[] getSections() {
		return sections;
	}

	@Override
	public @NotNull ChunkSection getSection(int height) {
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
	public Biome getBiome(int x, int y, int z) {
		return getSection(y).getBiome(x, y, z);
	}

	@Override
	public void setBiome(Biome biome, int x, int y, int z) {
		getSection(y).setBiome(biome, x, y, z);
	}

	@Override
	public Collection<TileEntity> getTileEntities() {
		if (tiles == null)
			return List.of();
		return tiles.values();
	}
	
	@Override
	public boolean hasTileEntity(int x, int y, int z) {
		return getTileEntityUnsafe(x, y, z) != null;
	}
	
	@Override
	public TileEntity getTileEntity(int x, int y, int z) {
		TileEntity tile = tiles.get(getTileIndex(x, y, z));
		return tile != null ? tile.clone() : tile;
	}
	
	@Override
	public TileEntity getTileEntityUnsafe(int x, int y, int z) {
		return tiles.get(getTileIndex(x, y, z));
	}
	
	@Override
	public TileEntity setTileEntity(TileEntity tile, int x, int y, int z) {
		if (tile != null) {
			tile = tile.clone();
			tile.setLocation(this, x, y, z);
		}
		return tiles.put(getTileIndex(x, y, z), tile);
	}
	
	@Override
	public TileEntity setTileEntity(Material material, int x, int y, int z) {
		TileEntity tile = null;
		if (material != null) {
			tile = material.createTileEntity();
			tile.setLocation(this, x, y, z);
		}
		return tiles.put(getTileIndex(x, y, z), tile);
	}
	
	protected int getTileIndex(int x, int y, int z) {
		int pos = x & 0xF;
		pos = pos << 8 | (y & 0xF);
		pos = pos << 8 | (z & 0xF);
		return pos;
	}

	@Override
	public int getHighestBlockYAt(int x, int z) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<Entity> getEntities() {
		return entities;
	}

	@Override
	public <C extends Collection<Entity>> C getEntities(C entities) {
		entities.addAll(this.entities);
		return entities;
	}

	@Override
	public <T extends Entity> Collection<T> getEntitiesByClass(Class<T> clazz) {
		return getEntitiesByClass(new ArrayList<>(), clazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity, C extends Collection<T>> C getEntitiesByClass(C entities, Class<T> clazz) {
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
		if (section == null) 
			return null;
		return section.getBlockData(x, y, z);
	}
	
	@Override
	public BlockData getBlockDataAtUnsafe(int x, int y, int z) {
		ChunkSection section = getSection(y);
		if (section == null) 
			return null;
		return section.getBlockDataUnsafe(x, y, z);
	}

	@Override
	public Material getBlockType(int x, int y, int z) {
		ChunkSection section = getSection(y);
		if (section == null) 
			return Material.AIR;
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

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Entity> getEntitiesByClasses(Class<? extends Entity>... classes) {
		return getEntitiesByClasses(new ArrayList<>(), classes);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <C extends Collection<Entity>> C getEntitiesByClasses(C entities, Class<? extends Entity>... classes) {
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

	@Override
	public Entity getEntity(int entityID) {
		// TODO Auto-generated method stub
		return null;
	}

}
