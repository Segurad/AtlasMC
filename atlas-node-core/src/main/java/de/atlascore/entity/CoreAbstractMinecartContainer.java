package de.atlascore.entity;

import de.atlasmc.NamespacedKey;
import de.atlasmc.entity.AbstractMinecartContainer;
import de.atlasmc.entity.EntityType;
import de.atlasmc.inventory.Inventory;

public abstract class CoreAbstractMinecartContainer extends CoreAbstractMinecart implements AbstractMinecartContainer {
	
	private Inventory inv;
	private NamespacedKey lootTable;
	private long lootSeed;
	
	public CoreAbstractMinecartContainer(EntityType type) {
		super(type);
	}

	@Override
	public Inventory getInventory() {
		if (inv == null)
			inv = createInventory();
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
		return lootSeed;
	}
	
	@Override
	public void setLootTableSeed(long seed) {
		this.lootSeed = seed;
	}

	protected abstract Inventory createInventory();

}
