package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.PrimitiveFieldCodecs;
import de.atlasmc.util.annotation.NotNull;

public interface PotionDurationScaleComponent extends ItemComponent {

	@NotNull
	public static final NBTCodec<PotionDurationScaleComponent>
	NBT_CODEC = PrimitiveFieldCodecs
				.buildFloat(PotionDurationScaleComponent.class)
				.include(ItemComponent.NBT_CODEC)
				.getter(PotionDurationScaleComponent::getScale)
				.setter(PotionDurationScaleComponent::setScale) 
				.build();
	
	float getScale();
	
	void setScale(float scale);
	
	@Override
	PotionDurationScaleComponent clone();
	
	@Override
	default NBTCodec<? extends PotionDurationScaleComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
