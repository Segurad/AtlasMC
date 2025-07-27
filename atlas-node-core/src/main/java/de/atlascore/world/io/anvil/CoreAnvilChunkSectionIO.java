package de.atlascore.world.io.anvil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;

/**
 * Class for handling the IO operations for chunk sections
 */
public class CoreAnvilChunkSectionIO {

	protected static final NBTFieldSet<CoreAnvilChunkSectionIO> NBT_FIELDS;
	
	protected static final CharKey
	NBT_BLOCK_LIGHT = CharKey.literal("BlockLight"),
	NBT_BLOCK_STATES = CharKey.literal("BlockStates"),
	NBT_PALETTE = CharKey.literal("Palette"),
	NBT_SKY_LIGHT = CharKey.literal("Palette"),
	NBT_Y = CharKey.literal("Y"),
	NBT_NAME = CharKey.literal("Name"),
	NBT_PROPERTIES = CharKey.literal("Properties");
	
	static {
		NBT_FIELDS = NBTFieldSet.newSet();
		NBT_FIELDS.setField(NBT_BLOCK_LIGHT, (holder, reader) -> {
			if (holder.blocklight == null)
				holder.blocklight = new byte[2048];
			reader.readByteArrayTag(holder.blocklight);
			holder.hasBlocklight = true;
		});
		NBT_FIELDS.setField(NBT_BLOCK_STATES, (holder, reader) -> {
			holder.indizes = reader.readLongArrayTag();
		});
		NBT_FIELDS.setField(NBT_PALETTE, (holder, reader) -> {
			reader.readNextEntry();
			holder.palette = new ArrayList<>(reader.getRestPayload());
			while (reader.getRestPayload() > 0) {
				reader.readNextEntry();
				BlockData data = BlockData.NBT_HANDLER.deserialize(reader);
				holder.palette.add(data);
			}
			reader.readNextEntry();
		});
		NBT_FIELDS.setField(NBT_SKY_LIGHT, (holder, reader) -> {
			if (holder.skylight == null)
				holder.skylight = new byte[2048];
			reader.readByteArrayTag(holder.skylight);
			holder.hasSkylight = true;
		});
		NBT_FIELDS.setField(NBT_Y, (holder, reader) -> {
			holder.hightIndex = reader.readByteTag();
		});
	}
	
	private byte[] blocklight;
	private byte[] skylight;
	private boolean hasBlocklight;
	private boolean hasSkylight;
	private long[] indizes;
	private byte hightIndex;
	private List<BlockData> palette;
	
	public void loadSections(Chunk chunk, NBTReader reader) throws IOException {
	
		// free
		free();
	}
	
	public void saveSections(Chunk chunk, NBTWriter writer) throws IOException {
		
	}
	
	public void free() {
		this.indizes = null;
		this.palette = null;
	}
	
}
