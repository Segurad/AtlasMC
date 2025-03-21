package de.atlascore.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Piglin;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CorePiglin extends CoreAbstractPiglin implements Piglin {
	
	protected static final MetaDataField<Boolean>
	META_IS_BABY = new MetaDataField<>(CoreAbstractPiglin.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean>
	META_IS_CHARGING_CROSSBOW = new MetaDataField<>(CoreAbstractPiglin.LAST_META_INDEX+2, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean>
	META_IS_DANCING = new MetaDataField<>(CoreAbstractPiglin.LAST_META_INDEX+3, false, MetaDataType.BOOLEAN);

	protected static final int LAST_META_INDEX = CoreAbstractPiglin.LAST_META_INDEX+3;
	
	protected static final NBTFieldSet<CorePiglin> NBT_FIELDS;
	
	protected static final CharKey
	NBT_IS_BABY = CharKey.literal("IsBaby"),
	NBT_CANNOT_HUNT = CharKey.literal("CannotHunt"),
	NBT_INVENTORY = CharKey.literal("Inventory");
	
	static {
		NBT_FIELDS = CoreAbstractPiglin.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_IS_BABY, (holder, reader) -> {
			holder.setBaby(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_CANNOT_HUNT, (holder, reader) -> {
			holder.setCanHunt(reader.readByteTag() == 0);
		});
		NBT_FIELDS.setField(NBT_INVENTORY, (holder, reader) -> {
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				reader.readNextEntry();
				ItemStack item = ItemStack.getFromNBT(reader);
				holder.addPocketItem(item);
			}
			reader.readNextEntry();
		});
	}
	
	private List<ItemStack> pocketItems;
	private boolean canHunt;
	
	public CorePiglin(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldSet<? extends CorePiglin> getFieldSetRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IS_BABY);
		metaContainer.set(META_IS_CHARGING_CROSSBOW);
		metaContainer.set(META_IS_DANCING);
	}

	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public boolean isBaby() {
		return metaContainer.getData(META_IS_BABY);
	}

	@Override
	public boolean isChargingCrossbow() {
		return metaContainer.getData(META_IS_CHARGING_CROSSBOW);
	}

	@Override
	public boolean isDancing() {
		return metaContainer.getData(META_IS_SILENT);
	}

	@Override
	public void setBaby(boolean baby) {
		metaContainer.get(META_IS_BABY).setData(baby);		
	}

	@Override
	public void setChargingCorssbow(boolean charging) {
		metaContainer.get(META_IS_CHARGING_CROSSBOW).setData(charging);		
	}

	@Override
	public void setDancing(boolean dancing) {
		metaContainer.get(META_IS_DANCING).setData(dancing);		
	}

	@Override
	public void setCanHunt(boolean hunt) {
		this.canHunt = hunt;
	}

	@Override
	public boolean canHunt() {
		return canHunt;
	}

	@Override
	public List<ItemStack> getPocketItems() {
		if (pocketItems == null)
			pocketItems = new ArrayList<>();
		return pocketItems;
	}

	@Override
	public boolean hasPockItems() {
		return pocketItems != null && !pocketItems.isEmpty();
	}

	@Override
	public void addPocketItem(ItemStack item) {
		if (item == null)
			throw new IllegalArgumentException("Item can not be null!");
		getPocketItems().add(item);
	}

	@Override
	public void removePocketItem(ItemStack item) {
		if (!hasPockItems())
			return;
		getPocketItems().remove(item);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isBaby())
			writer.writeByteTag(NBT_IS_BABY, true);
		if (!canHunt())
			writer.writeByteTag(NBT_CANNOT_HUNT, true);
		if (hasPockItems()) {
			List<ItemStack> items = getPocketItems();
			writer.writeListTag(NBT_INVENTORY, TagType.COMPOUND, items.size());
			for (ItemStack item : items) {
				item.toNBT(writer, systemData);
				writer.writeEndTag();
			}
		}
	}

}
