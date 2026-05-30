package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface PotionDurationScaleComponent extends ItemComponent {

	@NotNull
	public static final NBTCodec<PotionDurationScaleComponent>
	NBT_CODEC = NBTCodec
					.builder(PotionDurationScaleComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.floatField(ComponentType.POTION_DURATION_SCALE.getNamespacedKey(), PotionDurationScaleComponent::getScale, PotionDurationScaleComponent::setScale, 1)
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
