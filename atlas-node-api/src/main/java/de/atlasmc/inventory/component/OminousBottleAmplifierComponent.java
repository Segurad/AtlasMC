package de.atlasmc.inventory.component;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface OminousBottleAmplifierComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<OminousBottleAmplifierComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(OminousBottleAmplifierComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.intField(ComponentType.OMINOUS_BOTTLE_AMPLIFIER.getNamespacedKey(), OminousBottleAmplifierComponent::getAmplifier, OminousBottleAmplifierComponent::setAmplifier, 0)
					.build();
	
	int getAmplifier();
	
	void setAmplifier(int amplifier);
	
	OminousBottleAmplifierComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends OminousBottleAmplifierComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
