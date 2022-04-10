package de.atlascore.world.io.anvil;

import java.io.IOException;
import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.util.nbt.AbstractNBTBase;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

/**
 * Class for handling the IO operations for chunk sections
 */
public class CoreAnvilChunkSectionIO extends AbstractNBTBase {

	protected static final NBTFieldContainer NBT_FIELDS;
	
	protected static final String
	BLOCK_LIGHT = "BlockLight",
	BLOCK_STATES = "BlockStates",
	PALETTE = "Palette",
	SKY_LIGHT = "Palette",
	Y = "Y";
	
	static {
		NBT_FIELDS = new NBTFieldContainer();
		NBT_FIELDS.setField(BLOCK_LIGHT, (holder, reader) -> {
			CoreAnvilChunkSectionIO loader = (CoreAnvilChunkSectionIO) holder;
			loader.blocklight = reader.readByteArrayTag();
		});
		NBT_FIELDS.setField(BLOCK_STATES, (holder, reader) -> {
			CoreAnvilChunkSectionIO loader = (CoreAnvilChunkSectionIO) holder;
			loader.blockstates = reader.readLongArrayTag();
		});
		NBT_FIELDS.setField(PALETTE, (holder, reader) -> {
			CoreAnvilChunkSectionIO loader = (CoreAnvilChunkSectionIO) holder;
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				Material mat = Material.getByName(reader.readStringTag());
				BlockData data = mat.createBlockData();
				reader.readNextEntry();
				data.fromNBT(reader);
				loader.palette.add(data);
				reader.readNextEntry();
			}
		});
		NBT_FIELDS.setField(SKY_LIGHT, (holder, reader) -> {
			CoreAnvilChunkSectionIO loader = (CoreAnvilChunkSectionIO) holder;
			loader.skylight = reader.readByteArrayTag();
		});
		NBT_FIELDS.setField(Y, (holder, reader) -> {
			CoreAnvilChunkSectionIO loader = (CoreAnvilChunkSectionIO) holder;
			loader.hightIndex = reader.readByteTag();
		});
	}
	
	private byte[] blocklight, skylight;
	private long[] blockstates;
	private List<BlockData> palette;
	private byte hightIndex;
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}

}
