package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;

public interface PotionDurationScaleComponent extends ItemComponent {

	public static final NBTCodec<PotionDurationScaleComponent>
	NBT_HANDLER = NBTCodec
					.builder(PotionDurationScaleComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.floatField(ComponentType.POTION_DURATION_SCALE.getNamespacedKey(), PotionDurationScaleComponent::getScale, PotionDurationScaleComponent::setScale, 1)
					.build();
	
	float getScale();
	
	void setScale(float scale);
	
	PotionDurationScaleComponent clone();
	
	@Override
	default NBTCodec<? extends PotionDurationScaleComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
