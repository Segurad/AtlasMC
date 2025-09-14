package de.atlasmc.node.inventory;

public interface AnvilInventory extends AbstractCraftingInventory {
	
	int getRepairCost();
	
	void setRepairCost(int value);

}
