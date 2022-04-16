package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.Material;
import de.atlasmc.entity.ChestedHorse;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.AbstractHorseInventory;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CoreChestedHorse extends CoreAbstractHorse implements ChestedHorse {
	
	protected static final MetaDataField<Boolean>
	META_HORSE_HAS_CHEST = new MetaDataField<>(CoreAbstractHorse.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreAbstractHorse.LAST_META_INDEX+1;

	protected static final String
	NBT_CHESTED_HORSE = "ChestedHorse",
	NBT_ITEMS = "Items";
	
	static {
		NBT_FIELDS.setField(NBT_CHESTED_HORSE, (holder, reader) -> {
			if (holder instanceof ChestedHorse) {
				((ChestedHorse) holder).setChest(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_ITEMS, (holder, reader) -> {
			if (!(holder instanceof ChestedHorse)) {
				reader.skipTag();
				return;
			}
			reader.readNextEntry();
			AbstractHorseInventory inv = ((ChestedHorse) holder).getInventory();
			while (reader.getRestPayload() > 0) {
				Material mat = null;
				if (!NBT_ID.equals(reader.getFieldName())) {
					reader.mark();
					reader.search(NBT_ID);
					mat = Material.getByName(reader.readStringTag());
					reader.reset();
				} else mat = Material.getByName(reader.readStringTag());
				ItemStack item = new ItemStack(mat);
				int slot = item.fromSlot(reader);
				if (inv.getSize() > slot && slot > 0)
					inv.setItem(slot, item);
			}
		});
	}
	
	public CoreChestedHorse(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
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
