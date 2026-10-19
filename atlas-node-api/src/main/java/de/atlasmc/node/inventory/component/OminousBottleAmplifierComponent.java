package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.PrimitiveFieldCodecs;
import de.atlasmc.util.annotation.NotNull;

public interface OminousBottleAmplifierComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<OminousBottleAmplifierComponent>
	NBT_CODEC = PrimitiveFieldCodecs
				.buildInt(OminousBottleAmplifierComponent.class)
				.include(ItemComponent.NBT_CODEC)
				.getter(OminousBottleAmplifierComponent::getAmplifier)
				.setter(OminousBottleAmplifierComponent::setAmplifier)
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
