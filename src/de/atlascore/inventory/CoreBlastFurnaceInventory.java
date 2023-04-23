package de.atlascore.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.inventory.BlastFurnaceInventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.InventoryType;

public class CoreBlastFurnaceInventory extends CoreAbstractFurnaceInventory implements BlastFurnaceInventory {

	public CoreBlastFurnaceInventory(Chat title, InventoryHolder holder) {
		super(InventoryType.BLAST_FURNACE, title, holder);
	}

}
