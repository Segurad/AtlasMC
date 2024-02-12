package de.atlasmc.block.tile;

import de.atlasmc.Nameable;
import de.atlasmc.chat.Chat;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryHolder;

public interface AbstractContainerTile<I extends Inventory> extends TileEntity, InventoryHolder, Nameable {
	
	public boolean hasCustomName();
	
	/**
	 * Returns the {@link Inventory} of this Tile (creates a Inventory of not present)
	 * @return the Inventory of this Tile
	 */
	public I getInventory();
	
	/**
	 * 
	 * @return true if a inventory has been set
	 */
	public boolean hasInventory();

	public void setLock(Chat lock);
	
	public boolean hasLock();
	
	public Chat getLock();
	
}
