package de.atlasmc.core.node.block.tile;

import de.atlasmc.chat.Chat;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.tile.EnchantingTable;
import de.atlasmc.node.inventory.ContainerFactory;
import de.atlasmc.node.inventory.EnchantingInventory;
import de.atlasmc.node.inventory.InventoryType;
import de.atlasmc.node.inventory.ItemPredicate;

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
