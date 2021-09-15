package de.atlasmc.inventory;

import de.atlasmc.potion.PotionEffect;

public interface BeaconInventory extends Inventory {

	public PotionEffect getPrimaryEffect();
	
	public PotionEffect getSecondaryEffect();

	public void setPrimaryEffect(PotionEffect effect);
	
	public void setSecondaryEffect(PotionEffect effect);

	public int getPrimaryID();
	
	public int getSecondaryID();
	
	/**
	 * Sets the primary effect ID<br>
	 * Use -1 for no effect
	 * @param id
	 */
	public void setPrimaryID(int id);
	
	/**
	 * Sets the secondary effect ID<br>
	 * Use -1 for no effect
	 * @param id
	 */
	public void setSecondaryID(int id);
	
	public int getPowerLevel();
	
	/**
	 * Sets the power level between 0 and 4
	 * @param power
	 */
	public void setPowerLevel(int power);
	
	public ItemStack getItem();
	
	public void setItem(ItemStack item);
	
}
