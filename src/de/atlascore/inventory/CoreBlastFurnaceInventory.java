package de.atlascore.inventory;

import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.BlastFurnaceInventory;
import de.atlasmc.inventory.InventoryHolder;

public class CoreBlastFurnaceInventory extends CoreAbstractFurnaceInventory implements BlastFurnaceInventory {

	public CoreBlastFurnaceInventory(String title, InventoryHolder holder) {
		super(InventoryType.BLAST_FURNACE, title, holder);
	}

}
