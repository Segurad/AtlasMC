package de.atlasmc.core.node.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.node.inventory.BrewingInventory;
import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.InventoryType;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.InventoryType.SlotType;

public class CoreBrewingStandInventory extends CoreInventory implements BrewingInventory {

	protected static final byte 
	PROPERTY_BREW_TIME = 0,
	PROPERTY_FUEL_TIME = 1;
	
	public CoreBrewingStandInventory(Chat title, InventoryHolder holder) {
		super(5, 3, InventoryType.BREWING, title, holder);
		properties[PROPERTY_BREW_TIME] = 400;
	}
	
	@Override
	protected int getPropertyCount() {
		return 2;
	}
	
	@Override
	public SlotType getSlotType(int slot) {
		switch (slot) {
		case 0:
		case 1:
		case 2:
		case 3:
			return SlotType.CRAFTING;
		case 4:
			return SlotType.FUEL;
		default:
			throw new IllegalArgumentException("Invalid slot index: " + slot);
		}
	}

	@Override
	public int getFuelLevel() {
		return properties[PROPERTY_FUEL_TIME];
	}

	@Override
	public void setFuelLevel(int value) {
		updateProperty(PROPERTY_FUEL_TIME, value);
	}

	@Override
	public int getBrewTime() {
		return properties[PROPERTY_BREW_TIME];
	}

	@Override
	public void setBrewTime(int value) {
		updateProperty(PROPERTY_BREW_TIME, value);
	}

	@Override
	public ItemStack getFuel() {
		return getItem(4);
	}

	@Override
	public void setFuel(ItemStack fuel) {
		setItem(4, fuel);
	}

	@Override
	public ItemStack getIngredient() {
		return getItem(3);
	}

	@Override
	public void setIngredient(ItemStack ingredient) {
		setItem(3, ingredient);
	}
	
}
