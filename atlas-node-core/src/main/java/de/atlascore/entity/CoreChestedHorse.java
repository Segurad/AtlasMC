package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.ChestedHorse;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.inventory.AbstractHorseInventory;
import de.atlasmc.inventory.ContainerFactory;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreChestedHorse extends CoreAbstractHorse implements ChestedHorse {
	
	protected static final MetaDataField<Boolean>
	META_HORSE_HAS_CHEST = new MetaDataField<>(CoreAbstractHorse.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreAbstractHorse.LAST_META_INDEX+1;

	protected static final CharKey
	NBT_CHESTED_HORSE = CharKey.literal("ChestedHorse"),
	NBT_ITEMS = CharKey.literal("Items");
	
	static {
		NBT_FIELDS.setField(NBT_CHESTED_HORSE, (holder, reader) -> {
			if (holder instanceof ChestedHorse) {
				((ChestedHorse) holder).setChest(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_ITEMS, (holder, reader) -> {
			if (!(holder instanceof ChestedHorse entity)) {
				reader.skipTag();
				return;
			}
			reader.readNextEntry();
			AbstractHorseInventory inv = entity.getInventory();
			while (reader.getRestPayload() > 0) {
				reader.readNextEntry();
				ItemStack item = ItemStack.getFromNBT(reader, false);
				int slot = item.fromSlot(reader);
				if (inv.getSize() > slot && slot > 0)
					inv.setItem(slot, item);
			}
			reader.readNextEntry();
		});
	}
	
	public CoreChestedHorse(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_HORSE_HAS_CHEST);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean hasChest() {
		return metaContainer.getData(META_HORSE_HAS_CHEST);
	}

	@Override
	public void setChest(boolean chest) {
		if (!metaContainer.get(META_HORSE_HAS_CHEST).setData(chest) || inv == null)
			return;
		// create new inventory because of size change and copy contents
		AbstractHorseInventory old = inv;
		inv = createInventory();
		inv.setContents(old.getContents());
	}
	
	@Override
	protected AbstractHorseInventory createInventory() {
		return ContainerFactory.HORSE_INV_FACTORY.create(this);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (hasChest())
			writer.writeByteTag(NBT_CHESTED_HORSE, true);
		if (inv != null) {
			int items = 0;
			for (int i = 2; i < inv.getSize(); i++) {
				if (inv.getItem(i) != null)
					items++;
			}
			if (items > 0) {
				writer.writeListTag(NBT_ITEMS, TagType.COMPOUND, items);
				for (int i = 2; i < inv.getSize(); i++) {
					ItemStack item = inv.getItem(i);
					if (item == null)
						continue;
					item.toSlot(writer, systemData, i);
					writer.writeEndTag();
				}
			}
		}
	}

}
