package de.atlascore.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.inventory.GrindstoneInventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.InventoryType.SlotType;

public class CoreGrindstoneInventory extends CoreInventory implements GrindstoneInventory {

	public CoreGrindstoneInventory(Chat title, InventoryHolder holder) {
		super(3, 2, InventoryType.GRINDSTONE, title, holder);
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
	public void setResult(ItemStack result) {
		setItem(2, result);
	}

}
