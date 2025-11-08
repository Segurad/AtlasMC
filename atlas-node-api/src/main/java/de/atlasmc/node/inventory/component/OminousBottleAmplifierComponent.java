package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;

public interface OminousBottleAmplifierComponent extends ItemComponent {
	
	public static final NBTCodec<OminousBottleAmplifierComponent>
	NBT_HANDLER = NBTCodec
					.builder(OminousBottleAmplifierComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.intField(ComponentType.OMINOUS_BOTTLE_AMPLIFIER.getNamespacedKey(), OminousBottleAmplifierComponent::getAmplifier, OminousBottleAmplifierComponent::setAmplifier, 0)
					.build();
	
	int getAmplifier();
	
	void setAmplifier(int amplifier);
	
	OminousBottleAmplifierComponent clone();
	
	@Override
	default NBTCodec<? extends OminousBottleAmplifierComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
