package de.atlasmc.inventory;

import de.atlasmc.potion.PotionEffect;

public interface BeaconInventory extends Inventory {

	PotionEffect getPrimaryEffect();
	
	PotionEffect getSecondaryEffect();

	void setPrimaryEffect(PotionEffect effect);
	
	void setSecondaryEffect(PotionEffect effect);

	int getPrimaryID();
	
	int getSecondaryID();
	
	/**
	 * Sets the primary effect ID<br>
	 * Use -1 for no effect
	 * @param id
	 */
	void setPrimaryID(int id);
	
	/**
	 * Sets the secondary effect ID<br>
	 * Use -1 for no effect
	 * @param id
	 */
	void setSecondaryID(int id);
	
	int getPowerLevel();
	
	/**
	 * Sets the power level between 0 and 4
	 * @param power
	 */
	void setPowerLevel(int power);
	
	ItemStack getItem();
	
	void setItem(ItemStack item);
	
}
