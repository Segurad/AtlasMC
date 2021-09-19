package de.atlascore.inventory;

import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.LlamaInventory;

public class CoreLlamaInventory extends CoreInventory implements LlamaInventory {

	public CoreLlamaInventory(ChatComponent title, InventoryHolder holder) {
		super(2, InventoryType.LLAMA, title, holder);
	}

}
