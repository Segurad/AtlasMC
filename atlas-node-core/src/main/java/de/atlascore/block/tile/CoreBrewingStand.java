package de.atlascore.block.tile;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.tile.BrewingStand;
import de.atlasmc.inventory.BrewingInventory;
import de.atlasmc.inventory.ContainerFactory;
import de.atlasmc.inventory.InventoryType;

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
		if (!hasInventory())
			return 0;
		return getInventory().getFuelLevel();
	}

	@Override
	public void setFuelLevel(int value) {
		getInventory().setFuelLevel(value);
	}

	@Override
	public int getBrewTime() {
		if (!hasInventory())
			return 400;
		return getInventory().getFuelLevel();
	}

	@Override
	public void setBrewTime(int value) {
		getInventory().setBrewTime(value);
	}

}
