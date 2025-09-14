package de.atlasmc.core.node.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.node.inventory.BlastFurnaceInventory;
import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.InventoryType;

public class CoreBlastFurnaceInventory extends CoreAbstractFurnaceInventory implements BlastFurnaceInventory {

	public CoreBlastFurnaceInventory(Chat title, InventoryHolder holder) {
		super(InventoryType.BLAST_FURNACE, title, holder);
	}

}
