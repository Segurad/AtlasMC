package de.atlasmc.core.node.entity;

import de.atlasmc.NamespacedKey;
import de.atlasmc.node.entity.ChestBoat;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.inventory.Inventory;
import de.atlasmc.node.inventory.InventoryType;

public class CoreChestBoat extends CoreBoat implements ChestBoat {
	
	private Inventory inv;
	private NamespacedKey lootTable;
	private long lootTableSeed;

	public CoreChestBoat(EntityType type) {
		super(type);
	}

	@Override
	public Inventory getInventory() {
		if (inv == null)
			inv = InventoryType.CHEST.create(this);
		return inv;
	}
	
	@Override
	public boolean hasInventory() {
		return inv != null;
	}

	@Override
	public NamespacedKey getLootTable() {
		return lootTable;
	}

	@Override
	public void setLootTable(NamespacedKey key) {
		this.lootTable = key;
	}

	@Override
	public long getLootTableSeed() {
		return lootTableSeed;
	}

	@Override
	public void setLootTableSeed(long seed) {
		this.lootTableSeed = seed;
	}

}
