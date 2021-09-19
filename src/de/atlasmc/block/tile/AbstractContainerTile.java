package de.atlasmc.block.tile;

import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.inventory.InventoryHolder;

public interface AbstractContainerTile extends TileEntity, InventoryHolder {

	public void setCustomName(ChatComponent name);
	
	public boolean hasCustomName();
	
	public ChatComponent getCustomName();

	public void setLock(ChatComponent lock);
	
	public boolean hasLock();
	
	public ChatComponent getLock();
	
}
