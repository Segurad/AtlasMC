package de.atlasmc.entity;

import java.util.UUID;

import de.atlasmc.inventory.AbstractHorseInventory;
import de.atlasmc.inventory.InventoryHolder;

public interface AbstractHorse extends Animal, InventoryHolder {
	
	boolean isTamed();
	
	void setTamed(boolean tamed);
	
	boolean isSaddled();
	
	void setSaddled(boolean saddled);
	
	boolean canBred();
	
	void setCanBred(boolean bred);
	
	boolean isEating();
	
	void setEating(boolean eating);
	
	boolean isRearing();
	
	void setRearing(boolean rearing);
	
	boolean isMouthOpen();
	
	void setMouthOpen(boolean open);
	
	UUID getOwner();

	void setOwner(UUID owner);
	
	/**
	 * May not return the same Inventory every time e.g. {@link ChestedHorse} may return a new Inventory if the has chest value is changed
	 * @return inventory
	 */
	AbstractHorseInventory getInventory();
	
}
