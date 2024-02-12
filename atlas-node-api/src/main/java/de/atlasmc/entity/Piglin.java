package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.inventory.ItemStack;

public interface Piglin extends AbstractPiglin {
	
	public boolean isBaby();
	
	public void setBaby(boolean baby);
	
	public boolean isChargingCrossbow();
	
	public void setChargingCorssbow(boolean charging);
	
	public boolean isDancing();

	public void setDancing(boolean dancing);

	public void setCanHunt(boolean hunt);
	
	public boolean canHunt();
	
	public List<ItemStack> getPocketItems();
	
	public boolean hasPockItems();
	
	public void addPocketItem(ItemStack item);
	
	public void removePocketItem(ItemStack item);
	
}
