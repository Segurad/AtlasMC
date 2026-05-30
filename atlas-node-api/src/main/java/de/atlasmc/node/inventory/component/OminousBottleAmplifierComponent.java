package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface OminousBottleAmplifierComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<OminousBottleAmplifierComponent>
	NBT_CODEC = NBTCodec
					.builder(OminousBottleAmplifierComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.intField(ComponentType.OMINOUS_BOTTLE_AMPLIFIER.getNamespacedKey(), OminousBottleAmplifierComponent::getAmplifier, OminousBottleAmplifierComponent::setAmplifier, 0)
					.build();
	
	int getAmplifier();
	
	void setAmplifier(int amplifier);
	
	@Override
	OminousBottleAmplifierComponent clone();
	
	@Override
	default NBTCodec<? extends OminousBottleAmplifierComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
