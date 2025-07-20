package de.atlasmc.inventory;

import de.atlasmc.potion.PotionEffectType;

public interface BeaconInventory extends Inventory {

	PotionEffectType getPrimaryEffectType();
	
	PotionEffectType getSecondaryEffectType();
	
	/**
	 * Sets the primary effect<br>
	 * @param id
	 */
	void setPrimaryEffectType(PotionEffectType effect);
	
	/**
	 * Sets the secondary effect<br>
	 * @param id
	 */
	void setSecondaryEffectType(PotionEffectType effect);
	
	int getPowerLevel();
	
	/**
	 * Sets the power level between 0 and 4
	 * @param power
	 */
	void setPowerLevel(int power);
	
	ItemStack getItem();
	
	void setItem(ItemStack item);
	
}
