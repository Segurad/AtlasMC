package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.Material;
import de.atlasmc.entity.AbstractMinecartContainer;
import de.atlasmc.entity.EntityType;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public abstract class CoreAbstractMinecartContainer extends CoreAbstractMinecart implements AbstractMinecartContainer {

	protected static final ChildNBTFieldContainer<CoreAbstractMinecartContainer> NBT_FIELDS;
	
	protected static final CharKey
	NBT_ITEMS = CharKey.literal("Items"),
	NBT_LOOT_TABLE = CharKey.literal("LootTable"),
	NBT_LOOT_TABLE_SEED = CharKey.literal("LootTableSeed");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer<>(CoreAbstractMinecart.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_ITEMS, (holder, reader) -> {
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				Inventory inv = holder.getInventory();
				Material mat = null;
				if (!NBT_ID.equals(reader.getFieldName())) {
					reader.mark();
					reader.search(NBT_ID);
					mat = Material.getByName(reader.readStringTag());
					reader.reset();
				} else mat = Material.getByName(reader.readStringTag());
				ItemStack item = new ItemStack(mat);
				int slot = item.fromSlot(reader);
				if (slot != -999) 
					inv.setItem(slot, item);
			}
		});
		NBT_FIELDS.setField(NBT_LOOT_TABLE, NBTField.skip()); // TODO wait for loot table Registry
		NBT_FIELDS.setField(NBT_LOOT_TABLE_SEED, NBTField.skip()); // ^ how about no?
	}
	
	private Inventory inv;
	
	public CoreAbstractMinecartContainer(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreAbstractMinecartContainer> getFieldContainerRoot() {
		return NBT_FIELDS;
	}

	@Override
	public Inventory getInventory() {
		if (inv == null)
			inv = createInventory();
		return inv;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
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

	protected abstract Inventory createInventory();

}
