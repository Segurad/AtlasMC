package de.atlascore.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.entity.Player;
import de.atlasmc.inventory.BeaconInventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.InventoryType.SlotType;
import de.atlasmc.potion.PotionEffect;

public class CoreBeaconInventory extends CoreInventory implements BeaconInventory {

	protected static final byte
	PROPERTY_POWER_LEVEL = 0,
	PROPERTY_FIRST_POTION_ID = 1,
	PROPERTY_SECOND_POTION_ID = 2;
	
	private PotionEffect primary, secondary;
	private int primaryid = -1, secondaryid = -1, power;
	
	public CoreBeaconInventory(Chat title, InventoryHolder holder) {
		super(1, 0, InventoryType.BEACON, title, holder);
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
		return primaryid;
	}

	@Override
	public int getSecondaryID() {
		return secondaryid;
	}

	@Override
	public void setPrimaryID(int id) {
		this.primaryid = id;
		updateProperty(PROPERTY_FIRST_POTION_ID, id);
	}

	@Override
	public void setSecondaryID(int id) {
		this.secondaryid = id;
		updateProperty(PROPERTY_SECOND_POTION_ID, id);
	}

	@Override
	public int getPowerLevel() {
		return power;
	}

	@Override
	public void setPowerLevel(int power) {
		if (power < 0 || power > 4) throw new IllegalArgumentException("Power must be between 0 and 4: " + power);
		this.power = power;
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
	
	@Override
	public void updateProperties() {
		for (Player p : getViewers()) {
			updateProperties(p);
		}
	}

	@Override
	public void updateProperties(Player player) {
		updateProperty(PROPERTY_POWER_LEVEL, power, player);
		updateProperty(PROPERTY_FIRST_POTION_ID, primaryid, player);
		updateProperty(PROPERTY_SECOND_POTION_ID, secondaryid, player);
	}
	
}
