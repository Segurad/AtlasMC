package de.atlascore.world;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.entity.Entity;
import de.atlasmc.util.MathUtil;
import de.atlasmc.util.VariableValueArray;
import de.atlasmc.util.ViewerSet;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.world.Biome;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkSection;
import de.atlasmc.world.ChunkStatus;
import de.atlasmc.world.ChunkViewer;
import de.atlasmc.world.Dimension;
import de.atlasmc.world.World;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class CoreChunk implements Chunk {
	
	private static final BiConsumer<Chunk, ChunkViewer>
		VIEWER_ADD_FUNCTION = (holder, viewer) -> {
			viewer.added(holder);
		},
		VIEWER_REMOVE_FUNCTION = (holder, viewer) -> {
			viewer.removed(holder);
		};
	
	private final ChunkSection[] sections;
	private Int2ObjectMap<TileEntity> tiles;
	private final ViewerSet<Chunk, ChunkViewer> viewers;
	private final World world;
	private final int x;
	private final int z;
	private byte offsetY;
	private final VariableValueArray heightmap;
	private boolean loaded;
	
	private ChunkStatus status;
	
	public CoreChunk(World world, int x, int z) {
		this.x = x;
		this.z = z;
		viewers = new ViewerSet<>(this, VIEWER_ADD_FUNCTION, VIEWER_REMOVE_FUNCTION);
		Dimension dim = world.getDimension();
		int effectiveHeight = dim.getHeight() - dim.getMinY();
		this.offsetY = (byte) ((0 - dim.getMinY()) / 16);
		sections = new ChunkSection[effectiveHeight / 16];
		heightmap = new VariableValueArray(256, MathUtil.getRequiredBitCount(effectiveHeight));
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
		return sections[getSectionIndex(height)] != null;
	}
	
	protected int getSectionIndex(int height) {
		return MathUtil.floor(height / 16 + offsetY);
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
		return heightmap;
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
		if (tiles == null)
			return null;
		TileEntity tile = tiles.get(getTileIndex(x, y, z));
		return tile != null ? tile.clone() : tile;
	}
	
	@Override
	public TileEntity getTileEntityUnsafe(int x, int y, int z) {
		if (tiles == null)
			return null;
		return tiles.get(getTileIndex(x, y, z));
	}
	
	@Override
	public TileEntity setTileEntity(TileEntity tile, int x, int y, int z) {
		if (tile != null) {
			tile = tile.clone();
			tile.setLocation(this, x, y, z);
		}
		return internalSetTileUnsafe(tile, x, y, z);
	}
	
	@Override
	public TileEntity setTileEntity(Material material, int x, int y, int z) {
		TileEntity tile = null;
		if (material != null) {
			tile = material.createTileEntity();
			tile.setLocation(this, x, y, z);
		}
		return internalSetTileUnsafe(tile, x, y, z);
	}
	
	private TileEntity internalSetTileUnsafe(TileEntity tile, int x, int y, int z) {
		Int2ObjectMap<TileEntity> map = this.tiles;
		if (map == null) {
			if (tile == null)
				return null;
			map = tiles = new Int2ObjectOpenHashMap<>();
		}
		if (tile == null)
			return map.remove(getTileIndex(x, y, z));
		return map.put(getTileIndex(x, y, z), tile);
	}
	
	protected int getTileIndex(int x, int y, int z) {
		int pos = x & 0xF;
		pos = pos << 8 | (y & 0xF);
		pos = pos << 8 | (z & 0xF);
		return pos;
	}
	
	protected int getHeigtMapIndex(int x, int z) {
		return (z << 4) + x;
	}

	@Override
	public int getHighestBlockYAt(int x, int z) {
		return heightmap.get(getHeigtMapIndex(x, z)) + offsetY * 16;
	}

	@Override
	public Collection<Entity> getEntities() {
		return world.getEntityTracker().getEntities(x, z);
	}

	@Override
	public <C extends Collection<Entity>> C getEntities(C entities) {
		return world.getEntityTracker().getEntities(x, z, entities);
	}

	@Override
	public <T extends Entity> Collection<T> getEntitiesByClass(Class<T> clazz) {
		return world.getEntityTracker().getEntitesByClasses(x, z, clazz);
	}

	@Override
	public <T extends Entity, C extends Collection<T>> C getEntitiesByClass(Class<T> clazz, C entities) {
		return world.getEntityTracker().getEntitesByClasses(x, z, clazz, entities);
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

	@Override
	public Collection<Entity> getEntitiesByClasses(@SuppressWarnings("unchecked") Class<? extends Entity>... classes) {
		return world.getEntityTracker().getEntitesByClasses(x, z, classes);
	}

	@Override
	public <C extends Collection<Entity>> C getEntitiesByClasses(C entities, @SuppressWarnings("unchecked") Class<? extends Entity>... classes) {
		return world.getEntityTracker().getEntitiesByClasses(x, z, entities, classes);
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
	public void addListener(ChunkViewer listener) {
		viewers.add(listener);
	}

	@Override
	public void removeListener(ChunkViewer listener) {
		viewers.remove(listener);
	}

	@Override
	public void updateBlock(int x, int y, int z) {
		int state = getBlockState(x, y, z);
		for (ChunkViewer listener : viewers) {
			listener.updateBlock(this, state, x, y, z);
		}
	}

	@Override
	public int getBlockState(int x, int y, int z) {
		return getBlockDataAtUnsafe(x, y, z).getStateID();
	}

	@Override
	public ViewerSet<Chunk, ChunkViewer> getViewers() {
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
		return world.getEntityTracker().getEntity(x, z, entityID);
	}

	@Override
	public ChunkSection getSectionByIndex(int index) {
		int sIndex = index + offsetY;
		ChunkSection section = sections[sIndex];
		if (section == null) {
			section = new CoreChunkSection();
			sections[sIndex] = section;
		}
		return section;
	}

	@Override
	public boolean hasSectionIndex(int index) {
		int sIndex = index + offsetY;
		return sections[sIndex] != null;
	}

}
