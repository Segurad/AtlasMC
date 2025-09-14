package de.atlasmc.core.node.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.node.inventory.CartographyInventory;
import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.InventoryType;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.InventoryType.SlotType;

public class CoreCartographyInventory extends CoreInventory implements CartographyInventory {

	public CoreCartographyInventory(Chat title, InventoryHolder holder) {
		super(3, 0, InventoryType.CARTOGRAPHY, title, holder);
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
