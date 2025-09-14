package de.atlasmc.core.node.block.tile;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.tile.Furnace;
import de.atlasmc.node.inventory.AbstractFurnaceInventory;
import de.atlasmc.node.inventory.ContainerFactory;
import de.atlasmc.node.inventory.InventoryType;

public class CoreFurnace extends CoreAbstractContainerTile<AbstractFurnaceInventory> implements Furnace {

	public CoreFurnace(BlockType type) {
		super(type);
	}

	@Override
	protected AbstractFurnaceInventory createInventory() {
		if (getType() == BlockType.FURNACE.get())
			return ContainerFactory.FURNACE_INV_FACTPRY.create(InventoryType.FURNACE, this);
		if (getType() == BlockType.BLAST_FURNACE.get())
			return ContainerFactory.BLAST_FURNACE_INV_FACTORY.create(InventoryType.BLAST_FURNACE, this);
		if (getType() == BlockType.SMOKER.get())
			return ContainerFactory.SMOKER_INV_FACTORY.create(InventoryType.SMOKER, this);
		return null;
	}

}
