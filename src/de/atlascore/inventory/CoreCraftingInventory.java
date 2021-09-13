package de.atlascore.inventory;

import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.CraftingInventory;
import de.atlasmc.inventory.InventoryHolder;

public class CoreCraftingInventory extends CoreInventory implements CraftingInventory {

	public CoreCraftingInventory(String title, InventoryHolder holder) {
		super(5, InventoryType.CRAFTING, title, holder);
	}

}
