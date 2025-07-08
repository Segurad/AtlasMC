package de.atlascore.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.inventory.AbstractFurnaceInventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.InventoryType.SlotType;
import de.atlasmc.inventory.ItemStack;

public class CoreAbstractFurnaceInventory extends CoreInventory implements AbstractFurnaceInventory {

	protected static final byte
	PROPERTY_FUEL_LEVEL = 0,
	PROPERTY_MAX_FUEL_LEVEL = 1,
	PROPERTY_PROGRESS = 2,
	PROPERTY_MAX_PROGRESS = 3;
	
	public CoreAbstractFurnaceInventory(InventoryType type, Chat title, InventoryHolder holder) {
		super(3, 2, type, title, holder);
		properties[PROPERTY_MAX_FUEL_LEVEL] = 200;
	}
	
	@Override
	protected int getPropertyCount() {
		return 4;
	}
	
	@Override
	public SlotType getSlotType(int slot) {
		switch (slot) {
		case 0: 
			return SlotType.CRAFTING;
		case 1: 
			return SlotType.FUEL;
		case 2: 
			return SlotType.RESULT;
		default: 
			throw new IllegalArgumentException("Invalid slot index: " + slot);
		}
	}

	@Override
	public ItemStack getFuel() {
		return getItem(1);
	}

	@Override
	public void setFuel(ItemStack item) {
		setItem(1, item);
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
	public int getFuelLevel() {
		return properties[PROPERTY_FUEL_LEVEL];
	}

	@Override
	public void setFuelLevel(int value) {
		updateProperty(PROPERTY_FUEL_LEVEL, value);
	}

	@Override
	public int getMaxFuelLevel() {
		return properties[PROPERTY_MAX_FUEL_LEVEL];
	}

	@Override
	public void setMaxFuelLevel(int value) {
		updateProperty(PROPERTY_MAX_FUEL_LEVEL, value);
	}

	@Override
	public int getProgress() {
		return properties[PROPERTY_PROGRESS];
	}

	@Override
	public void setProgress(int value) {
		updateProperty(PROPERTY_PROGRESS, value);
	}

	@Override
	public int getMaxProgress() {
		return properties[PROPERTY_MAX_PROGRESS];
	}

	@Override
	public void setMaxProgress(int value) {
		updateProperty(PROPERTY_MAX_PROGRESS, value);
	}
	
}
