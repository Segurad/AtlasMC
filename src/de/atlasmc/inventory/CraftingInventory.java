package de.atlasmc.inventory;

public interface CraftingInventory extends Inventory {
	
	public ItemStack getResult();
	
	public void setResult(ItemStack result);

}
