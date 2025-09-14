package de.atlasmc.node.inventory.component;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface RepairCostComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<RepairCostComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(RepairCostComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.intField(ComponentType.REPAIR_COST.getNamespacedKey(), RepairCostComponent::getRepairCost, RepairCostComponent::setRepairCost, 0)
					.build();
	
	int getRepairCost();
	
	void setRepairCost(int cost);
	
	RepairCostComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends ItemComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
