package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface RepairCostComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<RepairCostComponent>
	NBT_CODEC = NBTCodec
					.builder(RepairCostComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.intField(ComponentType.REPAIR_COST.getNamespacedKey(), RepairCostComponent::getRepairCost, RepairCostComponent::setRepairCost, 0)
					.build();
	
	int getRepairCost();
	
	void setRepairCost(int cost);
	
	@Override
	RepairCostComponent clone();
	
	@Override
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
