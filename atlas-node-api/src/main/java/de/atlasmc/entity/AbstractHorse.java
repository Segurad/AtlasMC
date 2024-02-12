package de.atlasmc.entity;

import java.util.UUID;

import de.atlasmc.inventory.AbstractHorseInventory;
import de.atlasmc.inventory.InventoryHolder;

public interface AbstractHorse extends Animal, InventoryHolder {
	
	public boolean isTamed();
	
	public void setTamed(boolean tamed);
	
	public boolean isSaddled();
	
	public void setSaddled(boolean saddled);
	
	public boolean canBred();
	
	public void setCanBred(boolean bred);
	
	public boolean isEating();
	
	public void setEating(boolean eating);
	
	public boolean isRearing();
	
	public void setRearing(boolean rearing);
	
	public boolean isMouthOpen();
	
	public void setMouthOpen(boolean open);
	
	public UUID getOwner();

	public void setOwner(UUID owner);
	
	/**
	 * May not return the same Inventory every time e.g. {@link ChestedHorse} may return a new Inventory if the has chest value is changed
	 * @return inventory
	 */
	public AbstractHorseInventory getInventory();
	
}
