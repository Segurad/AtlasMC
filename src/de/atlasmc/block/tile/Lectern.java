package de.atlasmc.block.tile;

import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.LecternInventory;

public interface Lectern extends TileEntity, InventoryHolder {
	
	public LecternInventory getInventory();

	public ItemStack getBook();
	
	public void setBook(ItemStack book);

	public int getPage();
	
	public void setPage(int page);
	
}
