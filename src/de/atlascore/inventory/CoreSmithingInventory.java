package de.atlascore.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.SmithingInventory;

public class CoreSmithingInventory extends CoreInventory implements SmithingInventory {

	public CoreSmithingInventory(Chat title, InventoryHolder holder) {
		super(3, InventoryType.SMITHING, title, holder);
	}

	@Override
	public ItemStack getResult() {
		return getItem(2);
	}

	@Override
	public void setResult(ItemStack item) {
		setItem(2, item);
	}

}
