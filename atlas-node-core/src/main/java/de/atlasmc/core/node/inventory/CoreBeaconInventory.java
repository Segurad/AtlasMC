package de.atlasmc.core.node.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.node.inventory.BeaconInventory;
import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.InventoryType;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.InventoryType.SlotType;
import de.atlasmc.node.potion.PotionEffectType;

public class CoreBeaconInventory extends CoreInventory implements BeaconInventory {

	protected static final byte
	PROPERTY_POWER_LEVEL = 0,
	PROPERTY_FIRST_POTION_ID = 1,
	PROPERTY_SECOND_POTION_ID = 2;
	
	private PotionEffectType primary;
	private PotionEffectType secondary;
	
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
	public PotionEffectType getPrimaryEffectType() {
		return primary;
	}

	@Override
	public PotionEffectType getSecondaryEffectType() {
		return secondary;
	}

	@Override
	public void setPrimaryEffectType(PotionEffectType effect) {
		this.primary = effect;
		updateProperty(PROPERTY_FIRST_POTION_ID, effect == null ? -1 : effect.getID());
	}

	@Override
	public void setSecondaryEffectType(PotionEffectType effect) {
		this.secondary = effect;
		updateProperty(PROPERTY_SECOND_POTION_ID, effect == null ? -1 : effect.getID());
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
