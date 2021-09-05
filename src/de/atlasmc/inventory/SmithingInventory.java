package de.atlasmc.inventory;

public interface SmithingInventory extends Inventory {
	
	public ItemStack getResult();
	
	public void setResult(ItemStack item);

}
