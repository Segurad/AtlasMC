package de.atlascore.block.tile;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.tile.EnchantingTable;
import de.atlasmc.chat.Chat;
import de.atlasmc.inventory.ContainerFactory;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryType;

public class CoreEnchantingTable extends CoreTileEntity implements EnchantingTable {
	
	private Chat name;
	
	public CoreEnchantingTable(BlockType type) {
		super(type);
	}

	@Override
	public Inventory getInventory() {
		return ContainerFactory.ENCHANTING_INV_FACTORY.create(InventoryType.ENCHANTING, name, this);
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

}
