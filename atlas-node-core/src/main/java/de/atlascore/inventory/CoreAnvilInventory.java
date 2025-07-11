package de.atlascore.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.inventory.AnvilInventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.InventoryType.SlotType;
import de.atlasmc.inventory.ItemStack;

public class CoreAnvilInventory extends CoreInventory implements AnvilInventory {
	
	protected static final byte
	PROPERTY_REPAIR_COST = 0;
	
	public CoreAnvilInventory(Chat title, InventoryHolder holder) {
		super(3, 2, InventoryType.ANVIL, title, holder);
	}
	
	@Override
	protected int getPropertyCount() {
		return 1;
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

	@Override
	public int getRepairCost() {
		return properties[PROPERTY_REPAIR_COST];
	}

	@Override
	public void setRepairCost(int value) {
		updateProperty(PROPERTY_REPAIR_COST, value);
	}

}
