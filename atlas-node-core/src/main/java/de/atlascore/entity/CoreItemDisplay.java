package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.Material;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.ItemDisplay;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreItemDisplay extends CoreDisplay implements ItemDisplay {

	protected static final MetaDataField<ItemStack> META_DISPLAYED_ITEM = new MetaDataField<>(CoreDisplay.LAST_META_INDEX+1, null, MetaDataType.SLOT);
	protected static final MetaDataField<Byte> META_DISPLAY_TYPE = new MetaDataField<>(CoreDisplay.LAST_META_INDEX+2, (byte) 0, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreDisplay.LAST_META_INDEX+2;
	
	private static final CharKey
	NBT_ITEM = CharKey.literal("item"),
	NBT_ITEM_DISPLAY = CharKey.literal("item_display");
	
	static {
		NBT_FIELDS.setField(NBT_ITEM, (holder, reader) -> {
			if (holder instanceof ItemDisplay ent) {
				reader.readNextEntry();
				Material mat = null;
				if (!NBT_ID.equals(reader.getFieldName())) {
					reader.mark();
					reader.search(NBT_ID);
					mat = Material.getByName(reader.readStringTag());
					reader.reset();
				} else mat = Material.getByName(reader.readStringTag());
				ItemStack item = new ItemStack(mat);
				item.fromNBT(reader);
				ent.setItem(item);
			} else {
				reader.skipTag();
			}
		});
		NBT_FIELDS.setField(NBT_ITEM_DISPLAY, (holder, reader) -> {
			if (holder instanceof ItemDisplay ent) {
				ent.setRenderType(RenderType.getByNameID(reader.readStringTag()));
			} else {
				reader.skipTag();
			}
		});
	}
	
	public CoreItemDisplay(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_DISPLAYED_ITEM);
		metaContainer.set(META_DISPLAY_TYPE);
	}

	@Override
	public ItemStack getItem() {
		return metaContainer.getData(META_DISPLAYED_ITEM);
	}

	@Override
	public void setItem(ItemStack item) {
		metaContainer.get(META_DISPLAYED_ITEM).setData(item);
	}

	@Override
	public RenderType getRenderType() {
		return RenderType.getByID(metaContainer.getData(META_DISPLAY_TYPE));
	}

	@Override
	public void setRenderType(RenderType renderType) {
		if (renderType == null)
			throw new IllegalArgumentException("RenderType can not be null!");
		metaContainer.get(META_DISPLAY_TYPE).setData((byte) renderType.getID());
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		ItemStack item = metaContainer.getData(META_DISPLAYED_ITEM);
		if (item != null) {
			writer.writeCompoundTag(NBT_ITEM);
			item.toNBT(writer, systemData);
			writer.writeEndTag();
		}
		RenderType type = getRenderType();
		if (type != RenderType.NONE)
			writer.writeStringTag(NBT_ITEM_DISPLAY, null);
	}

}
