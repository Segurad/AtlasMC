package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.ChestBoat;
import de.atlasmc.entity.EntityType;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreChestBoat extends CoreBoat implements ChestBoat {
	
	protected static final NBTFieldSet<CoreChestBoat> NBT_FIELDS;
	
	protected static final CharKey
	NBT_ITEMS = CharKey.literal("Items"),
	NBT_LOOT_TABLE = CharKey.literal("LootTable"),
	NBT_LOOT_TABLE_SEED = CharKey.literal("LootTableSeed");
	
	static {
		NBT_FIELDS = CoreBoat.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_ITEMS, (holder, reader) -> {
			Inventory inv = holder.getInventory();
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				reader.readNextEntry();
				ItemStack item = ItemStack.getFromNBT(reader, false);
				int slot = item.fromSlot(reader);
				if (slot != -999) 
					inv.setItem(slot, item);
			}
			reader.readNextEntry();
		});
		NBT_FIELDS.setField(NBT_LOOT_TABLE, NBTField.skip()); // TODO wait for loot table Registry
		NBT_FIELDS.setField(NBT_LOOT_TABLE_SEED, NBTField.skip()); // ^ how about no?
	}
	
	private Inventory inv;

	public CoreChestBoat(EntityType type, UUID uuid) {
		super(type, uuid);
	}

	@Override
	public Inventory getInventory() {
		if (inv == null)
			inv = InventoryType.CHEST.create(this);
		return inv;
	}
	
	@Override
	protected NBTFieldSet<? extends CoreChestBoat> getFieldSetRoot() {
		return NBT_FIELDS;
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

}
