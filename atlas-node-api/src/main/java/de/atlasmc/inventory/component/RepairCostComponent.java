package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface RepairCostComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:repair_cost");
	
	int getRepairCost();
	
	void setRepairCost(int cost);
	
	RepairCostComponent clone();

}
