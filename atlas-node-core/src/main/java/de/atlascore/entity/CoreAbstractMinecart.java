package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.entity.AbstractMinecart;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreAbstractMinecart extends CoreVehicle implements AbstractMinecart {

	protected static final MetaDataField<Integer>
	META_CUSTOM_BLOCK_ID = new MetaDataField<>(CoreVehicle.LAST_META_INDEX+1, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Integer>
	META_CUSTOM_BLOCK_Y = new MetaDataField<>(CoreVehicle.LAST_META_INDEX+2, 6, MetaDataType.VAR_INT);
	protected static final MetaDataField<Boolean>
	META_SHOW_CUSTOM_BLOCK = new MetaDataField<>(CoreVehicle.LAST_META_INDEX+3, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreVehicle.LAST_META_INDEX+3;
	
	protected static final NBTFieldSet<CoreAbstractMinecart> NBT_FIELDS;
	
	protected static final CharKey
	NBT_CUSTOM_DISPLAY_TILE = CharKey.literal("CustomDisplayTile"),
	NBT_DISPLAY_OFFSET = CharKey.literal("DisplayOffset"),
	NBT_DISPLAY_STATE = CharKey.literal("DisplayState"),
	NBT_NAME = CharKey.literal("Name"),
	NBT_PROPERTIES = CharKey.literal("Properties");
	
	static {
		NBT_FIELDS = CoreEntity.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_CUSTOM_DISPLAY_TILE, (holder, reader) -> {
			holder.setShowCustomBlock(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_DISPLAY_OFFSET, (holder, reader) -> {
			holder.setCustomBlockY(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_DISPLAY_STATE, (holder, reader) -> {
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
				holder.setCustomBlock(data);
			else if (mat != null)
				holder.setCustomBlockType(mat);
		});
	}
	
	private BlockData customBlockData; 
	
	public CoreAbstractMinecart(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldSet<? extends CoreAbstractMinecart> getFieldSetRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_CUSTOM_BLOCK_ID);
		metaContainer.set(META_CUSTOM_BLOCK_Y);
		metaContainer.set(META_SHOW_CUSTOM_BLOCK);
	}

	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public BlockData getCustomBlock() {
		return customBlockData;
	}

	@Override
	public void setCustomBlock(BlockData data) {
		if (data == null)
			metaContainer.get(META_CUSTOM_BLOCK_ID).setData(0);
		else
			metaContainer.get(META_CUSTOM_BLOCK_ID).setData(data.getStateID());
		this.customBlockData = data;		
	}

	@Override
	public int getCustomBlockY() {
		return metaContainer.getData(META_CUSTOM_BLOCK_Y);
	}

	@Override
	public void setCustomBlockY(int y) {
		if (y < 0 || y > 16)
			throw new IllegalArgumentException("Y must be between 0 and 16: " + y);
		metaContainer.get(META_CUSTOM_BLOCK_Y).setData(y);
	}

	@Override
	public boolean getShowCustomBlock() {
		return metaContainer.getData(META_SHOW_CUSTOM_BLOCK);
	}

	@Override
	public void setShowCustomBlock(boolean show) {
		metaContainer.get(META_SHOW_CUSTOM_BLOCK).setData(show);		
	}

	@Override
	public boolean hasCustomBlock() {
		return customBlockData != null;
	}

	@Override
	public void setCustomBlockType(Material material) {
		if (material != null)
			setCustomBlock(material.createBlockData());
		else
			setCustomBlock(null);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getShowCustomBlock())
			writer.writeByteTag(NBT_CUSTOM_DISPLAY_TILE, true);
		int customBlockY = getCustomBlockY();
		if (customBlockY != META_CUSTOM_BLOCK_Y.getDefaultData())
			writer.writeIntTag(NBT_DISPLAY_OFFSET, customBlockY);
		if (customBlockData != null) {
			writer.writeCompoundTag(NBT_DISPLAY_STATE);
			if (systemData)
				writer.writeStringTag(NBT_NAME, customBlockData.getMaterial().getNamespacedKeyRaw());
			else
				writer.writeStringTag(NBT_NAME, customBlockData.getMaterial().getClientKey().toString());
			writer.writeCompoundTag(NBT_PROPERTIES);
			customBlockData.toNBT(writer, systemData);
			writer.writeEndTag();
			writer.writeEndTag();
		}
	}

}
