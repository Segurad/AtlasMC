package de.atlasmc.inventory;

public interface LecternInventory extends Inventory {
	
	ItemStack getBook();
	
	void setBook(ItemStack book);
	
	int getPage();
	
	void setPage(int page);

}
