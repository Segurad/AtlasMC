package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface Squid extends WaterAnimal, AgeableMob {

	@NotNull
	public static final NBTCodec<Squid>
	NBT_CODEC = NBTCodec
					.builder(Squid.class)
					.include(WaterAnimal.NBT_CODEC)
					.include(AgeableMob.NBT_CODEC)
					.build();
	
	@Override
	default NBTCodec<? extends Squid> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
