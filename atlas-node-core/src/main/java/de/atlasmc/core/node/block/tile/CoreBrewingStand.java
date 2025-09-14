package de.atlasmc.core.node.block.tile;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.tile.BrewingStand;
import de.atlasmc.node.inventory.BrewingInventory;
import de.atlasmc.node.inventory.ContainerFactory;
import de.atlasmc.node.inventory.InventoryType;

public class CoreBrewingStand extends CoreAbstractContainerTile<BrewingInventory> implements BrewingStand {
	
	public CoreBrewingStand(BlockType type) {
		super(type);
	}

	@Override
	protected BrewingInventory createInventory() {
		return ContainerFactory.BREWING_INV_FACTORY.create(InventoryType.BREWING, this);
	}

	@Override
	public int getFuelLevel() {
		if (inv == null)
			return 0;
		return getInventory().getFuelLevel();
	}

	@Override
	public void setFuelLevel(int value) {
		getInventory().setFuelLevel(value);
	}

	@Override
	public int getBrewTime() {
		if (inv == null)
			return 400;
		return getInventory().getFuelLevel();
	}

	@Override
	public void setBrewTime(int value) {
		getInventory().setBrewTime(value);
	}

}
