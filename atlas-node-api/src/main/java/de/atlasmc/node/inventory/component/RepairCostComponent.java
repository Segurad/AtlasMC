package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.PrimitiveFieldCodecs;
import de.atlasmc.util.annotation.NotNull;

public interface RepairCostComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<RepairCostComponent>
	NBT_CODEC = PrimitiveFieldCodecs
				.buildInt(RepairCostComponent.class)
				.include(ItemComponent.NBT_CODEC)
				.getter(RepairCostComponent::getRepairCost)
				.setter(RepairCostComponent::setRepairCost)
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
