package de.atlascore.world.io;

import java.io.IOException;
import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.util.nbt.AbstractNBTBase;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreAnvilChunkSectionLoader extends AbstractNBTBase {

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
			CoreAnvilChunkSectionLoader loader = (CoreAnvilChunkSectionLoader) holder;
			loader.blocklight = reader.readByteArrayTag();
		});
		NBT_FIELDS.setField(BLOCK_STATES, (holder, reader) -> {
			CoreAnvilChunkSectionLoader loader = (CoreAnvilChunkSectionLoader) holder;
			loader.blockstates = reader.readLongArrayTag();
		});
		NBT_FIELDS.setField(PALETTE, (holder, reader) -> {
			CoreAnvilChunkSectionLoader loader = (CoreAnvilChunkSectionLoader) holder;
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
			CoreAnvilChunkSectionLoader loader = (CoreAnvilChunkSectionLoader) holder;
			loader.skylight = reader.readByteArrayTag();
		});
		NBT_FIELDS.setField(Y, (holder, reader) -> {
			CoreAnvilChunkSectionLoader loader = (CoreAnvilChunkSectionLoader) holder;
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
