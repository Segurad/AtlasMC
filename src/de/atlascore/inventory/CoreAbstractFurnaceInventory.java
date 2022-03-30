package de.atlascore.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.event.inventory.InventoryType.SlotType;
import de.atlasmc.inventory.AbstractFurnaceInventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.ItemStack;

public class CoreAbstractFurnaceInventory extends CoreInventory implements AbstractFurnaceInventory {

	protected static final byte
	PROPERTY_FUEL_LEVEL = 0,
	PROPERTY_MAX_FUEL_LEVEL = 1,
	PROPERTY_PROGRESS = 2,
	PROPERTY_MAX_PROGRESS = 3;
	
	private int progress, fuel, maxprogress, maxfuel;
	
	public CoreAbstractFurnaceInventory(InventoryType type, Chat title, InventoryHolder holder) {
		super(3, type, title, holder);
		this.maxprogress = 200;
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
		return fuel;
	}

	@Override
	public void setFuelLevel(int value) {
		this.fuel = value;
		updateProperty(PROPERTY_FUEL_LEVEL, value);
	}

	@Override
	public int getMaxFuelLevel() {
		return maxfuel;
	}

	@Override
	public void setMaxFuelLevel(int value) {
		this.maxfuel = value;
		updateProperty(PROPERTY_MAX_FUEL_LEVEL, value);
	}

	@Override
	public int getProgress() {
		return progress;
	}

	@Override
	public void setProgress(int value) {
		this.progress = value;
		updateProperty(PROPERTY_PROGRESS, value);
	}

	@Override
	public int getMaxProgress() {
		return maxprogress;
	}

	@Override
	public void setMaxProgress(int value) {
		this.maxprogress = value;
		updateProperty(PROPERTY_MAX_PROGRESS, value);
	}
	
	@Override
	public void updateProperties() {
		for (Player p : getViewers()) {
			updateProperties(p);
		}
	}
	
	@Override
	public void updateProperties(Player player) {
		updateProperty(PROPERTY_MAX_FUEL_LEVEL, maxfuel, player);
		updateProperty(PROPERTY_FUEL_LEVEL, fuel, player);
		updateProperty(PROPERTY_MAX_PROGRESS, progress, player);
		updateProperty(PROPERTY_PROGRESS, progress, player);
	}
	
}
