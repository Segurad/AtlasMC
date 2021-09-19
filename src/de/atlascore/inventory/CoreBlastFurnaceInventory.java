package de.atlascore.inventory;

import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.BlastFurnaceInventory;
import de.atlasmc.inventory.InventoryHolder;

public class CoreBlastFurnaceInventory extends CoreAbstractFurnaceInventory implements BlastFurnaceInventory {

	public CoreBlastFurnaceInventory(ChatComponent title, InventoryHolder holder) {
		super(InventoryType.BLAST_FURNACE, title, holder);
	}

}
