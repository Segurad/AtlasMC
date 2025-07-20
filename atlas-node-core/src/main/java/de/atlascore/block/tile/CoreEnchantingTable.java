package de.atlascore.block.tile;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.tile.EnchantingTable;
import de.atlasmc.chat.Chat;
import de.atlasmc.inventory.ContainerFactory;
import de.atlasmc.inventory.EnchantingInventory;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.ItemPredicate;

public class CoreEnchantingTable extends CoreTileEntity implements EnchantingTable {
	
	private ItemPredicate lock;
	private Chat name;
	
	public CoreEnchantingTable(BlockType type) {
		super(type);
	}

	@Override
	public EnchantingInventory getInventory() {
		return ContainerFactory.ENCHANTING_INV_FACTORY.create(InventoryType.ENCHANTING, name, this);
	}
	
	@Override
	public boolean hasInventory() {
		return false;
	}

	@Override
	public Chat getCustomName() {
		return name;
	}

	@Override
	public void setCustomName(Chat name) {
		this.name = name;
	}
	
	@Override
	public boolean hasCustomName() {
		return name != null;
	}

	@Override
	public void setLock(ItemPredicate lock) {
		this.lock = lock;
	}

	@Override
	public boolean hasLock() {
		return lock != null;
	}

	@Override
	public ItemPredicate getLock() {
		return lock;
	}

}
