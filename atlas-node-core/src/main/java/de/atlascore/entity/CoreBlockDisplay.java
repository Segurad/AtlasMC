package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.entity.BlockDisplay;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBlockDisplay extends CoreDisplay implements BlockDisplay {

	protected static final MetaDataField<Integer> META_DISPLAYED_BLOCK = new MetaDataField<>(CoreDisplay.LAST_META_INDEX+1, 0, MetaDataType.BLOCKSTATE);
	
	protected static final int LAST_META_INDEX = CoreDisplay.LAST_META_INDEX+1;
	
	private static final CharKey 
	NBT_BLOCK_STATE = CharKey.literal("block_state"),
	NBT_NAME = CharKey.literal("Name"),
	NBT_PROPERTIES = CharKey.literal("Properties");
	
	static {
		NBT_FIELDS.setField(NBT_BLOCK_STATE, (holder, reader) -> {
			if (holder instanceof BlockDisplay ent) {
				reader.readNextEntry();
				Material mat = null;
				BlockData data = null;
				while (reader.getType() != TagType.TAG_END) {
					final CharSequence value = reader.getFieldName();
					if (NBT_NAME.equals(value))
						mat = Material.getByName(reader.readStringTag());
					else if (NBT_PROPERTIES.equals(value))
						if (mat == null)
							reader.skipTag();
						else {
							data = mat.createBlockData();
							reader.readNextEntry();
							data.fromNBT(reader);
						}
					else
						reader.skipTag();
				}
				reader.skipTag();
				if (data != null)
					ent.setBlockData(data);
				else if (mat != null)
					ent.setBlockDataType(mat);
			} else {
				reader.skipTag();
			}
		});
	}
	
	private BlockData block;
	
	public CoreBlockDisplay(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_DISPLAYED_BLOCK);
	}

	@Override
	public BlockData getBlockData() {
		return block;
	}

	@Override
	public void setBlockData(BlockData data) {
		int id = 0;
		if (data != null) {
			block = data;
			id = data.getStateID();
		}
		metaContainer.get(META_DISPLAYED_BLOCK).setData(id);
	}

	@Override
	public void setBlockDataType(Material mat) {
		int id = 0;
		if (mat == null)
			block = null;
		if (block == null || block.getMaterial() != mat) {
			block = mat.createBlockData();
			id = block.getStateID();
		}
		metaContainer.get(META_DISPLAYED_BLOCK).setData(id);
	}

	@Override
	public Material getBlockDataType() {
		return block != null ? block.getMaterial() : null;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (block != null) {
			writer.writeCompoundTag(NBT_BLOCK_STATE);
			writer.writeStringTag(NBT_NAME, block.getMaterial().getNamespacedKeyRaw());
			writer.writeCompoundTag(NBT_PROPERTIES);
			block.toNBT(writer, systemData);
			writer.writeEndTag();
			writer.writeEndTag();
		}
	}

}
