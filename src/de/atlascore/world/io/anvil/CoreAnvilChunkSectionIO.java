package de.atlascore.world.io.anvil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.util.VariableValueArray;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.AbstractNBTBase;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkSection;

/**
 * Class for handling the IO operations for chunk sections
 */
public class CoreAnvilChunkSectionIO extends AbstractNBTBase {

	protected static final NBTFieldContainer NBT_FIELDS;
	
	protected static final CharKey
	NBT_BLOCK_LIGHT = CharKey.of("BlockLight"),
	NBT_BLOCK_STATES = CharKey.of("BlockStates"),
	NBT_PALETTE = CharKey.of("Palette"),
	NBT_SKY_LIGHT = CharKey.of("Palette"),
	NBT_Y = CharKey.of("Y"),
	NBT_NAME = CharKey.of("Name"),
	NBT_PROPERTIES = CharKey.of("Properties");
	
	static {
		NBT_FIELDS = new NBTFieldContainer();
		NBT_FIELDS.setField(NBT_BLOCK_LIGHT, (holder, reader) -> {
			CoreAnvilChunkSectionIO loader = (CoreAnvilChunkSectionIO) holder;
			if (loader.blocklight == null)
				loader.blocklight = new byte[2048];
			reader.readByteArrayTag(loader.blocklight);
			loader.hasBlocklight = true;
		});
		NBT_FIELDS.setField(NBT_BLOCK_STATES, (holder, reader) -> {
			CoreAnvilChunkSectionIO loader = (CoreAnvilChunkSectionIO) holder;
			loader.indizes = reader.readLongArrayTag();
		});
		NBT_FIELDS.setField(NBT_PALETTE, (holder, reader) -> {
			CoreAnvilChunkSectionIO loader = (CoreAnvilChunkSectionIO) holder;
			reader.readNextEntry();
			loader.palette = new ArrayList<>(reader.getRestPayload());
			while (reader.getRestPayload() > 0) {
				Material mat = Material.getByName(reader.readStringTag());
				BlockData data = mat.createBlockData();
				if (reader.getType() == TagType.COMPOUND) {
					reader.readNextEntry();
					data.fromNBT(reader);
				}
				loader.palette.add(data);
				reader.readNextEntry();
			}
		});
		NBT_FIELDS.setField(NBT_SKY_LIGHT, (holder, reader) -> {
			CoreAnvilChunkSectionIO loader = (CoreAnvilChunkSectionIO) holder;
			if (loader.skylight == null)
				loader.skylight = new byte[2048];
			reader.readByteArrayTag(loader.skylight);
			loader.hasSkylight = true;
		});
		NBT_FIELDS.setField(NBT_Y, (holder, reader) -> {
			CoreAnvilChunkSectionIO loader = (CoreAnvilChunkSectionIO) holder;
			loader.hightIndex = reader.readByteTag();
		});
	}
	
	private byte[] blocklight, skylight;
	private boolean hasBlocklight, hasSkylight;
	private long[] indizes;
	private byte hightIndex;
	private List<BlockData> palette;
	
	public CoreAnvilChunkSectionIO() {}
	
	public void loadSections(Chunk chunk, NBTReader reader) throws IOException {
		reader.readNextEntry();
		while (reader.getRestPayload() > 0) {
			super.fromNBT(reader);
			final ChunkSection section = chunk.getSection(hightIndex);
			for (int i = 0; i < palette.size(); i++) {
				section.setPaletteEntry(i, palette.get(i), true);
			}
			VariableValueArray indizes = section.getIndizes();
			long[] backing = indizes.array();
			System.arraycopy(this.indizes, 0, backing, 0, backing.length);
			if (hasBlocklight) {
				byte[] light = section.getBlockLight().array();
				System.arraycopy(blocklight, 0, light, 0, light.length);
				hasBlocklight = false;
			}
			if (hasSkylight) {
				byte[] light = section.getSkyLight().array();
				System.arraycopy(blocklight, 0, light, 0, light.length);
				hasSkylight = false;
			}
		}
		// free
		free();
	}
	
	public void saveSections(Chunk chunk, NBTWriter writer) throws IOException {
		for (int i = 0; i < 16; i++) {
			if (!chunk.hasSection(i))
				continue;
			ChunkSection section = chunk.getSection(i);
			writer.writeCompoundTag();
			writer.writeByteTag(NBT_Y, i);
			final int paletteSize = section.getPaletteSize();
			writer.writeListTag(NBT_PALETTE, TagType.COMPOUND, section.getPaletteSize());
			for (int j = 0; j < paletteSize; j++) {
				writer.writeCompoundTag();
				BlockData data = section.getPaletteEntry(i);
				writer.writeStringTag(NBT_NAME, data.getMaterial().getNamespacedName());
				if (data.getMaterial().getBlockID() != data.getStateID()) {
					writer.writeCompoundTag(NBT_PROPERTIES);
					data.toNBT(writer, true);
					writer.writeEndTag();
				}
				writer.writeEndTag();
			}
			writer.writeLongArrayTag(NBT_BLOCK_STATES, section.getIndizes().array());
			if (section.hasBlockLight())
				writer.writeByteArrayTag(NBT_BLOCK_LIGHT, section.getBlockLight().array());
			if (section.hasSkyLight())
				writer.writeByteArrayTag(NBT_SKY_LIGHT, section.getSkyLight().array());
			writer.writeEndTag();
		}
	}
	
	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		throw new IllegalStateException("Illegal invokation use #loadSections");
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		throw new IllegalStateException("Illegal invokation use #saveSections");
	}

	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	public void free() {
		this.indizes = null;
		this.palette = null;
	}
	
}
