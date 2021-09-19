package de.atlascore.inventory;

import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.event.inventory.InventoryType.SlotType;
import de.atlasmc.inventory.BrewingInventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.ItemStack;

public class CoreBrewingStandInventory extends CoreInventory implements BrewingInventory {

	protected static final byte 
	PROPERTY_BREW_TIME = 0,
	PROPERTY_FUEL_TIME = 1;
	
	private int fuel, maxfuel, brewingTime, dfuel, dbrewingTime = 400;
	
	public CoreBrewingStandInventory(ChatComponent title, InventoryHolder holder) {
		super(5, InventoryType.BREWING, title, holder);
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
		return fuel;
	}

	@Override
	public void setFuelLevel(int value) {
		this.fuel = value;
	}

	@Override
	public int getMaxFuelLevel() {
		return maxfuel;
	}

	@Override
	public void setMaxFuelLevel(int value) {
		this.maxfuel = value;
	}

	@Override
	public int getDisplayFuel() {
		return dfuel;
	}

	@Override
	public void setDisplayFuel(int value) {
		this.dfuel = value;
		updateProperty(PROPERTY_FUEL_TIME, value);
	}

	@Override
	public int getBrewTime() {
		return brewingTime;
	}

	@Override
	public void setBrewTime(int value) {
		this.brewingTime = value;
	}

	@Override
	public int getDisplayBrewTime() {
		return dbrewingTime;
	}

	@Override
	public void setDisplayBrewTime(int value) {
		this.dbrewingTime = value;
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
	
	@Override
	public void updateProperties() {
		for (Player p : getViewers()) {
			updateProperties(p);
		}
	}

	@Override
	public void updateProperties(Player player) {
		updateProperty(PROPERTY_BREW_TIME, dfuel, player);
		updateProperty(PROPERTY_FUEL_TIME, dbrewingTime, player);
	}
	
}
