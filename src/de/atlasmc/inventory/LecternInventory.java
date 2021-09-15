package de.atlasmc.inventory;

public interface LecternInventory extends Inventory {
	
	public ItemStack getBook();
	
	public void setBook(ItemStack book);
	
	public int getPage();
	
	public void setPage(int page);

}
