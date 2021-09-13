package de.atlascore.inventory;

import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.BeaconInventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.potion.PotionEffect;

public class CoreBeaconInventory extends CoreInventory implements BeaconInventory {

	public CoreBeaconInventory(String title, InventoryHolder holder) {
		super(1, InventoryType.BEACON, title, holder);
	}

	@Override
	public PotionEffect getPrimaryEffect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PotionEffect getSecondaryEffect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPrimaryEffect(PotionEffect effect) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSecondaryEffect(PotionEffect effect) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getPrimaryID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSecondaryID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPrimaryID(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSecondaryID(int id) {
		// TODO Auto-generated method stub
		
	}

}
