package de.atlascore.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.inventory.BeaconInventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.InventoryType.SlotType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.potion.PotionEffect;

public class CoreBeaconInventory extends CoreInventory implements BeaconInventory {

	protected static final byte
	PROPERTY_POWER_LEVEL = 0,
	PROPERTY_FIRST_POTION_ID = 1,
	PROPERTY_SECOND_POTION_ID = 2;
	
	private PotionEffect primary;
	private PotionEffect secondary;
	
	public CoreBeaconInventory(Chat title, InventoryHolder holder) {
		super(1, 0, InventoryType.BEACON, title, holder);
		properties[PROPERTY_POWER_LEVEL] = -1;
		properties[PROPERTY_SECOND_POTION_ID] = -1;
	}

	@Override
	protected int getPropertyCount() {
		return 3;
	}
	
	@Override
	public SlotType getSlotType(int slot) {
		if (slot == 0) {
			return SlotType.CRAFTING;
		}
		throw new IllegalArgumentException("Invalid slot index: " + slot);
	}
	
	@Override
	public PotionEffect getPrimaryEffect() {
		return primary;
	}

	@Override
	public PotionEffect getSecondaryEffect() {
		return secondary;
	}

	@Override
	public void setPrimaryEffect(PotionEffect effect) {
		this.primary = effect;
	}

	@Override
	public void setSecondaryEffect(PotionEffect effect) {
		this.secondary = effect;
	}

	@Override
	public int getPrimaryID() {
		return properties[PROPERTY_FIRST_POTION_ID];
	}

	@Override
	public int getSecondaryID() {
		return properties[PROPERTY_SECOND_POTION_ID];
	}

	@Override
	public void setPrimaryID(int id) {
		updateProperty(PROPERTY_FIRST_POTION_ID, id);
	}

	@Override
	public void setSecondaryID(int id) {
		updateProperty(PROPERTY_SECOND_POTION_ID, id);
	}

	@Override
	public int getPowerLevel() {
		return properties[PROPERTY_POWER_LEVEL];
	}

	@Override
	public void setPowerLevel(int power) {
		if (power < 0 || power > 4) 
			throw new IllegalArgumentException("Power must be between 0 and 4: " + power);
		updateProperty(PROPERTY_POWER_LEVEL, power);
	}

	@Override
	public ItemStack getItem() {
		return getItem(0);
	}

	@Override
	public void setItem(ItemStack item) {
		setItem(0, item);
	}
	
}
