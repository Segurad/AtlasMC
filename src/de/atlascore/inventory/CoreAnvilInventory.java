package de.atlascore.inventory;

import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.event.inventory.InventoryType.SlotType;
import de.atlasmc.inventory.AnvilInventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.ItemStack;

public class CoreAnvilInventory extends CoreInventory implements AnvilInventory {
	
	protected static final byte
	PROPERTY_REPAIR_COST = 0;
	
	private int repaircost;
	
	public CoreAnvilInventory(String title, InventoryHolder holder) {
		super(3, InventoryType.ANVIL, title, holder);
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
		return repaircost;
	}

	@Override
	public void setRepairCost(int value) {
		this.repaircost = value;
		updateProperty(PROPERTY_REPAIR_COST, value);
	}
	
	@Override
	public void updateProperties() {
		for (Player p : getViewers()) {
			updateProperties(p);
		}
	}
	
	@Override
	public void updateProperties(Player player) {
		updateProperty(PROPERTY_REPAIR_COST, repaircost, player);
	}

}
