package de.atlasmc.core.node.block.tile;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.tile.Hopper;
import de.atlasmc.node.inventory.ContainerFactory;
import de.atlasmc.node.inventory.Inventory;
import de.atlasmc.node.inventory.InventoryType;

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
