package de.atlasmc.block.tile;

import de.atlasmc.inventory.BeaconInventory;
import de.atlasmc.inventory.InventoryHolder;

public interface Beacon extends TileEntity, InventoryHolder {
	
	public BeaconInventory getInventory();

	public void setLevel(int level);
	
	public int getLevel();
	
	public int getPrimaryID();
	
	public void setPrimaryID(int primary);
	
	public int getSecondaryID();
	
	public void setSecondaryID(int secondary);

}
