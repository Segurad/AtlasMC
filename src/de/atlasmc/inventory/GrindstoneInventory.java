package de.atlasmc.inventory;

public interface GrindstoneInventory extends Inventory {
	
	public ItemStack getResult();
	
	public void setResult(ItemStack result);

}
