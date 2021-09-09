package de.atlasmc.inventory;

import de.atlasmc.potion.PotionEffect;

public interface BeaconInventory extends Inventory {

	public PotionEffect getPrimaryEffect();
	
	public PotionEffect getSecondaryEffect();

	public void setPrimaryEffect(PotionEffect effect);
	
	public void setSecondaryEffect(PotionEffect effect);

	public int getPrimaryID();
	
	public int getSecondaryID();
	
	public void setPrimaryID(int id);
	
	public void setSecondaryID(int id);
	
}
