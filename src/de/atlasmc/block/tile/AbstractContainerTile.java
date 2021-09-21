package de.atlasmc.block.tile;

import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryHolder;

public interface AbstractContainerTile<I extends Inventory> extends TileEntity, InventoryHolder {

	public void setCustomName(ChatComponent name);
	
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
	
	public ChatComponent getCustomName();

	public void setLock(ChatComponent lock);
	
	public boolean hasLock();
	
	public ChatComponent getLock();
	
}
