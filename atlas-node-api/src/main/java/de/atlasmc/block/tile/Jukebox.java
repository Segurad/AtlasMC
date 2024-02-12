package de.atlasmc.block.tile;

import de.atlasmc.inventory.ItemStack;

public interface Jukebox extends TileEntity {
	
	public ItemStack getRecordItem();
	
	public void setRecordItem(ItemStack record);

}
