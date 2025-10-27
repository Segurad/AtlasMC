package de.atlasmc.node.inventory.component;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface RepairCostComponent extends ItemComponent {
	
	public static final NBTCodec<RepairCostComponent>
	NBT_HANDLER = NBTCodec
					.builder(RepairCostComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.intField(ComponentType.REPAIR_COST.getNamespacedKey(), RepairCostComponent::getRepairCost, RepairCostComponent::setRepairCost, 0)
					.build();
	
	int getRepairCost();
	
	void setRepairCost(int cost);
	
	RepairCostComponent clone();
	
	@Override
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
