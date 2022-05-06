package de.atlascore.entity;

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
import de.atlasmc.world.World;

public abstract class CoreAbstractMinecartContainer extends CoreAbstractMinecart implements AbstractMinecartContainer {

	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final CharKey
	NBT_ITEMS = CharKey.of("Items"),
	NBT_LOOT_TABLE = CharKey.of("LootTable"),
	NBT_LOOT_TABLE_SEED = CharKey.of("LootTableSeed");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreAbstractMinecart.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_ITEMS, (holder, reader) -> {
			if (holder instanceof AbstractMinecartContainer) {
				reader.readNextEntry();
				while (reader.getRestPayload() > 0) {
					Inventory inv = ((AbstractMinecartContainer) holder).getInventory();
					Material mat = null;
					if (!NBT_ID.equals(reader.getFieldName())) {
						reader.mark();
						reader.search(NBT_ID);
						mat = Material.getByName(reader.readStringTag());
						reader.reset();
					} else mat = Material.getByName(reader.readStringTag());
					ItemStack item = new ItemStack(mat);
					int slot = item.fromSlot(reader);
					if (slot != -999) inv.setItem(slot, item);
				}
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_LOOT_TABLE, NBTField.SKIP); // TODO wait for loot table Registry
		NBT_FIELDS.setField(NBT_LOOT_TABLE_SEED, NBTField.SKIP); // ^ how about no?
	}
	
	private Inventory inv;
	
	public CoreAbstractMinecartContainer(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}

	@Override
	public Inventory getInventory() {
		if (inv == null)
			inv = createInventory();
		return inv;
	}

	protected abstract Inventory createInventory();

}
