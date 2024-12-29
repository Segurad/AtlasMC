package de.atlasmc.block.tile;

import de.atlasmc.Nameable;
import de.atlasmc.chat.Chat;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryHolder;

public interface AbstractContainerTile<I extends Inventory> extends TileEntity, InventoryHolder, Nameable {
	
	/**
	 * Returns the {@link Inventory} of this Tile (creates a Inventory of not present)
	 * @return the Inventory of this Tile
	 */
	I getInventory();
	
	/**
	 * 
	 * @return true if a inventory has been set
	 */
	boolean hasInventory();

	void setLock(Chat lock);
	
	boolean hasLock();
	
	Chat getLock();
	
}
