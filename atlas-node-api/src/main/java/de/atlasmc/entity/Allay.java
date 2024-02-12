package de.atlasmc.entity;

import de.atlasmc.inventory.InventoryHolder;

public interface Allay extends Creature, InventoryHolder {
	
	boolean canDuplicate();
	
	void setCanDuplicate(boolean canDuplicate);
	
	long getDuplicationCooldown();
	
	void setDuplicationCooldown(long cooldown);
	
}
