package de.atlasmc.inventory.component;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface PotionDurationScaleComponent extends ItemComponent {

	public static final NBTSerializationHandler<PotionDurationScaleComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(PotionDurationScaleComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.floatField(ComponentType.POTION_DURATION_SCALE, PotionDurationScaleComponent::getScale, PotionDurationScaleComponent::setScale, 1)
					.build();
	
	float getScale();
	
	void setScale(float scale);
	
	PotionDurationScaleComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends PotionDurationScaleComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
