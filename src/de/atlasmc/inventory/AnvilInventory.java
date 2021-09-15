package de.atlasmc.inventory;

public interface AnvilInventory extends Inventory {

	public ItemStack getResult();

	public void setResult(ItemStack result);
	
	public int getRepairCost();
	
	public void setRepairCost(int value);

}
