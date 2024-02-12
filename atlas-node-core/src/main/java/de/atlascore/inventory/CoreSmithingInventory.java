package de.atlascore.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.SmithingInventory;
import de.atlasmc.inventory.InventoryType.SlotType;

public class CoreSmithingInventory extends CoreInventory implements SmithingInventory {

	public CoreSmithingInventory(Chat title, InventoryHolder holder) {
		super(3, 2, InventoryType.SMITHING, title, holder);
	}
	
	@Override
	public SlotType getSlotType(int slot) {
		switch (slot) {
		case 0:
		case 1: 
			return SlotType.CRAFTING;
		case 2: 
			return SlotType.RESULT;
		default: 
			throw new IllegalArgumentException("Invalid slot index: " + slot);
		}
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
