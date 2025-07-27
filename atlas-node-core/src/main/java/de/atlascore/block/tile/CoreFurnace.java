package de.atlascore.block.tile;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.tile.Furnace;
import de.atlasmc.inventory.AbstractFurnaceInventory;
import de.atlasmc.inventory.ContainerFactory;
import de.atlasmc.inventory.InventoryType;

public class CoreFurnace extends CoreAbstractContainerTile<AbstractFurnaceInventory> implements Furnace {

	public CoreFurnace(BlockType type) {
		super(type);
	}

	@Override
	protected AbstractFurnaceInventory createInventory() {
		if (getType().getNamespacedKey() == BlockType.FURNACE)
			return ContainerFactory.FURNACE_INV_FACTPRY.create(InventoryType.FURNACE, this);
		if (getType().getNamespacedKey() == BlockType.BLAST_FURNACE)
			return ContainerFactory.BLAST_FURNACE_INV_FACTORY.create(InventoryType.BLAST_FURNACE, this);
		if (getType().getNamespacedKey() == BlockType.SMOKER)
			return ContainerFactory.SMOKER_INV_FACTORY.create(InventoryType.SMOKER, this);
		return null;
	}

}
