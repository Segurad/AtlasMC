package de.atlascore.block.tile;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.tile.Hopper;
import de.atlasmc.inventory.ContainerFactory;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryType;

public class CoreHopper extends CoreAbstractContainerTile<Inventory> implements Hopper {
	
	private int cooldown;
	
	public CoreHopper(BlockType type) {
		super(type);
	}

	@Override
	protected Inventory createInventory() {
		return ContainerFactory.GENERIC_INV_FACTORY.create(InventoryType.HOPPER, this);
	}

	@Override
	public int getTransferCooldown() {
		return cooldown;
	}

	@Override
	public void setTransferCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

}
