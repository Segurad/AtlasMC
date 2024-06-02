package de.atlascore.world.anvil.io;

import java.io.IOException;

import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.AbstractNBTBase;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;

/**
 * Class for handling the IO operations for a chunk column
 */
public class CoreAnvilChunkIO extends AbstractNBTBase {
	
	protected static final CharKey
	NBT_DATA_VERSION = CharKey.literal("DataVersion"),
	//NBT_X_POS = CharKey.of("xPos"),
	//NBT_Z_POS = CharKey.of("zPos"),
	//NBT_Y_POS = CharKey.of("yPos"), // 1.17
	NBT_STATUS = CharKey.literal("Status"),
	NBT_LAST_UPDATE = CharKey.literal("LastUpdate"),
	NBT_SECTIONS = CharKey.literal("sections"),
	NBT_BLOCK_ENTITIES = CharKey.literal("block_entities"),
	// hightmaps
	NBT_HEIGHTMAPS = CharKey.literal("Heightmaps"),
	NBT_MOTION_BLOCKING = CharKey.literal("MOTION_BLOCKING"),
	NBT_MOTION_BLOCKING_NO_LEAVES = CharKey.literal("MOTION_BLOCKING_NO_LEAVES"),
	NBT_OCEAN_FLOOR = CharKey.literal("OCEAN_FLOOR"),
	// OCEAN_FLOOR_WG
	NBT_WORLD_SURFACE = CharKey.literal("WORLD_SURFACE"),
	// WORLD_SURFACE_WG
	NBT_TILE_TICKS = CharKey.literal("TileTicks"),
	NBT_TO_BE_TICKED = CharKey.literal("ToBeTicked"),
	NBT_INHABITED_TIME = CharKey.literal("InhabitedTime"),
	NBT_BIOMES = CharKey.literal("Biomes");
	
	protected static final NBTFieldContainer<CoreAnvilChunkIO> NBT_FIELDS;
	
	static {
		NBT_FIELDS = new NBTFieldContainer<>();
		NBT_FIELDS.setField(NBT_STATUS, NBTField.skip());
		NBT_FIELDS.setField(NBT_LAST_UPDATE, (holder, reader) -> {
			holder.lastUpdate = reader.readLongTag();
		});	
		NBT_FIELDS.setField(NBT_SECTIONS, (holder, reader) -> {
			if (holder.sectionIO == null)
				holder.sectionIO = new CoreAnvilChunkSectionIO();
			holder.sectionIO.loadSections(holder.chunk, reader);
		});
		NBT_FIELDS.setField(NBT_HEIGHTMAPS, NBTField.skip());
//		NBTFieldContainer heightmaps = NBT_FIELDS.setContainer(NBT_HEIGHTMAPS);
//		heightmaps.setField(NBT_MOTION_BLOCKING, NBTField.SKIP);
//		heightmaps.setField(NBT_MOTION_BLOCKING_NO_LEAVES, NBTField.SKIP);
//		heightmaps.setField(NBT_OCEAN_FLOOR, NBTField.SKIP);
//		heightmaps.setField(NBT_WORLD_SURFACE, NBTField.SKIP);
		NBT_FIELDS.setField(NBT_TILE_TICKS, (holder, reader) -> {
			
		});
	}
	
	private long lastUpdate;
	private CoreAnvilChunkSectionIO sectionIO;
	private Chunk chunk;
	
	public void loadChunk(Chunk chunk, NBTReader reader) throws IOException {
		this.chunk = chunk;
		fromNBT(reader);
		// free
		chunk = null;
		lastUpdate = 0;
	}

	public boolean checkVersion(int version) {
		return true;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		if (chunk == null)
			throw new IllegalStateException("Chunk is not initialized! (use #loadChunk)");
		super.fromNBT(reader);
	}

	@Override
	protected NBTFieldContainer<? extends CoreAnvilChunkIO> getFieldContainerRoot() {
		return NBT_FIELDS;
	}

}
