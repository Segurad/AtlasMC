package de.atlascore.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.inventory.CraftingInventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.InventoryType.SlotType;

public class CoreCraftingInventory extends CoreInventory implements CraftingInventory {

	public CoreCraftingInventory(Chat title, InventoryHolder holder) {
		super(5, 0, InventoryType.CRAFTING, title, holder);
	}
	
	@Override
	public SlotType getSlotType(int slot) {
		switch (slot) {
		case 0:
			return SlotType.RESULT;
		case 1:
		case 2:
		case 3:
		case 4:
			return SlotType.CRAFTING;
		default:
			throw new IllegalArgumentException("Invalid slot index: " + slot);
		}
	}

	@Override
	public ItemStack getResult() {
		return getItem(0);
	}

	@Override
	public void setResult(ItemStack item) {
		setItem(0, item);
	}

}
